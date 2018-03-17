package stepdefinition;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import services.City;

public class CityStepDefinition {

    private String baseURL;

    @Before
    public void before() {
        baseURL = "http://restapi.demoqa.com/utilities/weather/city/";

    }

    @Given("^that I am a weather broadcaster$")
    public void that_I_am_a_weather_broadcaster() throws Throwable {
        // Express the Regexp above with the code you wish you had
    }

    @When("^I query for a given \"([^\"]*)\"$")
    public void I_query_for_a_given(String city) throws Throwable {
        // Express the Regexp above with the code you wish you had
        City cityObj = new City(city, baseURL);

        System.out.println("Value returned is " + cityObj.testResponse());
    }

    @Then("^I would like to see the temparature of the city.$")
    public void I_would_like_to_see_the_temparature_of_the_city() throws Throwable {
        // Express the Regexp above with the code you wish you had
    }

    @After
    public void After() {
        baseURL = "";
    }
}
