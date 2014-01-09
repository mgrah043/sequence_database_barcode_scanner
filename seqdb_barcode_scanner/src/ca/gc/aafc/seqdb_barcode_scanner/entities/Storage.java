package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Storage implements Serializable {
	
	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("storageName")
	private String storageName;
	@JsonProperty("room")
	private String room;
	@JsonProperty("compartmentTypes")
	private String compartmentTypes;
	@JsonProperty("numberOfShelves")
	private int numberOfShelves;
	
	public String getStorageName() {
		return storageName;
	}
	
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getCompartmentTypes() {
		return compartmentTypes;
	}
	
	public void setCompartmentTypes(String compartmentTypes) {
		this.compartmentTypes = compartmentTypes;
	}
	
	public int getNumberOfShelves() {
		return numberOfShelves;
	}
	
	public void setNumberOfShelves(int numberOfShelves) {
		this.numberOfShelves = numberOfShelves;
	}
	
	public long getId() {
		return id;
	}
	
}
