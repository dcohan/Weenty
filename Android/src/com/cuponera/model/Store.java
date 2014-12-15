package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Store extends BaseModel {

	@JsonProperty("Distance")
	private double distance;

	@JsonProperty("IdStore")
	private int idStore;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("ContactNumber")
	private String contactNumber;

	@JsonProperty("Latitude")
	private double latitude;

	@JsonProperty("Longitude")
	private double longitude;

	@JsonProperty("ZipCode")
	private String zipCode;

	@JsonProperty("IdState")
	private int idState;

	@JsonProperty("StoreHours")
	private String storeHours;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("FacebookUrl")
	private String facebookUrl;

	@JsonProperty("WhatsApp")
	private String whatsApp;

	@JsonProperty("ImagePath")
	private String imagePath;

	@JsonProperty("HasOffers")
	private int hasOffers;

	@JsonProperty("IdCategory")
	private int idCategory;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getIdState() {
		return idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}

	public String getStoreHours() {
		return storeHours;
	}

	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getWhatsApp() {
		return whatsApp;
	}

	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean hasOffers() {
		return hasOffers == 1;
	}

	public void setHasOffers(int hasOffers) {
		this.hasOffers = hasOffers;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

}
