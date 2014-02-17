package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ContainerType implements Serializable{

	@JsonProperty("id")
	private long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("baseType")
	private String baseType;
	@JsonProperty("numberOfWells")
	private int numberOfWells;
	@JsonProperty("numberOfColumns")
	private int numberOfColumns;
	@JsonProperty("numberOfRows")
	private int numberOfRows;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBaseType() {
		return baseType;
	}
	
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	
	public int getNumberOfWells() {
		return numberOfWells;
	}
	
	public void setNumberOfWells(int numberOfWells) {
		this.numberOfWells = numberOfWells;
	}
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
}
