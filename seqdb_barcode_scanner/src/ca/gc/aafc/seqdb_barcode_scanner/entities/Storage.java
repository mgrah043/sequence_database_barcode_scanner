package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Storage {
	
	private String id;
	private String storageName;
	private String room;
	private String compartmentTypes;
	private String numberOfShelves;
	
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
	public String getNumberOfShelves() {
		return numberOfShelves;
	}
	public void setNumberOfShelves(String numberOfShelves) {
		this.numberOfShelves = numberOfShelves;
	}
	public String getId() {
		return id;
	}
	
}
