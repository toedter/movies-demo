package com.toedter.movies;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.*;

@Configuration
// @EnableHypermediaSupport(type = { HAL, HAL_FORMS, UBER, COLLECTION_JSON })
@EnableHypermediaSupport(type = { HAL_FORMS })
class HypermediaConfiguration {
}
