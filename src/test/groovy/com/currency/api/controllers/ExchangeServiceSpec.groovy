package com.currency.api.controllers

import spock.lang.Ignore

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class ExchangeServiceSpec extends BaseControllerSpec {

  // integration test
    @Ignore
    def "Should build ExchangeRates"() {

        expect:
        given()
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/latest")
                .then()
                .statusCode(200)
    }


}
