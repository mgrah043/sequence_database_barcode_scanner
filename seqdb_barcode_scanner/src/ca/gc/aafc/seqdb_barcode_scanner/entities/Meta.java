package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Meta {

	@JsonProperty("ellapsedMillis")
	private long elapsedMillis;
	@JsonProperty("thisUrl")
	private String thisUrl;
	@JsonProperty("debugToggleUrl")
	private String debugToggleUrl;
	@JsonProperty("debug")
	private String debug;
	@JsonProperty("mode")
	private String mode;
	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("status")
	private int status;
	
	public long getElapsedMillis() {
		return elapsedMillis;
	}
	
	public void setElapsedMillis(long elapsedMillis) {
		this.elapsedMillis = elapsedMillis;
	}
	
	public String getThisUrl() {
		return thisUrl;
	}
	
	public void setThisUrl(String thisUrl) {
		this.thisUrl = thisUrl;
	}
	
	public String getDebugToggleUrl() {
		return debugToggleUrl;
	}
	
	public void setDebugToggleUrl(String debugToggleUrl) {
		this.debugToggleUrl = debugToggleUrl;
	}
	
	public String isDebug() {
		return debug;
	}
	
	public void setDebug(String debug) {
		this.debug = debug;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
