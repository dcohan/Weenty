package com.payless.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;

public class Item extends BaseModel {

	@JsonProperty("IdStore")
	private int idStore;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("City")
	private String city;

	@JsonProperty("State")
	private String state;

	@JsonProperty("ContactNumber")
	private String contactNumber;

	@JsonProperty("Distance")
	private double distance;

	@JsonProperty("Location")
	private ItemLocation location;

	@JsonProperty("StoreHours")
	private ArrayList<StoreHours> storeHours;

	public Item() {
	}

	public Item(Parcel source) {
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public ArrayList<StoreHours> getStoreHours() {
		return storeHours;
	}

	public void setStoreHours(ArrayList<StoreHours> storeHours) {
		this.storeHours = storeHours;
	}

	public ItemLocation getLocation() {
		return location;
	}

	public void setLocation(ItemLocation location) {
		this.location = location;
	}
}
