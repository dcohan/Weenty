package com.cuponera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.cuponera.utils.Utils;

public abstract class BaseModel implements Parcelable {
	
	public String serialize() {
		return Utils.serialize(this);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}

}
