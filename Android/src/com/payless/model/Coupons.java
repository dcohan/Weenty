package com.payless.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Coupons extends BaseModel {
	
	@JsonProperty("CouponsLastUpdate")
	private Date couponsLastUpdate;
	
	@JsonProperty("Coupons")
	private List<Coupon> coupons;

	public Date getCouponsLastUpdate() {
		return couponsLastUpdate;
	}

	public void setCouponsLastUpdate(Date couponsLastUpdate) {
		this.couponsLastUpdate = couponsLastUpdate;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	
}
