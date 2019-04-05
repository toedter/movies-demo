package com.toedter.movies;

import com.toedter.movies.movie.MovieController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class RootController {

	@GetMapping("/api")
    ResponseEntity<RepresentationModel> root() {

		RepresentationModel resourceSupport = new RepresentationModel();

		resourceSupport.add(linkTo(methodOn(RootController.class).root()).withSelfRel());

		Link selfLink = linkTo(MovieController.class).slash("directors").withRel("directors");
		Link templatedLink = new Link(selfLink.getHref() + "{?size,page}").withRel("directors");

		resourceSupport.add(templatedLink);

		return ResponseEntity.ok(resourceSupport);
	}

}
