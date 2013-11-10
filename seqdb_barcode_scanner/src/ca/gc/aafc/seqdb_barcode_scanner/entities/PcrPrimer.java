package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PcrPrimer {

	private String id;
	private String primerName;
	private String direction;
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
	public String getId() {
		return id;
	}
	
}
