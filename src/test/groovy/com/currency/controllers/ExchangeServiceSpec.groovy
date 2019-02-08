package com.currency.controllers


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class ExchangeServiceSpec extends BaseControllerSpec {

  // integration test
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
