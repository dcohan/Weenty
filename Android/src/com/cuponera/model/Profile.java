package com.cuponera.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.utils.ValidationUtils;

public class Profile extends BaseModel {

	public enum Gender {
		None, Male, Female
	}

	@JsonProperty("IdProfile")
	private String profileID;

	@JsonProperty("Xid")
	private String XID;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("FirstName")
	private String firsName;

	@JsonProperty("ZipCode")
	private String zipCode;

	@JsonProperty("City")
	private String city;

	@JsonProperty("IdState")
	private String idState;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("PhoneNumber")
	private String phoneNumber;

	@JsonProperty("Gender")
	private Gender gender;

	@JsonProperty("Address_1")
	private String addressOne;

	@JsonProperty("Address_2")
	private String addressTwo;

	@JsonProperty("parsedBirthDate")
	private Date birthDate;

	@JsonProperty("Geolocation")
	private boolean geolocation;

	@JsonProperty("PushNotification")
	private boolean pushNotification;

	@JsonProperty("SMSNotification")
	private boolean smsNotification;

	@JsonProperty("EmailNotification")
	private boolean emailNotification;

	@JsonProperty("PostalNotification")
	private boolean directMailNotification;

	@JsonProperty("IsFirstRun")
	private boolean isFirstRun;

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getXID() {
		return XID;
	}

	public void setXID(String xID) {
		XID = xID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirsName() {
		return firsName;
	}

	public void setFirsName(String firsName) {
		this.firsName = firsName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIdState() {
		return idState;
	}

	public void setIdState(String idState) {
		this.idState = idState;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isGeolocation() {
		return geolocation;
	}

	public void setGeolocation(boolean geolocation) {
		this.geolocation = geolocation;
	}

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(boolean pushNotification) {
		this.pushNotification = pushNotification;
	}

	public boolean isSmsNotification() {
		return smsNotification;
	}

	public void setSmsNotification(boolean smsNotification) {
		this.smsNotification = smsNotification;
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public boolean isDirectMailNotification() {
		return directMailNotification;
	}

	public void setDirectMailNotification(boolean directMailNotification) {
		this.directMailNotification = directMailNotification;
	}

	@JsonProperty("BirthDate")
	public void parseBirthDate(String stringDate) throws ParseException {
		Date date = null;
		if (!ValidationUtils.isNullOrEmpty(stringDate)) {
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			date = dateFormater.parse(stringDate);
		}
		setBirthDate(date);
	}

	public boolean isFirstRun() {
		return isFirstRun;
	}

	public void setFirstRun(boolean isFirstRun) {
		this.isFirstRun = isFirstRun;
	}

}
