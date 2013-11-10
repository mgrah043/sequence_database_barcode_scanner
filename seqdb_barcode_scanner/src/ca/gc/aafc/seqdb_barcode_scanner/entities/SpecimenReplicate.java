package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SpecimenReplicate {

	private String id;
	private String biologicalCollectionName;
	private String specimenIdentifier;
	private String version;
	private Location location;
	
	public String getBiologicalCollectionName() {
		return biologicalCollectionName;
	}
	public void setBiologicalCollectionName(String biologicalCollectionName) {
		this.biologicalCollectionName = biologicalCollectionName;
	}
	public String getSpecimenIdentifier() {
		return specimenIdentifier;
	}
	public void setSpecimenIdentifier(String specimenIdentifier) {
		this.specimenIdentifier = specimenIdentifier;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getId() {
		return id;
	}
}
