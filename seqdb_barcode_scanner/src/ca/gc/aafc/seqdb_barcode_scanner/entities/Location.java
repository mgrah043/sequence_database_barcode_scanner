package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Location implements Serializable {

	@JsonProperty("primaryKey")
	private Long id;
	@JsonProperty("containerNumber")
	private int containerNumber;
	@JsonProperty("storageUnit")
	private int storageUnit;
	@JsonProperty("compartment")
	private int compartment;
	@JsonProperty("shelf")
	private int shelf;
	@JsonProperty("rack")
	private int rack;
	@JsonProperty("dateMoved")
	private String dateMoved;
	@JsonProperty("wellColumn")
	private String wellColumn;
	@JsonProperty("wellRow")
	private String wellRow;
	
	public int getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(int containerNumber) {
		this.containerNumber = containerNumber;
	}

	public int getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(int storageUnit) {
		this.storageUnit = storageUnit;
	}

	public int getCompartment() {
		return compartment;
	}

	public void setCompartment(int compartment) {
		this.compartment = compartment;
	}

	public int getShelf() {
		return shelf;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}

	public int getRack() {
		return rack;
	}

	public void setRack(int rack) {
		this.rack = rack;
	}

	public String getDateMoved() {
		return dateMoved;
	}

	public void setDateMoved(String dateMoved) {
		this.dateMoved = dateMoved;
	}

	public String getWellColumn() {
		return wellColumn;
	}

	public void setWellColumn(String wellColumn) {
		this.wellColumn = wellColumn;
	}

	public String getWellRow() {
		return wellRow;
	}

	public void setWellRow(String wellRow) {
		this.wellRow = wellRow;
	}

	public long getId() {
		return id;
	}
	
}
