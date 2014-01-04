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

import ca.gc.aafc.seqdb_barcode_scanner.entities.Count;
import ca.gc.aafc.seqdb_barcode_scanner.entities.MixedSpecimen;
import ca.gc.aafc.seqdb_barcode_scanner.entities.UriList;

/**
 * @author NazirLKC
 *
 */
public class MixedSpecimenService implements EntityServiceI{

	// TODO read this value from the config file
	private String BASE_URL;
	private String ENTITY_URL;
	
	public MixedSpecimenService(String serverURL){
		BASE_URL = serverURL;
		ENTITY_URL = BASE_URL + "/mixedSpecimen";
	}
	
	public long getCount() {
		// The URL for making the GET request
		final String url = ENTITY_URL + "/count/";
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate.getForObject(url, Count.class).getCount();
	}

	public ArrayList<MixedSpecimen> getAll() {
		final String url = ENTITY_URL;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		// This is where we call the exchange method and process the response
		ResponseEntity<UriList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), UriList.class);
		
		ArrayList<MixedSpecimen> mixedSpecimens = new ArrayList<MixedSpecimen>();
		UriList uriList = responseEntity.getBody();
		
		// iterate over list of URLs to get all entities
		while (uriList.getNextPageUrl() != null){
			for (String mixedSpecimenURL : uriList.getUris()){
				// parse the id from the URL
				String[] partialURL = mixedSpecimenURL.split("/");
				long id = Long.parseLong(partialURL[partialURL.length-1]);
				// get mixed specimen by id and add to list
				mixedSpecimens.add(getById(id));
			}
			// get the next set of URLs
			responseEntity = restTemplate.exchange(uriList.getNextPageUrl(), HttpMethod.GET, getRequestEntity(), UriList.class);
			uriList = responseEntity.getBody();
		}
		
		return mixedSpecimens;
	}

	public MixedSpecimen getById(long id) {
		// The URL for making the GET request
		final String url = ENTITY_URL + "/" + id;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate.getForObject(url, MixedSpecimen.class, id);
	}

	public boolean deleteById(long id) {
		final String url = ENTITY_URL + "/id";
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		restTemplate.delete(url, MixedSpecimen.class, id);
		return true;
	}

	public boolean create(Serializable entity) {
		MixedSpecimen mixedSpecimen = (MixedSpecimen)entity;
		
		String url = BASE_URL + "user";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// create the request body
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("mixedSpecimen[id]", String.valueOf(mixedSpecimen.getId()));
		// body.add("mixedSpecimen[first_name]", mixedSpecimen.getFirstName());
		// body.add("mixedSpecimen[last_name]",mixedSpecimen.getLastName());

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

	}

	public MixedSpecimen update(Serializable entity) {
		MixedSpecimen mixedSpecimen = (MixedSpecimen)entity;
		
		String url = ENTITY_URL + "/id";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("mixedSpecimen[id]", String.valueOf(mixedSpecimen.getId()));
//		body.add("mixedSpecimen[first_name]",mixedSpecimen.getFirstName());
//		body.add("mixedSpecimen[last_name]",mixedSpecimen.getLastName());

		// create the request entity
		HttpEntity<?> requestEntity = new HttpEntity<Object>(body,requestHeaders);
		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		try {
			ResponseEntity<MixedSpecimen> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, MixedSpecimen.class, mixedSpecimen.getId());
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
	}

	private HttpEntity<?> getRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
		return new HttpEntity<Object>(requestHeaders);
	}
}
