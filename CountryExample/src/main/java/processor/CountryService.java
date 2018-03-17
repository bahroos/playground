package processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.CountryNotFoundException;
import model.Country;

/*
 * It is just a helper class which should be replaced by database implementation.
 * It is not very well written class, it is just used for demonstration.
 */
public class CountryService {

	private static HashMap<Integer,Country> countryIdMap = new HashMap<>();

	public CountryService() {

	}

	public void preInitiateCountryMap() {

		// Creating some object of countries while initializing
		Country indiaCountry = new Country(1, "India",10000);
		Country chinaCountry = new Country(4, "China",20000);
		Country nepalCountry = new Country(3, "Nepal",8000);
		Country bhutanCountry = new Country(2, "Bhutan",7000);

		countryIdMap.put(1, indiaCountry);
		countryIdMap.put(4, chinaCountry);
		countryIdMap.put(3, nepalCountry);
		countryIdMap.put(2, bhutanCountry);
	}

	public void tearDownCountryMap() {
		countryIdMap.clear();
	}

	public List<Country> getAllCountries() {
		List<Country> countries = new ArrayList<Country>(countryIdMap.values());
		return countries;
	}

	public Country getCountry(int id) {
		Country country = countryIdMap.get(id);

		if(country == null) {
			return null;
		}
		return country;
	}

	public Country addCountry(Country country) {
		country.setId(getMaxId()+1);
		countryIdMap.put(country.getId(), country);
		return country;
	}

	public Country updateCountry(Country country) {
		if(country.getId()<=0) {
			return null;
		}
		countryIdMap.put(country.getId(), country);
		return country;

	}
	public void deleteCountry(int id) {
		countryIdMap.remove(id);
	}

	public HashMap<Integer, Country> getCountryIdMap() {
		return countryIdMap;
	}

	// Utility method to get max id
	public int getMaxId() {
		int max=0;
		for (int id:countryIdMap.keySet()) {
			if(max<=id) {
				max = id;
			}
		}
		return max;
	}
}
