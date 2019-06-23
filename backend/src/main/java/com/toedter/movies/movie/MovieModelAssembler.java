package com.toedter.movies.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.*;
import org.springframework.hateoas.mediatype.hal.forms.HalFormsProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
@Slf4j
class MovieModelAssembler {

    public MovieRepresentationModel toModel(Movie movie) {
        // Link moviesLink = linkTo(MovieController.class).slash("movies").withRel("movies");
        // Link templatedMoviesLink = new Link(moviesLink.getHref() + "{?size,page}").withRel("movies");

        final UriTemplate template =
                UriTemplate.of(linkTo(methodOn(MovieController.class).findAll(0, 0)).toString())
                        .with("page", TemplateVariable.VariableType.REQUEST_PARAM)
                        .with("size", TemplateVariable.VariableType.REQUEST_PARAM);
        Link templatedMoviesLink = new Link(template.toString()).withRel("movies");

        Link selfLink = linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel();

        final Affordance updateAffordance =
                afford(methodOn(MovieController.class).updateMovie(null, movie.getId()));
        addAffordancePrompts(updateAffordance);

        final Affordance deleteAffordance =
                afford(methodOn(MovieController.class).deleteMovie(movie.getId()));

        return new MovieRepresentationModel(movie, selfLink
                .andAffordance(updateAffordance)
                .andAffordance(deleteAffordance),
                templatedMoviesLink);
    }

    public void addAffordancePrompts(Affordance affordance) {
        AffordanceModel affordanceModel = affordance.getAffordanceModel(MediaTypes.HAL_FORMS_JSON);

        try {
            Method method = affordanceModel.getClass().getDeclaredMethod("getInputProperties");
            method.setAccessible(true);
            List<HalFormsProperty> halFormsProperties = (List<HalFormsProperty>) method.invoke(affordanceModel);
            Field promptField = HalFormsProperty.class.getDeclaredField("prompt");
            promptField.setAccessible(true);

            ResourceBundle movieProperties = ResourceBundle.getBundle("movie");

            for (HalFormsProperty property : halFormsProperties) {
                String prompt = property.getName();
                try {
                    prompt = movieProperties.getString(property.getName());
                } catch (Exception e) {
                    log.debug("key: " + property.getName() + " not found.");
                }

                promptField.set(property, prompt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
