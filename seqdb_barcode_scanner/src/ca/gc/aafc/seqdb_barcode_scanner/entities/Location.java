package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Location {

	private String id;
	private String containerNumber;
	private String well;
	private String storageUnit;
	private String shelf;
	private String room;
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getWell() {
		return well;
	}
	public void setWell(String well) {
		this.well = well;
	}
	public String getStorageUnit() {
		return storageUnit;
	}
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getId() {
		return id;
	}
	
	
}
