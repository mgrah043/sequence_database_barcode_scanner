package ca.gc.aafc.seqdb_barcode_scanner.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ca.gc.aafc.seqdb_barcode_scanner.entities.Count;
import ca.gc.aafc.seqdb_barcode_scanner.entities.SpecimenReplicate;
import ca.gc.aafc.seqdb_barcode_scanner.entities.UriList;

/**
 * @author NazirLKC
 *
 */
public class SpecimenReplicateService implements EntityServiceI{

	// TODO read this value from the config file
	private static final String BASE_URL = "http://localhost:4567/v1";
	private static final String ENTITY_URL = BASE_URL + "/specimenReplicate";
	
	public long getCount() {
		// The URL for making the GET request
		final String url = ENTITY_URL + "/count/";
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

		return restTemplate.getForObject(url, Count.class).getCount();
	}

	public ArrayList<SpecimenReplicate> getAll() {
		final String url = ENTITY_URL;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		// This is where we call the exchange method and process the response
		ResponseEntity<UriList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), UriList.class);
		
		ArrayList<SpecimenReplicate> specimenReplicates = new ArrayList<SpecimenReplicate>();
		UriList uriList = responseEntity.getBody();
		
		// iterate over list of URLs to get all entities
		while (uriList.getNextPageUrl() != null){
			for (String specimenReplicateURL : uriList.getUris()){
				// parse the id from the URL
				String[] partialURL = specimenReplicateURL.split("/");
				long id = Long.parseLong(partialURL[partialURL.length-1]);
				// get specimen replicate by id and add to list
				specimenReplicates.add(getById(id));
			}
			// get the next set of URLs
			responseEntity = restTemplate.exchange(uriList.getNextPageUrl(), HttpMethod.GET, getRequestEntity(), UriList.class);
			uriList = responseEntity.getBody();
		}
		
		return specimenReplicates;
	}

	public SpecimenReplicate getById(long id) {
		// The URL for making the GET request
		final String url = ENTITY_URL + "/" + id;
		// This is where we get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

		return restTemplate.getForObject(url, SpecimenReplicate.class, id);
	}

	private HttpEntity<?> getRequestEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
		return new HttpEntity<Object>(requestHeaders);
	}
}
