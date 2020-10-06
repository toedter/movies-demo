package com.toedter.movies.director;

import com.toedter.movies.RootController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(RootController.API_BASE_PATH)
public class DirectorController {

    private final DirectorRepository repository;

    DirectorController(DirectorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/directors")
    ResponseEntity<CollectionModel<DirectorRepresentationModel>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {

        final int finalSize = size;
        final int finalPage = page;
        final PageRequest pageRequest = PageRequest.of(finalPage, finalSize);

        final Page<Director> pagedResult = repository.findAll(pageRequest);

        List<DirectorRepresentationModel> directorResources = StreamSupport.stream(pagedResult.spliterator(), false)
                .map(director -> new DirectorRepresentationModel(director))
                .collect(Collectors.toList());

        Link selfLink = linkTo(DirectorController.class).slash("directors?page=" + page + "&size=" + size).withSelfRel();
        Link baseLink = linkTo(DirectorController.class).slash("directors").withSelfRel();

        PagedModel.PageMetadata pageMetadata =
                new PagedModel.PageMetadata(pagedResult.getSize(), pagedResult.getNumber(), pagedResult.getTotalElements(), pagedResult.getTotalPages());
        final PagedModel<DirectorRepresentationModel> pagedModel =
                PagedModel.of(directorResources, pageMetadata);


        final Pageable prev = pageRequest.previous();
        if (prev.getPageNumber() < page) {
            Link prevLink = Link.of(baseLink.getHref() + "?page=" + prev.getPageNumber() + "&size=" + prev.getPageSize()).withRel(IanaLinkRelations.PREV);
            pagedModel.add(prevLink);
        }

        final Pageable next = pageRequest.next();
        if (next.getPageNumber() > page && next.getPageNumber() < pagedResult.getTotalPages()) {
            Link nextLink = Link.of(baseLink.getHref() + "?page=" + next.getPageNumber() + "&size=" + next.getPageSize()).withRel(IanaLinkRelations.NEXT);
            pagedModel.add(nextLink);
        }

        if (page > 0) {
            Link firstLink = Link.of(baseLink.getHref() + "?page=0&size=" + size).withRel(IanaLinkRelations.FIRST);
            pagedModel.add(firstLink);
        }

        if (page < pagedResult.getTotalPages() - 1) {
            Link lastLink = Link.of(baseLink.getHref() + "?page=" + (pagedResult.getTotalPages() - 1) + "&size=" + size).withRel(IanaLinkRelations.LAST);
            pagedModel.add(lastLink);
        }

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorRepresentationModel> findOne(@PathVariable Long id) {
        return repository.findById(id)
                .map(DirectorRepresentationModel::new)
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }
}
