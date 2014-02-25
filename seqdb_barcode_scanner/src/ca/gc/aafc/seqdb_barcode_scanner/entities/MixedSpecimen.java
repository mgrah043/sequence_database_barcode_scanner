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

	@JsonProperty("id")
	private long id;
	@JsonProperty("fungiIsolated")
	private String fungiIsolated;
	@JsonProperty("notes")
	private String notes;
	@JsonProperty("treatment")
	private String treatment;
	@JsonProperty("project")
	private String project;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFungiIsolated() {
		return fungiIsolated;
	}
	
	public void setFungiIsolated(String fungiIsolated) {
		this.fungiIsolated = fungiIsolated;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getTreatment() {
		return treatment;
	}
	
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
}
