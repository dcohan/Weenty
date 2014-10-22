package com.cuponera.utils;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public abstract class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private static String YEAR = "YEAR";
	private static String MONTH = "MONTH";
	private static String DAY = "DAY";

	public DatePickerFragment(Calendar calendar) {
		super();
		Bundle b = new Bundle();
		b.putInt(YEAR, calendar.get(Calendar.YEAR));
		b.putInt(MONTH, calendar.get(Calendar.MONTH));
		b.putInt(DAY, calendar.get(Calendar.DAY_OF_MONTH));
		setArguments(b);
	}

	public DatePickerFragment() {
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = (getArguments().getInt("YEAR") != 0) ? getArguments().getInt("YEAR") : c.get(Calendar.YEAR);
		int month = (getArguments().getInt("MONTH") != 0) ? getArguments().getInt("MONTH") : c.get(Calendar.MONTH);
		int day = (getArguments().getInt("DAY") != 0) ? getArguments().getInt("DAY") : c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), this, year, month, day);
		DatePicker datePicker = dpDialog.getDatePicker();

		datePicker.setMaxDate(ValidationUtils.getMaxBirthdayDate().getTimeInMillis());
		return dpDialog;
	}

}