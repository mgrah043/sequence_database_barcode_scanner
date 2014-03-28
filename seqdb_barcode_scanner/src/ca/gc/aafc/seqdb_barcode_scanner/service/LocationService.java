package ca.gc.aafc.seqdb_barcode_scanner.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Location;
import ca.gc.aafc.seqdb_barcode_scanner.entities.UriList;
import ca.gc.aafc.seqdb_barcode_scanner.entities.WSResponse;

/**
 * @author NazirLKC
 *
 */
public class LocationService implements EntityServiceI{

	private String BASE_URL;
	private String ENTITY_URL;
	
	public LocationService(String serverURL){
		BASE_URL = serverURL;
		ENTITY_URL = BASE_URL + "/location";
	}
	
	public long getCount() {
		long count = -1;
		// The URL for making the GET request
		final String url = ENTITY_URL + "/count/";
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		try {
			WSResponse wsResponse = restTemplate.getForObject(url, WSResponse.class);
			// check for errors
			if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
				count = wsResponse.getCount().getTotal();
			}
		} catch (Exception e){
			if (e.getMessage() != null) Log.e(LocationService.class.toString(), e.getMessage());
			else Log.e(LocationService.class.toString(), "An error occured while getting the location count");
		}
		return count;
	}

	public ArrayList<Location> getAll() {
		final String url = ENTITY_URL;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		// This is where we call the exchange method and process the response
		ResponseEntity<WSResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), WSResponse.class);
		
		ArrayList<Location> locations = new ArrayList<Location>();
		WSResponse wsResponse = responseEntity.getBody();
		// check for errors
		if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
			UriList uriList = wsResponse.getUriList();
			// iterate over list of URIs and get the locations
			for (UriList.UrlPath locationURL : uriList.getUris()){
				long id = Long.parseLong(locationURL.getUrlPath());
				// get location by id and add to list
				locations.add(getById(id));
			}
			
			// If there are more pages of URIs, get them
			while (uriList.getNextPageUrl() != null){
				// get the next set of URLs
				responseEntity = restTemplate.exchange(uriList.getNextPageUrl(), HttpMethod.GET, getRequestEntity(), WSResponse.class);
				wsResponse = responseEntity.getBody();
				uriList = wsResponse.getUriList();
				// iterate over list of URIs and get the locations
				for (UriList.UrlPath locationURL : uriList.getUris()){
					long id = Long.parseLong(locationURL.getUrlPath());
					// get location by id and add to list
					locations.add(getById(id));
				}
			}
		}
		
		return locations;
	}
	
	public ArrayList<Location> getAll(String url) {
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		Log.d(LocationService.class.toString(), "URL: " + url);
		// This is where we call the exchange method and process the response
		ResponseEntity<WSResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), WSResponse.class);
		
		ArrayList<Location> locations = new ArrayList<Location>();
		WSResponse wsResponse = responseEntity.getBody();
		// check for errors
		if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
			UriList uriList = wsResponse.getUriList();
			Log.d(LocationService.class.toString(), "URL list is null: " + (uriList == null));
			// iterate over list of URIs and get the locations
			for (UriList.UrlPath containerURL : uriList.getUris()){
				long id = Long.parseLong(containerURL.getUrlPath());
				// get container by id and add to list
				locations.add(getById(id));
			}
			
			// If there are more pages of URIs, get them
			while (uriList.getNextPageUrl() != null){
				// get the next set of URLs
				responseEntity = restTemplate.exchange(uriList.getNextPageUrl(), HttpMethod.GET, getRequestEntity(), WSResponse.class);
				wsResponse = responseEntity.getBody();
				uriList = wsResponse.getUriList();
				// iterate over list of URIs and get the locations
				for (UriList.UrlPath containerURL : uriList.getUris()){
					long id = Long.parseLong(containerURL.getUrlPath());
					// get container by id and add to list
					locations.add(getById(id));
				}
			}
		}
		
		return locations;
	}

	public Location getById(long id) {
		Location location = null;
		// The URL for making the GET request
		final String url = ENTITY_URL + "/" + id;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		try {
			WSResponse wsResponse = restTemplate.getForObject(url, WSResponse.class, id);
			// check for errors
			if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
				location = wsResponse.getLocation();
				
				if (!location.getMixedSpecimenUrl().isEmpty()){
					String[] parsedURL = location.getMixedSpecimenUrl().split("/");
					long mixedSpecimenId = Long.parseLong(parsedURL[parsedURL.length - 1]);
					location.setMixedSpecimen(new MixedSpecimenService(BASE_URL).getById(mixedSpecimenId, location));
				//TODO uncomment the following lines once the methods have been implemented
				}else if (!location.getPcrPrimerUrl().isEmpty()){
					//String[] parsedURL = location.getPcrPrimerUrl().split("/");
					//long pcrPrimerId = Long.parseLong(parsedURL[parsedURL.length - 1]);
					//location.setPcrPrimer(new PcrPrimerService(BASE_URL).getById(pcrPrimerId, location));
				}else if (!location.getSampleUrl().isEmpty()){
					//String[] parsedURL = location.getSampleUrl().split("/");
					//long sampleId = Long.parseLong(parsedURL[parsedURL.length - 1]);
					//location.setSample(new SampleService(BASE_URL).getById(sampleId, location));
				}else if (!location.getSpecimenReplicateUrl().isEmpty()){
					//String[] parsedURL = location.getSpecimenReplicateUrl().split("/");
					//long specimenReplicateId = Long.parseLong(parsedURL[parsedURL.length - 1]);
					//location.setSpecimenReplicate(new SpecimenReplicateService(BASE_URL).getById(specimenReplicateId, location));
				}
			}
		} catch (Exception e){
			if (e.getMessage() != null) Log.e(LocationService.class.toString(), e.getMessage());
			else Log.e(LocationService.class.toString(), "An error occured while getting the location by ID");
		}
		
		return location;
	}

	public boolean deleteById(long id) {
		final String url = ENTITY_URL + "/id";
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		restTemplate.delete(url, Location.class, id);
		return true;
	}

	public boolean create(Serializable entity) {
		Location location = (Location)entity;
		
		String url = BASE_URL + "user";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		try {
			// create the request body
			String body = new ObjectMapper().writeValueAsString(location);
			// create the request entity
			HttpEntity<?> requestEntity = new HttpEntity<Object>(body, requestHeaders);
			// Get the RestTemplate and add the message converters
			RestTemplate restTemplate = new RestTemplate();

			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			messageConverters.add(new FormHttpMessageConverter());
			messageConverters.add(new StringHttpMessageConverter());
			restTemplate.setMessageConverters(messageConverters);
			try {
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
				HttpStatus status = response.getStatusCode();
				if (status == HttpStatus.CREATED) {
					return true;
				} else {
					return false;
				}
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				return false;
			}
		} catch (JsonProcessingException e1) {
			Log.e(LocationService.class.toString(), "An error occured while converting to JSON");
			return false;
		}
	}

	public Location update(Serializable entity) {
		Location location = (Location)entity;
		
		String url = ENTITY_URL + "/id";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				
		try {
			// create the request body
			String body = new ObjectMapper().writeValueAsString(location);
			// create the request entity
			HttpEntity<?> requestEntity = new HttpEntity<Object>(body,requestHeaders);
			RestTemplate restTemplate = new RestTemplate();

			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			messageConverters.add(new FormHttpMessageConverter());
			messageConverters.add(new StringHttpMessageConverter());
			restTemplate.setMessageConverters(messageConverters);
			try {
				ResponseEntity<Location> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Location.class, location.getId());
				HttpStatus status = response.getStatusCode();
				if (status == HttpStatus.CREATED) {
					return response.getBody();
				} else {
					return null;
				}
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				return null;
			}
		} catch (JsonProcessingException e1) {
			Log.e(LocationService.class.toString(), "An error occured while converting to JSON");
			return null;
		}
	}

	private HttpEntity<?> getRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
		return new HttpEntity<Object>(requestHeaders);
	}
}
