package helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Country;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import processor.CountryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryHelper {

    public static final String baseUrl = "http://localhost:8080/CountryExample/rest";
    public static final String resourcePath = "/countries";
    public static Response response;

    public static void prePopulateDatabase() {
        System.out.println("ADDING MOCK VALUES");
        HashMap<Integer,Country> CountryList = getFakeCountryData();
        for(Map.Entry<Integer, Country> entry : CountryList.entrySet()) {
            Country country = entry.getValue();
            response = given()
                    .body(country)
                    .when()
                    .contentType(ContentType.JSON)
                    .post(CountryHelper.baseUrl + CountryHelper.resourcePath)
                    .then()
                    .statusCode(200).contentType(ContentType.JSON).extract().response();
        }

    }

    public static HashMap<Integer,Country> getFakeCountryData() {
        HashMap<Integer,Country> countryList = new HashMap<>();

        Country indiaCountry = new Country(1, "India",10000);
        Country chinaCountry = new Country(4, "China",20000);
        Country nepalCountry = new Country(3, "Nepal",8000);
        Country bhutanCountry = new Country(2, "Bhutan",7000);

        countryList.put(1, indiaCountry);
        countryList.put(4, chinaCountry);
        countryList.put(3, nepalCountry);
        countryList.put(2, bhutanCountry);

        return countryList;
    }

    public static void cleanDatabase() {
        System.out.println("DATABASE CLEANUP");
        ObjectMapper mapper = new ObjectMapper();
        response = given()
                .when()
                .get(CountryHelper.baseUrl + CountryHelper.resourcePath)
                .then()
                .statusCode(200).contentType(ContentType.JSON).extract().response();

        assertEquals(200, response.getStatusCode());

        response.getBody().print();

        String responseBody = response.getBody().asString();
        List<Country> countryList = null;
        try {
            countryList = mapper.readValue(responseBody, new TypeReference<List<Country>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Country c : countryList) {
            int id = c.getId();
            System.out.println("Deleting ID: " + id);
            response = given()
                    .when()
                    .delete(CountryHelper.baseUrl + CountryHelper.resourcePath + "/{id}", id)
                    .then()
                    .statusCode(204).contentType(ContentType.JSON).extract().response();
        }
    }
}
