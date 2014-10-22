package com.cuponera.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class ValidationUtils {

	private static final String DATE_PATTERN_INPUT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_PATTERN_OUTPUT = "MMMM dd, yyyy";
	private static final int EMAIL_OFFERS_MINIMUM_AGE_IN_YEARS = 13;
	private static final String NAME_REGEXP = "[a-zA-Z\\s'-]*";
	private static final String SPECIAL_CHARS_REGEXP = "[a-zA-Z0-9\\s]*";
	private static final String ZIP_CODE_REGEXP = "(^\\d{5}(-\\d{4})?$)|(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1} *\\d{1}[A-Za-z]{1}\\d{1}$)";
	private static final String PHONE_REGEXP = "^[2-9]\\d{2}\\d{3}\\d{4}$";
	private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]{2,})$";

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.trim().equals("");
	}

	public static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isNull(String string) {
		return string == null;
	}

	public static String formattedDateFromString(String inputDate) {
		String outputDate = "";
		SimpleDateFormat df_input = new SimpleDateFormat(DATE_PATTERN_INPUT, java.util.Locale.getDefault());
		SimpleDateFormat df_output = new SimpleDateFormat(DATE_PATTERN_OUTPUT, java.util.Locale.getDefault());

		try {
			outputDate = df_output.format(df_input.parse(inputDate));
		} catch (Exception e) {
			Log.e("Error", "Exception in formateDateFromstring(): " + e.getMessage());
		}
		return outputDate;

	}

	public static boolean isValidEmailAddress(String emailAddress) {
		if (isNullOrEmpty(emailAddress)) {
			return false;
		}
		String expression = EMAIL_REGEXP;
		CharSequence inputStr = emailAddress;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	public static boolean validateZipCode(String string) {
		if (isNullOrEmpty(string)) {
			return false;
		}

		return string.matches(ZIP_CODE_REGEXP);
	}

	public static boolean validatePhone(String string) {
		if (isNullOrEmpty(string)) {
			return false;
		}

		return string.matches(PHONE_REGEXP) && string.length() == 10;
	}

	public static boolean validateName(String string) {
		if (isNullOrEmpty(string)) {
			return true;
		}

		return string.matches(NAME_REGEXP);
	}

	public static boolean validateMail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}

		return isValidEmailAddress(email);
	}

	public static boolean validateSpecialCharacters(String text) {
		String expression = SPECIAL_CHARS_REGEXP;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	public static Calendar getMaxBirthdayDate() {
		Calendar date = com.cuponera.utils.Calendar.getInstance();
		date.add(Calendar.YEAR, -EMAIL_OFFERS_MINIMUM_AGE_IN_YEARS);
		return date;
	}
	
}
