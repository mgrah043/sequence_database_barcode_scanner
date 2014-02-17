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

	@JsonProperty("id")
	private Long id;
	@JsonProperty("wellColumn")
	private int wellColumn;
	@JsonProperty("wellRow")
	private String wellRow;
	@JsonProperty("containerId")
	private Long containerId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getWellColumn() {
		return wellColumn;
	}
	
	public void setWellColumn(int wellColumn) {
		this.wellColumn = wellColumn;
	}
	
	public String getWellRow() {
		return wellRow;
	}
	
	public void setWellRow(String wellRow) {
		this.wellRow = wellRow;
	}
	
	public Long getContainerId() {
		return containerId;
	}
	
	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}
}
