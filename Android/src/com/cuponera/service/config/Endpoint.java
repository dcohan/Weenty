package com.cuponera.service.config;

public enum Endpoint {
	production("http", "todovending.com.ar"),

	dev("http", "e-nordelta.com.ar");

	private String apiHost;
	private String apiProtocol;

	private Endpoint(String apiProtocol, String apiHost) {
		this.apiProtocol = apiProtocol;
		this.apiHost = apiHost;
	}

	public String getApiHost() {
		return apiHost;
	}

	public String getApiProtocol() {
		return apiProtocol;
	}

}
