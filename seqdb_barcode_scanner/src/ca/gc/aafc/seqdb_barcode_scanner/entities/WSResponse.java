package ca.gc.aafc.seqdb_barcode_scanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author NazirLKC
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WSResponse {

	@JsonProperty("meta")
	private Meta meta;
	@JsonProperty("pagingPayload")
	private UriList uriList;
	@JsonProperty("countPayload")
	private Count count;
	@JsonProperty("container")
	private Container container;
	@JsonProperty("location")
	private Location location;
	@JsonProperty("mixedSpecimen")
	private MixedSpecimen mixedSpecimen;
	@JsonProperty("pcrPrimer")
	private PcrPrimer pcrPrimer;
	@JsonProperty("sample")
	private Sample sample;
	@JsonProperty("specimenReplicate")
	private SpecimenReplicate specimenReplicate;
	@JsonProperty("storage")
	private Storage storage;
	
	public Meta getMeta() {
		return meta;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	
	public UriList getUriList() {
		return uriList;
	}
	
	public void setUriList(UriList uriList) {
		this.uriList = uriList;
	}
	
	public Count getCount() {
		return count;
	}
	
	public void setCount(Count count) {
		this.count = count;
	}
	
	public Container getContainer() {
		return container;
	}
	
	public void setContainer(Container container) {
		this.container = container;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
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
	
	public Storage getStorage() {
		return storage;
	}
	
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
}
