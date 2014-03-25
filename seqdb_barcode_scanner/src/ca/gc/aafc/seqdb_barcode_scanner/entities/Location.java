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
	
	@JsonProperty("mixedSpecimenUrl")
	private String mixedSpecimenUrl;
	@JsonProperty("pcrPrimerUrl")
	private String pcrPrimerUrl;
	@JsonProperty("sampleUrl")
	private String sampleUrl;
	@JsonProperty("specimenReplicateUrl")
	private String specimenReplicateUrl;
	
	private MixedSpecimen mixedSpecimen;
	private PcrPrimer pcrPrimer;
	private Sample sample;
	private SpecimenReplicate specimenReplicate;
	
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

	public String getMixedSpecimenUrl() {
		return mixedSpecimenUrl;
	}

	public void setMixedSpecimenUrl(String mixedSpecimenUrl) {
		this.mixedSpecimenUrl = mixedSpecimenUrl;
	}

	public String getPcrPrimerUrl() {
		return pcrPrimerUrl;
	}

	public void setPcrPrimerUrl(String pcrPrimerUrl) {
		this.pcrPrimerUrl = pcrPrimerUrl;
	}

	public String getSampleUrl() {
		return sampleUrl;
	}

	public void setSampleUrl(String sampleUrl) {
		this.sampleUrl = sampleUrl;
	}

	public String getSpecimenReplicateUrl() {
		return specimenReplicateUrl;
	}

	public void setSpecimenReplicateUrl(String specimenReplicateUrl) {
		this.specimenReplicateUrl = specimenReplicateUrl;
	}

	public MixedSpecimen getMixedSpecimen() {
		return mixedSpecimen;
	}

	public void setMixedSpecimen(MixedSpecimen mixedSpecimen) {
		this.mixedSpecimen = mixedSpecimen;
	}

	public PcrPrimer getPcrPrimer() {
		return pcrPrimer;
	}

	public void setPcrPrimer(PcrPrimer pcrPrimer) {
		this.pcrPrimer = pcrPrimer;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public SpecimenReplicate getSpecimenReplicate() {
		return specimenReplicate;
	}

	public void setSpecimenReplicate(SpecimenReplicate specimenReplicate) {
		this.specimenReplicate = specimenReplicate;
	}
	
}
