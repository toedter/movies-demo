package com.toedter.movies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL_FORMS;

@Configuration
// @EnableHypermediaSupport(type = { HAL, HAL_FORMS, UBER, COLLECTION_JSON })
@EnableHypermediaSupport(type = { HAL_FORMS })
class HypermediaConfiguration {
    @Bean
    public HalConfiguration linkRelationBasedPolicy() {
        return new HalConfiguration()
                .withRenderSingleLinksFor(
                        LinkRelation.of("directors"), HalConfiguration.RenderSingleLinks.AS_ARRAY)
                .withRenderSingleLinksFor(
                        LinkRelation.of("movies"), HalConfiguration.RenderSingleLinks.AS_ARRAY);
    }

}
