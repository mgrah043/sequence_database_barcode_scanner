package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Sample {
	
	private String id;
	private String sampleName;
	private String version;
	private String experimenter;
	private String extractionDate;
	private String specimenTaxonomyGenus;
	private String specimenTaxonomySpecies;
	private String mixedSpecimenBiologicalCollection;
	private String mixedSpecimenNumber;
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
