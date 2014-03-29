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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Container;
import ca.gc.aafc.seqdb_barcode_scanner.entities.UriList;
import ca.gc.aafc.seqdb_barcode_scanner.entities.WSResponse;

/**
 * @author NazirLKC
 *
 */
public class ContainerService implements EntityServiceI{

	private String BASE_URL;
	private String ENTITY_URL;
	
	public ContainerService(String serverURL){
		BASE_URL = serverURL;
		ENTITY_URL = BASE_URL + "/container";
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
			if (e.getMessage() != null) Log.e(ContainerService.class.toString(), e.getMessage());
			else Log.e(ContainerService.class.toString(), "An error occured while getting the container count");
		}

		return count;
	}

	public ArrayList<Container> getAll() {
		final String url = ENTITY_URL;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		// This is where we call the exchange method and process the response
		ResponseEntity<WSResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), WSResponse.class);
		
		ArrayList<Container> containers = new ArrayList<Container>();
		WSResponse wsResponse = responseEntity.getBody();
		// check for errors
		if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
			UriList uriList = wsResponse.getUriList();
			// iterate over list of URIs and get the containers
			for (UriList.UrlPath containerURL : uriList.getUris()){
				long id = Long.parseLong(containerURL.getUrlPath());
				// get container by id and add to list
				containers.add(getById(id));
			}
			
			// If there are more pages of URIs, get them
			while (uriList.getNextPageUrl() != null){
				// get the next set of URLs
				responseEntity = restTemplate.exchange(uriList.getNextPageUrl(), HttpMethod.GET, getRequestEntity(), WSResponse.class);
				wsResponse = responseEntity.getBody();
				uriList = wsResponse.getUriList();
				// iterate over list of URIs and get the containers
				for (UriList.UrlPath containerURL : uriList.getUris()){
					long id = Long.parseLong(containerURL.getUrlPath());
					// get container by id and add to list
					containers.add(getById(id));
				}
			}
		}
		
		return containers;
	}

	public Container getById(long id) {
		Container container = null;
		// The URL for making the GET request
		final String url = ENTITY_URL + "/" + id;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		try {
			WSResponse wsResponse = restTemplate.getForObject(url, WSResponse.class, id);
			// check for errors
			if (wsResponse.getMeta() != null && wsResponse.getMeta().getStatus() == 200){
				container = wsResponse.getContainer();
				// get locations
				container.setlocationList(new LocationService(BASE_URL).getAll(container.getLocationsURL()));
			}
		} catch (Exception e){
			if (e.getMessage() != null) Log.e(ContainerService.class.toString(), e.getMessage());
			else Log.e(ContainerService.class.toString(), "An error occured while getting the container by ID");
		} 
		
		return container;
	}

	public boolean deleteById(long id) {
		final String url = ENTITY_URL + "/" + id;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		restTemplate.delete(url, Container.class, id);
		return true;
	}

	public boolean create(Serializable entity) {
		Container container = (Container)entity;
		
		String url = ENTITY_URL;
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			// create the request body
			String body = new ObjectMapper().writeValueAsString(container);
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
			Log.e(ContainerService.class.toString(), "An error occured while converting to JSON");
			return false;
		}
	}

	public Container update(Serializable entity) {
		Container container = (Container)entity;
		
		String url = ENTITY_URL + "/" + container.getId();
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		try {
			// create the request body
			String body = new ObjectMapper().writeValueAsString(container);
			// create the request entity
			HttpEntity<?> requestEntity = new HttpEntity<Object>(body,requestHeaders);
			RestTemplate restTemplate = new RestTemplate();

			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			messageConverters.add(new FormHttpMessageConverter());
			messageConverters.add(new StringHttpMessageConverter());
			restTemplate.setMessageConverters(messageConverters);
			try {
				ResponseEntity<Container> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Container.class, container.getId());
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
			Log.e(ContainerService.class.toString(), "An error occured while converting to JSON");
			return null;
		}
	}

	private HttpEntity<?> getRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
		return new HttpEntity<Object>(requestHeaders);
	}
}
