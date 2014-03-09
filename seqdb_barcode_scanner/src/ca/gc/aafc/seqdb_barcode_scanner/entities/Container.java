package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Container implements Serializable {

	@JsonProperty("id")
	private long id;
	@JsonProperty("containerNumber")
	private String containerNumber;
	@JsonProperty("containerType")
	private ContainerType containerType;
	@JsonProperty("locations")
	private String locationsURL;
	
	private ArrayList<Location> locationList;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getContainerNumber() {
		return containerNumber;
	}
	
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	
	public ContainerType getContainerType() {
		return containerType;
	}
	
	public void setContainerType(ContainerType containerType) {
		this.containerType = containerType;
	}
	
	public String getLocationsURL() {
		return locationsURL;
	}
	
	public void setLocationsURL(String locationsURL) {
		this.locationsURL = locationsURL;
	}
	
	public ArrayList<Location> getlocationList() {
		return locationList;
	}
	
	public void setlocationList(ArrayList<Location> locationList) {
		this.locationList = locationList;
	}
}
