package com.cuponera.service.profile;

import java.util.Map;

import android.content.Context;

import com.cuponera.model.Profile;
import com.cuponera.model.Profile.Gender;
import com.cuponera.pool.AsyncPoolLoader.HTTPMethod;
import com.cuponera.settings.Settings;
import com.cuponera.utils.ValidationUtils;

public abstract class UpdateProfileRequest extends ProfileRequest {

	private String lastName;
	private String firsName;
	private String zipCode;
	private String city;
	private String idState;
	private String email;
	private String phoneNumber;
	private Gender gender;
	private String addressOne;
	private String addressTwo;
	private String birthDate;
	private double latitude;
	private double longitude;
	private boolean smsNotification;
	private boolean mailNotification;
	private boolean postalNotification;


	public UpdateProfileRequest(Context context) {
		super(context);
		Profile profile = Settings.getInstance(context).getProfile();
		if(profile != null) {
			setSmsNotification(profile.isSmsNotification());
			setMailNotification(profile.isEmailNotification());
			setPostalNotification(profile.isDirectMailNotification());
		}
	}

	@Override
	public Map<String, Object> getUriParams() {
		Map<String, Object> params = super.getUriParams();
		if (!ValidationUtils.isNull(getFirsName())) {
			params.put("FirstName", getFirsName());
		}
		if (!ValidationUtils.isNull(getLastName())) {
			params.put("LastName", getLastName());
		}
		if (!ValidationUtils.isNull(getEmail())) {
			params.put("Email", getEmail());
		}
		if (!ValidationUtils.isNull(getPhoneNumber())) {
			params.put("PhoneNumber", getPhoneNumber());
		}
		if (!ValidationUtils.isNull(getBirthDate())) {
			params.put("BirthDate", getBirthDate());
		}
		if (getGender() == Gender.Male) {
			params.put("Gender", "Male");
		} else if(getGender() == Gender.Female){
			params.put("Gender", "Female");
		}
		if (!ValidationUtils.isNull(getAddressOne())) {
			params.put("Address_1", getAddressOne());
		}
		if (!ValidationUtils.isNull(getAddressTwo())) {
			params.put("Address_2", getAddressTwo());
		}
		if (!ValidationUtils.isNull(getCity())) {
			params.put("City", getCity());
		}
		if (!ValidationUtils.isNull(getIdState())) {
			params.put("IdState", getIdState());
		}
		if (getLatitude() != 0) {
			params.put("Latitude", getLatitude());
		}
		if (getLongitude() != 0) {
			params.put("Longitude", getLongitude());
		}
		if (!ValidationUtils.isNull(getZipCode())){
			params.put("ZipCode", getZipCode());
		}

		params.put("SMSNotification", isSmsNotification());
		params.put("EmailNotification", isMailNotification());
		params.put("PostalNotification", isPostalNotification());
		
		return params;
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.HTTPPut;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}

	public boolean isSmsNotification() {
		return smsNotification;
	}

	public void setSmsNotification(boolean smsNotification) {
		this.smsNotification = smsNotification;
	}

	public boolean isMailNotification() {
		return mailNotification;
	}

	public void setMailNotification(boolean mailNotification) {
		this.mailNotification = mailNotification;
	}

	public boolean isPostalNotification() {
		return postalNotification;
	}

	public void setPostalNotification(boolean postalNotification) {
		this.postalNotification = postalNotification;
	}

}
