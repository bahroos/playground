package restweb;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exception.CountryNotAcceptableException;
import exception.CountryNotFoundException;
import model.Country;
import processor.CountryService;


@Path("/countries")
public class CountryRestController {
	
	CountryService countryService = new CountryService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountries() {
		List<Country> listOfCountries = countryService.getAllCountries();
		return listOfCountries;
	}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Country getCountryById(@PathParam("id") int id) {
		if(countryService.getCountry(id) == null) {
			throw new CountryNotFoundException("NOT FOUND");
		}
		return countryService.getCountry(id);
	}
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Country addCountry(Country country) {
    	if(country.getCountryName().equals("Fake")) {
    		throw new CountryNotAcceptableException("Can not put FAKE country");
		}
		return countryService.addCountry(country);
	}

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	public Country updateCountry(Country country) {
    	if(countryService.getCountry(country.getId()) == null) {
    		throw new CountryNotFoundException("Can not update not existing country");
		}
		return countryService.updateCountry(country);
	}
	
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public void deleteCountry(@PathParam("id") int id) {
		if(countryService.getCountry(id) == null) {
			throw new CountryNotFoundException("Country not found");
		}
		 countryService.deleteCountry(id);
	}
	
}
