package com.toedter.movies;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL_FORMS;

@Configuration
// @EnableHypermediaSupport(type = { HAL, HAL_FORMS, UBER, COLLECTION_JSON })
@EnableHypermediaSupport(type = {HAL_FORMS})
class HypermediaConfiguration {
//    This is the config if we always want to render movies and directors as array
//
//    @Bean
//    public HalFormsConfiguration linkRelationBasedPolicy() {
//        return new HalFormsConfiguration()
//                .getHalConfiguration().withRenderSingleLinksFor(
//                        LinkRelation.of("directors"), HalConfiguration.RenderSingleLinks.AS_ARRAY)
//                .withRenderSingleLinksFor(
//                        LinkRelation.of("movies"), HalConfiguration.RenderSingleLinks.AS_ARRAY);
//    }
}
