package com.cuponera.service.state;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cuponera.model.State;
import com.cuponera.service.BaseResponse;

public class StateResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonProperty("value")
	private ArrayList<State> state;

	public ArrayList<State> getState() {
		return state;
	}

	public void setState(ArrayList<State> state) {
		this.state = state;
	}

}
