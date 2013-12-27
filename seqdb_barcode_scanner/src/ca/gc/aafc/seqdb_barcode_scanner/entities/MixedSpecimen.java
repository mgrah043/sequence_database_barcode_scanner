package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MixedSpecimen implements Serializable {

	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("mixedSpecimenBiologicalCollection")
	private String mixedSpecimenBiologicalCollection;
	@JsonProperty("mixedSpecimenNumber")
	private long mixedSpecimenNumber;
	@JsonProperty("location")
	private Location location;
	
	public String getMixedSpecimenBiologicalCollection() {
		return mixedSpecimenBiologicalCollection;
	}
	
	public void setMixedSpecimenBiologicalCollection(
			String mixedSpecimenBiologicalCollection) {
		this.mixedSpecimenBiologicalCollection = mixedSpecimenBiologicalCollection;
	}
	
	public long getMixedSpecimenNumber() {
		return mixedSpecimenNumber;
	}
	
	public void setMixedSpecimenNumber(long mixedSpecimenNumber) {
		this.mixedSpecimenNumber = mixedSpecimenNumber;
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
