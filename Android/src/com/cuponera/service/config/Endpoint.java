package com.cuponera.service.config;

public enum Endpoint {
	production("https", "payless.emerios.com/Emerios/Payless/v1/"),

	uat("http", "payless.uat.emerios.com/Emerios/Payless/v1/"),

	preproduction("http", "payless.pre.emerios.com/Emerios/Payless/v1/"),

	test("http", "payless.test.emerios.com/Emerios/Payless/v1/"),

	dev("http", "payless.dev.emerios.com/Emerios/Payless/v1/"),
	
	local("http","10.10.3.90/Emerios/Payless/v1/");

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
