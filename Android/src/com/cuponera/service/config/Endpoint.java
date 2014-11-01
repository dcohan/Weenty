package com.cuponera.service.config;

public enum Endpoint {
	production("https", ""),

	dev("http", ""),
	
	local("http","192.168.1.40:6569");

	private String apiHost;
	private String apiProtocol;
	private String apiHostDGTP;
	private String apiProtocolDGTP;

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

	public String getApiHostDGTP() {
		return apiHostDGTP;
	}

	public String getApiProtocolDGTP() {
		return apiProtocolDGTP;
	}
}
