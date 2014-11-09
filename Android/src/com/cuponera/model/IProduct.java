package com.cuponera.model;

import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.utils.Utils;

public interface IProduct {
	
	public String getTitle(); 
	public String getDescription();
	public Integer getIdProduct();
	public String getImagePath();
	public Company getCompany(); 

}
