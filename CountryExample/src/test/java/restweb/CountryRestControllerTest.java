package restweb;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CountryNotAcceptableException;
import helper.CountryHelper;
import model.Country;
import model.ExceptionMessage;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import processor.CountryService;

import java.util.List;

public class CountryRestControllerTest {

    public static Response response;

    @BeforeClass
    public static void prepareDatabase() {
        CountryHelper.cleanDatabase();
        CountryHelper.prePopulateDatabase();
        System.out.println("----------- STARTING TESTS -----------");
    }

    @AfterClass
    public static void tearDownDatabase() {
        CountryHelper.cleanDatabase();
    }

    @Test
    public void getAllCountries() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        response = given()
                .when()
                .get(CountryHelper.baseUrl + CountryHelper.resourcePath)
                .then()
                .statusCode(200).contentType(ContentType.JSON).extract().response();

        assertEquals(200, response.getStatusCode());

        response.getBody().prettyPrint();
    }

    @Test
    public void getValidCountryById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        response = given()
                .when()
                .get(CountryHelper.baseUrl + CountryHelper.resourcePath + "/{id}", 1)
                .then()
                .statusCode(200).contentType("application/json").extract().response();

        assertEquals(200, response.getStatusCode());

        response.getBody().prettyPrint();

        String responseString = response.asString();

        Country country = mapper.readValue(responseString, Country.class);

        assertEquals(1, country.getId());
        assertEquals("India", country.getCountryName());
        assertEquals(10000, country.getPopulation());

    }

    @Test
    public void getInvalidCountryById() throws Exception {
        response = given()
                .when()
                .get(CountryHelper.baseUrl + CountryHelper.resourcePath + "/{id}", 600)
                .then()
                .statusCode(404).contentType(ContentType.JSON).extract().response();

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void addCountryToList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country(5, "MyNewAwesomeCountry", 123);
        response = given()
                .body(country)
                .when()
                .contentType(ContentType.JSON)
                .post(CountryHelper.baseUrl + CountryHelper.resourcePath)
                .then()
                .statusCode(200).contentType(ContentType.JSON).extract().response();

        String responseBody = response.getBody().asString();
        Country createdCountry = mapper.readValue(responseBody, Country.class);

        assertEquals(country.getId(), createdCountry.getId());
        assertEquals(country.getCountryName(), createdCountry.getCountryName());
        assertEquals(country.getPopulation(),createdCountry.getPopulation());

    }

    @Test
    public void addInvalidCountryToList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country(99, "Fake", 99);
        response = given()
                .body(country)
                .when()
                .contentType(ContentType.JSON)
                .post(CountryHelper.baseUrl + CountryHelper.resourcePath)
                .then()
                .statusCode(406).contentType(ContentType.JSON).extract().response();

        ExceptionMessage exceptionThrown = mapper.readValue(response.getBody().asString(), ExceptionMessage.class);

        assertEquals(String.valueOf(406), exceptionThrown.getExcpetionCode());
        assertEquals("Can not put FAKE country", exceptionThrown.getExceptionMessage());

    }

    @Test
    public void updateValidCountry() throws Exception {

    }

    @Test
    public void tryUpdateInvalidCountry() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country(999, "NewUpdatedCountry", 999);
        response = given()
                .body(country)
                .when()
                .contentType(ContentType.JSON)
                .put(CountryHelper.baseUrl + CountryHelper.resourcePath)
                .then()
                .statusCode(404).contentType(ContentType.JSON).extract().response();

        assertEquals(404, response.getStatusCode());

        ExceptionMessage exceptionThrown = mapper.readValue(response.getBody().asString(), ExceptionMessage.class);

        assertEquals(String.valueOf(404), exceptionThrown.getExcpetionCode());
        assertEquals("Can not update not existing country", exceptionThrown.getExceptionMessage());

    }

    @Test
    public void deleteValidCountry() throws Exception {
        response = given()
                .when()
                .delete(CountryHelper.baseUrl + CountryHelper.resourcePath + "/{id}", 2)
                .then()
                .statusCode(204).contentType(ContentType.JSON).extract().response();

        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void tryDeleteNotExistingCountry() throws Exception {
        response = given()
                .when()
                .delete(CountryHelper.baseUrl + CountryHelper.resourcePath + "/{id}", 999)
                .then()
                .statusCode(404).contentType(ContentType.JSON).extract().response();

        assertEquals(404, response.getStatusCode());
    }

}