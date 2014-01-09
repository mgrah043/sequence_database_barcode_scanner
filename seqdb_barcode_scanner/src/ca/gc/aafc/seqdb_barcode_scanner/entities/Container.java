package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nazir
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Container implements Serializable {

	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("container")
	private int container;
	@JsonProperty("storageUnit")
	private int storageUnit;
	@JsonProperty("compartment")
	private int compartment;
	@JsonProperty("shelf")
	private int shelf;
	@JsonProperty("rack")
	private int rack;
	
	public int getContainer() {
		return container;
	}
	public void setContainer(int container) {
		this.container = container;
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
	public long getId() {
		return id;
	}
	
}
