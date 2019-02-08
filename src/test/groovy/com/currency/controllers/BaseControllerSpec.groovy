package com.currency.controllers

import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification

import com.currency.CurrencyExchangeApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@SpringBootTest(classes = CurrencyExchangeApplication)
@DirtiesContext
abstract class BaseControllerSpec extends Specification {

    @Autowired
    protected WebApplicationContext context

    def setup() {
        RestAssuredMockMvc.webAppContextSetup(context)
    }

    protected MockMvcRequestSpecification given() {
        RestAssuredMockMvc.given()
                .log()
                .all(true)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
    }
}