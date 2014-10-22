package com.cuponera.utils;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.cuponera.R;

public class Validator {

	private Context context;
	public Button submitButton;
	private ArrayList<ValidatorInput> inputs = new ArrayList<ValidatorInput>();
	private TextWatcher watcher;

	public enum ValidationType {
		Empty, Email, PostalCode, SpecialCharacters, DistinctToString, Phone
	}

	public Validator(Context context) {
		this.context = context;

		watcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				validateData();
			}
		};

	}

	public void addInput(TextView textView, ValidationType type, boolean required) {
		ValidatorInput validatorInput = new ValidatorInput(textView, type, required);
		addInput(validatorInput);
	}

	public void addInputDistinctToString(TextView textView, String string) {
		ValidatorInput validatorInput = new ValidatorInput(textView, ValidationType.DistinctToString, true);
		validatorInput.validationString = string;
		addInput(validatorInput);
	}

	private void addInput(ValidatorInput input) {
		inputs.add(input);
		input.textView.addTextChangedListener(watcher);
		validateData();
	}

	public void removeInput(TextView textView) {
		for (Iterator<ValidatorInput> it = inputs.iterator(); it.hasNext();) {
			ValidatorInput input = it.next();
			if (input.textView.getId() == textView.getId()) {
				inputs.remove(input);
				input.textView.removeTextChangedListener(watcher);
				break;
			}
		}
		validateData();
	}

	private class ValidatorInput {
		public TextView textView;
		public ValidationType type;
		public int textColor;
		public String validationString;
		public boolean required;

		public ValidatorInput(TextView textView, ValidationType type, boolean required) {
			this.textView = textView;
			this.type = type;
			this.textColor = textView.getCurrentTextColor();
			this.required = required;
		}

	}

	private void validateData() {

		boolean valid = true;

		for (ValidatorInput input : inputs) {

			String text = input.textView.getText().toString();
			TextView textView = input.textView;

			textView.setTextColor(input.textColor);

			if (ValidationUtils.isNullOrEmpty(text)) {
				if (input.required) {
					valid = false;
				}
			} else {

				switch (input.type) {
				case Empty:
					break;
				case Email:
					if (!ValidationUtils.validateMail(text)) {
						valid = false;
					}
					break;
				case PostalCode:
					if (!ValidationUtils.validateZipCode(text)) {
						valid = false;
					}
					break;
				case SpecialCharacters:
					if (!ValidationUtils.validateSpecialCharacters(text)) {
						valid = false;
						textView.setTextColor(context.getResources().getColor(R.color.red));
					}
				case DistinctToString:
					if (input.validationString != null && input.validationString.equalsIgnoreCase(text)) {
						valid = false;
					}
					break;
				case Phone:
					if(!ValidationUtils.validatePhone(text)){
						valid = false;
					}
				}

			}

		}

		submitButton.setClickable(valid);
		submitButton.setEnabled(valid);

	}

}
