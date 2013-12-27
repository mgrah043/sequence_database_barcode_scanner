package ca.gc.aafc.seqdb_barcode_scanner.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PcrPrimer implements Serializable {

	@JsonProperty("primaryKey")
	private long id;
	@JsonProperty("primerName")
	private String primerName;
	@JsonProperty("direction")
	private String direction;
	@JsonProperty("location")
	private Location location;
	
	public String getPrimerName() {
		return primerName;
	}
	public void setPrimerName(String primerName) {
		this.primerName = primerName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
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
