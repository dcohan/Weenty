package com.cuponera.service.config;

public enum Endpoint {
	production("https", ""),

	dev("http", "e-nordelta.com.ar"),
	
	local("http","192.168.1.103:6569");

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
