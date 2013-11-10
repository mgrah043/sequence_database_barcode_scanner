package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MixedSpecimen {

	private String id;
	private String mixedSpecimenBiologicalCollection;
	private String mixedSpecimenNumber;
	private Location location;
	
	public String getMixedSpecimenBiologicalCollection() {
		return mixedSpecimenBiologicalCollection;
	}
	public void setMixedSpecimenBiologicalCollection(
			String mixedSpecimenBiologicalCollection) {
		this.mixedSpecimenBiologicalCollection = mixedSpecimenBiologicalCollection;
	}
	public String getMixedSpecimenNumber() {
		return mixedSpecimenNumber;
	}
	public void setMixedSpecimenNumber(String mixedSpecimenNumber) {
		this.mixedSpecimenNumber = mixedSpecimenNumber;
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
