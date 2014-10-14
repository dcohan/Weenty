package com.payless.profile;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.payless.BaseFragment;
import com.payless.R;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.helpers.AnalyticsHelper;
import com.payless.model.Profile;
import com.payless.model.Profile.Gender;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.profile.ProfileResponse;
import com.payless.service.profile.UpdateProfileRequest;
import com.payless.settings.Settings;
import com.payless.utils.DatePickerFragment;
import com.payless.utils.KeyValueArrayAdapter;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;
import com.payless.utils.ValidationUtils;
import com.payless.utils.Validator;
import com.payless.utils.Validator.ValidationType;
import com.payless.utils.WebViewFragment;

public class ProfileFragment extends BaseFragment implements HeaderInterface {

	private String keySpinner;
	private String valueSpinner;
	private Date birth_date;

	private TextView firstName;
	private TextView lastName;
	private Button dateSelect;
	private RadioButton maleRadio;
	private RadioButton femaleRadio;
	private TextView cellPhone;
	private TextView email;
	private TextView addressOne;
	private TextView addressTwo;
	private TextView birthdayText;
	private TextView city;
	private TextView stateText;
	private Spinner state;
	private TextView zipCode;
	private CheckBox textMessageCheckbox;
	private CheckBox emailCheckbox;
	private CheckBox directMailCheckbox;
	private CheckBox pushNotificationsCheckbox;
	private Validator validator;

	@Override
	protected int getLayout() {
		return R.layout.fragment_profile;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		firstName = mViewProxy.findTextView(R.id.first_name);
		lastName = mViewProxy.findTextView(R.id.last_name);
		dateSelect = mViewProxy.findButton(R.id.birth_date);
		birthdayText = mViewProxy.findTextView(R.id.birth_text);
		maleRadio = mViewProxy.findRadioButton(R.id.radio_male);
		femaleRadio = mViewProxy.findRadioButton(R.id.radio_female);
		cellPhone = mViewProxy.findTextView(R.id.cell_phone);
		email = mViewProxy.findTextView(R.id.email);
		addressOne = mViewProxy.findTextView(R.id.address_line_1);
		addressTwo = mViewProxy.findTextView(R.id.address_line_2);
		city = mViewProxy.findTextView(R.id.city);
		stateText = mViewProxy.findTextView(R.id.state_text);
		state = mViewProxy.findSpinner(R.id.state);
		zipCode = mViewProxy.findTextView(R.id.zip_code);
		textMessageCheckbox = mViewProxy.findCheckBox(R.id.checkbox_text_message);
		emailCheckbox = mViewProxy.findCheckBox(R.id.checkbox_email);
		directMailCheckbox = mViewProxy.findCheckBox(R.id.checkbox_direct_email);
		pushNotificationsCheckbox = mViewProxy.findCheckBox(R.id.checkbox_push_notifications);

		KeyValueArrayAdapter adapter = new KeyValueArrayAdapter(getActivity(), android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.setKeys(Arrays.asList(getResources().getStringArray(R.array.state_key)));
		adapter.setValues(Arrays.asList(getResources().getStringArray(R.array.state_names)));

		state.setAdapter(adapter);
		state.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg0.getChildAt(0) != null) {
					((TextView) arg0.getChildAt(0)).setTextSize(14f);
					KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) arg0.getAdapter();
					keySpinner = adapter.getKey(arg2);
					valueSpinner = adapter.getValue(arg2);
					stateText.setText(valueSpinner);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		OnClickListener termsListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategory(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.TERMS_AND_CONDITIONS);
				getBaseActivity().openURL(WebViewFragment.TERMS_AND_CONDITIONS, getString(R.string.terms_and_conditions));
			}
		};

		mViewProxy.findTextView(R.id.terms_and_conditions).setOnClickListener(termsListener);
		mViewProxy.findImageView(R.id.help).setOnClickListener(termsListener);

		setSavedValues();

		mViewProxy.findButton(R.id.birth_date).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				Calendar calendar = Calendar.getInstance();
				if (birth_date != null) {
					calendar.setTime(birth_date);
				}
				DatePickerFragment newFragment = new DatePickerFragment(calendar) {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar c = Calendar.getInstance();
						c.set(year, monthOfYear, dayOfMonth);
						birth_date = c.getTime();

						SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
						((Button) v).setText(dateFormat.format(birth_date));
						birthdayText.setText(dateFormat.format(birth_date));

					}

				};
				newFragment.show(getBaseActivity().getFragmentManager(), "datePicker");

			}
		});

		validator = new Validator(getBaseActivity());
		validator.submitButton = mViewProxy.findButton(R.id.submitButton);
		validator.addInput(firstName, ValidationType.SpecialCharacters, true);
		validator.addInput(lastName, ValidationType.SpecialCharacters, true);
		validator.addInput(email, ValidationType.Email, true);
		validator.addInput(birthdayText, ValidationType.Empty, true);

		OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (textMessageCheckbox.isChecked()) {
					mViewProxy.findTextView(R.id.text_message_disclaimer).setVisibility(View.VISIBLE);
					validator.addInput(cellPhone, ValidationType.Phone, true);
				} else {
					mViewProxy.findTextView(R.id.text_message_disclaimer).setVisibility(View.GONE);
					validator.removeInput(cellPhone);
				}

				if (directMailCheckbox.isChecked()) {
					validator.addInput(addressOne, ValidationType.Empty, true);
					validator.addInput(zipCode, ValidationType.PostalCode, true);
					validator.addInput(city, ValidationType.Empty, true);
					validator.addInput(stateText, ValidationType.Empty, true);
					mViewProxy.findTextView(R.id.text_address_disclaimer).setVisibility(View.VISIBLE);
				} else {
					validator.removeInput(addressOne);
					validator.removeInput(zipCode);
					validator.removeInput(city);
					validator.removeInput(stateText);
					mViewProxy.findTextView(R.id.text_address_disclaimer).setVisibility(View.GONE);
				}

			}
		};

		textMessageCheckbox.setOnCheckedChangeListener(checkListener);
		directMailCheckbox.setOnCheckedChangeListener(checkListener);

		mViewProxy.findButton(R.id.submitButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		});

	}

	public void submit() {

		if (formIsValid()) {
			AnalyticsHelper.logEventWithCategory(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.SUBMIT);
			AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.GENDER,
					maleRadio.isChecked() ? String.valueOf(Gender.Male) : String.valueOf(Gender.Female));
			AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.STATES, valueSpinner);
			AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.TEXT_MESSAGE,
					String.valueOf(textMessageCheckbox.isChecked()));
			AnalyticsHelper
					.logEventWithCategoryAndLabel(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.EMAIL, String.valueOf(emailCheckbox.isChecked()));
			AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.MY_PROFILE, AnalyticsHelper.DIRECT_MAIL,
					String.valueOf(directMailCheckbox.isChecked()));

			Utils.hideKeyboard(getBaseActivity(), mViewProxy.getView());

			UpdateProfileRequest request = new UpdateProfileRequest(getBaseActivity()) {

				@Override
				public void loadFailed() {

				}

				@Override
				public void onServiceReturned(ProfileResponse result) {
					super.onServiceReturned(result);
					if (result.succes()) {
						Toast.makeText(context, getString(R.string.profile_thanks), Toast.LENGTH_LONG).show();
					} else {
						EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.SYSTEM_SERVER_ERROR));
					}
				}

				@Override
				public void hideLoading() {
					getBaseActivity().hideLoading();
				}

				@Override
				public void showLoading() {
					getBaseActivity().showLoading();
				}
			};

			request.setFirsName(firstName.getText().toString());
			request.setLastName(lastName.getText().toString());
			if (birth_date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				request.setBirthDate(dateFormat.format(birth_date));
			}
			request.setGender((maleRadio.isChecked()) ? Gender.Male : Gender.Female);
			request.setPhoneNumber(cellPhone.getText().toString());
			request.setEmail(email.getText().toString());
			request.setAddressOne(addressOne.getText().toString());
			request.setAddressTwo(addressTwo.getText().toString());
			request.setCity(city.getText().toString());
			request.setIdState(keySpinner);
			request.setZipCode(zipCode.getText().toString());
			request.setPushNotification(pushNotificationsCheckbox.isChecked());
			request.setSmsNotification(textMessageCheckbox.isChecked());
			request.setMailNotification(emailCheckbox.isChecked());
			request.setPostalNotification(directMailCheckbox.isChecked());

			request.execute();

		}

	}

	@Override
	public String getTitle() {
		return getString(R.string.my_profile);
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return null;
	}

	private void setSavedValues() {
		Profile profile = Settings.getInstance(getBaseActivity()).getProfile();

		firstName.setText(profile.getFirsName());
		lastName.setText(profile.getLastName());
		birth_date = profile.getBirthDate();
		if (birth_date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
			dateSelect.setText(dateFormat.format(birth_date));
			birthdayText.setText(dateFormat.format(birth_date));
		} else {
			birthdayText.setText("");
		}
		if (profile.getGender() == Gender.Female) {
			femaleRadio.setChecked(true);
		} else {
			maleRadio.setChecked(true);
		}
		cellPhone.setText(profile.getPhoneNumber());
		email.setText(profile.getEmail());
		addressOne.setText(profile.getAddressOne());
		addressTwo.setText(profile.getAddressTwo());
		city.setText(profile.getCity());
		if (!Utils.isBlankOrZero(profile.getIdState())) {
			keySpinner = profile.getIdState();
			KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) state.getAdapter();
			for (int i = 0; i < adapter.getCount(); i++) {
				if (adapter.getKey(i).equals(keySpinner)) {
					state.setSelection(i);
					break;
				}
			}
		}
		zipCode.setText(profile.getZipCode());
		pushNotificationsCheckbox.setChecked(profile.isPushNotification());
		textMessageCheckbox.setChecked(profile.isSmsNotification());
		emailCheckbox.setChecked(profile.isEmailNotification());
		directMailCheckbox.setChecked(profile.isDirectMailNotification());

	}

	private boolean formIsValid() {
		if (!ValidationUtils.isNullOrEmpty(email.getText().toString()) && !ValidationUtils.isValidEmailAddress(email.getText().toString())) {
			showError(getString(R.string.invalid_email));
			return false;
		}

		return true;
	}

	private void showError(String msg) {
		Toast.makeText(getBaseActivity(), msg, Toast.LENGTH_LONG).show();
	}

}
