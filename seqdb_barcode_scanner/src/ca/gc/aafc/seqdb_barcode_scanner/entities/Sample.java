package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Sample implements Serializable {
	
	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("sampleName")
	private String sampleName;
	@JsonProperty("version")
	private String version;
	@JsonProperty("experimenter")
	private String experimenter;
	@JsonProperty("extractionDate")
	private String extractionDate;
	@JsonProperty("specimenTaxonomyGenus")
	private String specimenTaxonomyGenus;
	@JsonProperty("specimenTaxonomySpecies")
	private String specimenTaxonomySpecies;
	@JsonProperty("mixedSpecimenBiologicalCollection")
	private String mixedSpecimenBiologicalCollection;
	@JsonProperty("mixedSpecimenNumber")
	private long mixedSpecimenNumber;
	@JsonProperty("location")
	private Location location;
	
	public String getSampleName() {
		return sampleName;
	}
	
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getExperimenter() {
		return experimenter;
	}
	
	public void setExperimenter(String experimenter) {
		this.experimenter = experimenter;
	}
	
	public String getExtractionDate() {
		return extractionDate;
	}
	
	public void setExtractionDate(String extractionDate) {
		this.extractionDate = extractionDate;
	}
	
	public String getSpecimenTaxonomyGenus() {
		return specimenTaxonomyGenus;
	}
	
	public void setSpecimenTaxonomyGenus(String specimenTaxonomyGenus) {
		this.specimenTaxonomyGenus = specimenTaxonomyGenus;
	}
	
	public String getSpecimenTaxonomySpecies() {
		return specimenTaxonomySpecies;
	}
	
	public void setSpecimenTaxonomySpecies(String specimenTaxonomySpecies) {
		this.specimenTaxonomySpecies = specimenTaxonomySpecies;
	}
	
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
