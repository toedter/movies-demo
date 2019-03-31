package com.toedter.movies;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HalEntityModel<T> extends EntityModel<T> {

    private Map<String, Object> embeddedMap;

    public HalEntityModel(T content) {
        super(content);
    }

    public HalEntityModel(T content, Link... links) {
        super(content, links);
    }

    @SuppressWarnings("unchecked")
    public void addEmbeddedResource(String relation, EntityModel embeddedModel) {
        if (embeddedMap == null) {
            embeddedMap = new HashMap<>();
        }

        Object embeddedResources = embeddedMap.get(relation);
        // This is a quick hack, off course in real life there are singular resources ending with s
        boolean isPluralRelation = relation.endsWith("s");

        // if a relation is plural, the content will always be rendered as an array
        if(isPluralRelation) {
            if (embeddedResources == null) {
                embeddedResources = new ArrayList<>();
            }
            ((List<Object>) embeddedResources).add(embeddedModel);

            // if a relation is singular, it would be a single object if there is only one object available
            // Otherwise it would be rendered as array
        } else {
            if (embeddedResources == null) {
                embeddedResources = embeddedModel;
            } else {
                if (embeddedResources instanceof List) {
                    ((List<Object>) embeddedResources).add(embeddedModel);
                } else {
                    List<Object> embeddedResourcesList = new ArrayList<>();
                    embeddedResourcesList.add(embeddedResources);
                    embeddedResourcesList.add(embeddedModel);
                    embeddedResources = embeddedResourcesList;
                }
            }
        }
        embeddedMap.put(relation, embeddedResources);
    }

    @JsonProperty("_embeddedXXX")
    public Map<String, Object> getEmbeddedRecources() {
        return embeddedMap;
    }
}
