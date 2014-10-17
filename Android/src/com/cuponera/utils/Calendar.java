package com.cuponera.utils;

import java.util.Locale;

public abstract class Calendar extends java.util.Calendar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433534287652933853L;

	public static java.util.Calendar getInstance()
	{
		java.util.Calendar c = java.util.GregorianCalendar.getInstance(Locale.US);
		return c;
	}

}
