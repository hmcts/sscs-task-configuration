package uk.gov.hmcts.reform.sscstaskconfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SmokeTest {

    private static final String HEALTH_ENDPOINT = "/health";

    @Value("${server.port:4550}")
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void healthCheck() {
        Response response = RestAssured.given().when().get(HEALTH_ENDPOINT).thenReturn();

        assertThat(response.getStatusCode()).isEqualTo(OK.value());
        assertThat(response.jsonPath().getString("status")).isEqualTo("UP");
    }
}
