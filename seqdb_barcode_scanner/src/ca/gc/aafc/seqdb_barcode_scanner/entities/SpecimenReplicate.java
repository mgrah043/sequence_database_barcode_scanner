package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SpecimenReplicate implements Serializable{

	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("state")
	private String state;
	@JsonProperty("specimenIdentifier")
	private long specimenIdentifier;
	@JsonProperty("version")
	private String version;
	@JsonProperty("contents")
	private String contents;
	@JsonProperty("notes")
	private String notes;
	@JsonProperty("storageMedium")
	private String storageMedium;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("revivalDate")
	private String revivalDate;
	@JsonProperty("dateDestroyed")
	private String dateDestroyed;
	@JsonProperty("location")
	private Location location;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public long getSpecimenIdentifier() {
		return specimenIdentifier;
	}
	
	public void setSpecimenIdentifier(long specimenIdentifier) {
		this.specimenIdentifier = specimenIdentifier;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getStorageMedium() {
		return storageMedium;
	}
	
	public void setStorageMedium(String storageMedium) {
		this.storageMedium = storageMedium;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getRevivalDate() {
		return revivalDate;
	}
	
	public void setRevivalDate(String revivalDate) {
		this.revivalDate = revivalDate;
	}
	
	public String getDateDestroyed() {
		return dateDestroyed;
	}
	
	public void setDateDestroyed(String dateDestroyed) {
		this.dateDestroyed = dateDestroyed;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public long getId() {
		return id;
	}
}
