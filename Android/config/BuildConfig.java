package com.payless.service.config;

/**
 * This file <B>WILL BE OVERRIDEN</B> when building with ant
 * <br>If you need to force a given endpoint just set
 * <br>
 * <br>{@code public static Endpoint endpoint = Endpoint.test;}
 * <br>
 * <br>Do not place more functionality here since it will be overriden by ant builder.
 * @author LVidal
 *
 */
public class BuildConfig {

	public static Endpoint endpoint =  Endpoint.@CONFIG.ENDPOINT@;
	
	public static boolean isLoggingEnabled = @CONFIG.LOGGING@;

}
