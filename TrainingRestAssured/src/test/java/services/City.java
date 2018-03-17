package services;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.given;

public class City {
    private String city = "";
    private String baseURL = "";

    public City(String city, String baseURL) {
        this.city = city;
        this.baseURL = baseURL;
    }

    public String testResponse () {
        System.out.println("Triggering the call to " + baseURL + city);
        Response response =
                given().
                  proxy(com.jayway.restassured.specification.ProxySpecification.host("proxy-vs-access.inet.nl.abnamro.com").withPort(8080).withAuth("sieger.veenstra", "20100705135903")).
                when()
                  .get(baseURL + city).
                then()
                  .statusCode(200).contentType("application/json").extract().response();

        String respcode = response.asString();

        System.out.println("Response code contains : " + respcode);

        return respcode;
    }
}
