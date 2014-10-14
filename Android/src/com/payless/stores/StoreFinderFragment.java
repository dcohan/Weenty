package com.payless.stores;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cuponera.R;
import com.payless.BaseFragment;
import com.payless.event.ErrorEvent;
import com.payless.event.EventBus;
import com.payless.helpers.AnalyticsHelper;
import com.payless.navigation.HeaderImageInterface;
import com.payless.navigation.HeaderInterface;
import com.payless.service.stores.StoresRequest;
import com.payless.service.stores.StoresResponse;
import com.payless.utils.KeyValueArrayAdapter;
import com.payless.utils.LocationServices;
import com.payless.utils.LocationServices.RequestLocationListener;
import com.payless.utils.PaylessErrorHandler;
import com.payless.utils.Utils;
import com.payless.utils.ValidationUtils;

public class StoreFinderFragment extends BaseFragment implements HeaderInterface {

	private Spinner stateSpinner;
	private StoresRequest request;
	private String keySpinner;
	private String valueSpinner;

	private EditText zipCodeEdit;
	private EditText cityEdit;
	private RadioButton radioCity;
	private RadioButton radioZipCode;
	private RadioButton radioLocation;
	private ProgressDialog loadingDialog;

	@Override
	protected int getLayout() {
		return R.layout.fragment_stores;
	}

	@Override
	public String getTitle() {
		return getString(R.string.stores_title);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		stateSpinner = mViewProxy.findSpinner(R.id.stateSpinner);

		radioCity = mViewProxy.findRadioButton(R.id.radio_city);
		radioZipCode = mViewProxy.findRadioButton(R.id.radio_zipcode);
		radioLocation = mViewProxy.findRadioButton(R.id.radio_location);
		zipCodeEdit = mViewProxy.findEditText(R.id.zipcode_text);
		cityEdit = mViewProxy.findEditText(R.id.city_text);

		KeyValueArrayAdapter adapter = new KeyValueArrayAdapter(getActivity(), android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.setKeys(Arrays.asList(getResources().getStringArray(R.array.state_key)));
		adapter.setValues(Arrays.asList(getResources().getStringArray(R.array.state_names)));

//	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//	        R.array.state_names, android.R.layout.simple_spinner_item);

	    
		stateSpinner.setAdapter(adapter);
		stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg0.getChildAt(0) != null) {
					((TextView) arg0.getChildAt(0)).setTextSize(14f);
					KeyValueArrayAdapter adapter = (KeyValueArrayAdapter) arg0.getAdapter();
					keySpinner = adapter.getKey(arg2);
					valueSpinner = adapter.getValue(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				updateCheckBox(true, false, false);
			}
		});

		mViewProxy.findButton(R.id.clear_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnalyticsHelper.logEventWithCategory(AnalyticsHelper.STORE_FINDER, AnalyticsHelper.CLEAR);
				cityEdit.setText("");
				zipCodeEdit.setText("");
				stateSpinner.setSelection(0);
				Utils.hideKeyboard(getActivity(), getView());
			}
		});

		mViewProxy.findButton(R.id.search_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateData()) {
					final ArrayList<StoresRequest> requestParams = new ArrayList<StoresRequest>();
					request = new StoresRequest(getActivity()) {
						@Override
						public void onServiceReturned(StoresResponse result) {
							if (result.succes() && result.getTotal() > 0) {
								getBaseActivity().pushFragment(StoreFinderSearchViewFragment.newInstance(result.getItems(), requestParams), true);
							} else {
								EventBus.getInstance().dispatchEvent(new ErrorEvent(0, PaylessErrorHandler.NO_RESULTS_FOUND));
							}
						}

						@Override
						public void loadWasCancelled() {
						}

						@Override
						public void loadFailed() {
						}

					};
					Utils.hideKeyboard(getActivity(), getView());

					if (radioCity.isChecked()) {
						AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.STORE_FINDER, AnalyticsHelper.CITY_STATE, valueSpinner + "-"
								+ cityEdit.getText().toString());
						request.setCityName(cityEdit.getText().toString().trim());
						request.setState(keySpinner);
						request.execute();
					} else if (radioZipCode.isChecked()) {
						AnalyticsHelper.logEventWithCategoryAndLabel(AnalyticsHelper.STORE_FINDER, AnalyticsHelper.ZIP_CODE, zipCodeEdit.getText()
								.toString().trim());
						request.setZipCode(zipCodeEdit.getText().toString().trim());
						request.execute();
					} else {
						AnalyticsHelper.logEventWithCategory(AnalyticsHelper.STORE_FINDER, AnalyticsHelper.GPS);
						showLoadingLocation(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								request = null;
							}

						});
						LocationServices.getInstance(getActivity()).requestAccurateLocation(new RequestLocationListener() {

							@Override
							public void onLocationReceived(Location location) {
								hideLoadingLocation();
								if (request != null && location != null) {
									request.setLatitude(location.getLatitude());
									request.setLongitud(location.getLongitude());
									request.execute();
								}
							}

						});
					}
					requestParams.add(request);
				}
			}
		});

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.city_layout:
					updateCheckBox(true, false, false);
					break;
				case R.id.location_layout:
					updateCheckBox(false, false, true);
					break;
				case R.id.zipcode_layout:
					updateCheckBox(false, true, false);
					break;
				}

			}
		};

		OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					switch (buttonView.getId()) {
					case R.id.radio_city:
						updateCheckBox(true, false, false);
						break;
					case R.id.radio_location:
						updateCheckBox(false, false, true);
						break;
					case R.id.radio_zipcode:
						updateCheckBox(false, true, false);
						break;

					}
				}
			}
		};
		OnFocusChangeListener editListener = new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					switch (v.getId()) {
					case R.id.city_text:
						updateCheckBox(true, false, false);
						break;
					case R.id.zipcode_text:
						updateCheckBox(false, true, false);
						break;

					}
				}
			}
		};
		
		stateSpinner.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				updateCheckBox(true, false, false);
				return false;
			}
		});

		mViewProxy.findLinearLayout(R.id.city_layout).setOnClickListener(listener);
		mViewProxy.findLinearLayout(R.id.location_layout).setOnClickListener(listener);
		mViewProxy.findLinearLayout(R.id.zipcode_layout).setOnClickListener(listener);
		cityEdit.setOnFocusChangeListener(editListener);
		zipCodeEdit.setOnFocusChangeListener(editListener);
		radioCity.setOnCheckedChangeListener(checkListener);
		radioZipCode.setOnCheckedChangeListener(checkListener);
		radioLocation.setOnCheckedChangeListener(checkListener);

	}

	private void updateCheckBox(boolean checkedCity, boolean checkedZip, boolean checkedLocation) {
		radioCity.setChecked(checkedCity);
		radioZipCode.setChecked(checkedZip);
		radioLocation.setChecked(checkedLocation);

		if (checkedCity) {
			Utils.showKeyboard(getActivity(), cityEdit);
			zipCodeEdit.setText("");
			cityEdit.requestFocus();
			zipCodeEdit.clearFocus();
		} else if (checkedZip) {
			Utils.showKeyboard(getActivity(), zipCodeEdit);
			zipCodeEdit.requestFocus();
			stateSpinner.setSelection(0);
			cityEdit.setText("");
			cityEdit.clearFocus();
		} else {
			Utils.hideKeyboard(getActivity(), getView());
			stateSpinner.setSelection(0);
			zipCodeEdit.setText("");
			cityEdit.setText("");
		}

		radioLocation.setChecked(checkedLocation);
	}

	private boolean validateData() {
		if (radioCity.isChecked() && (ValidationUtils.isNullOrEmpty(cityEdit.getText().toString()) || stateSpinner.getSelectedItemPosition() == 0)) {
			Toast.makeText(getActivity(), getResources().getString(R.string.enter_city_and_state), Toast.LENGTH_SHORT).show();
			return false;
		}
		if (radioZipCode.isChecked() && ValidationUtils.isNullOrEmpty(zipCodeEdit.getText().toString())) {
			Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_zipcode), Toast.LENGTH_SHORT).show();
			return false;
		}
		if (radioLocation.isChecked() && !LocationServices.getInstance(getActivity()).isLocationEnabled()) {
			Toast.makeText(getActivity(), getResources().getString(R.string.please_enable_location_services), Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public HeaderImageInterface getRightImage() {
		return new HeaderImageInterface() {

			@Override
			public void onClickListener() {
				Utils.hideKeyboard(getActivity(), getView());
			}

			@Override
			public Drawable getDrawable() {
				return getBaseActivity().getResources().getDrawable(R.drawable.helpicon);
			}
		};
	}

	public void showLoadingLocation(OnCancelListener cancelListener) {
		loadingDialog = new ProgressDialog(getBaseActivity());
		loadingDialog.setCancelable(true);
		loadingDialog.setMessage(getString(R.string.searching_location));
		loadingDialog.show();
		loadingDialog.setOnCancelListener(cancelListener);
	}

	public void hideLoadingLocation() {
		loadingDialog.hide();
	}

}
