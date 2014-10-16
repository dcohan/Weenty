package com.payless.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

public class ViewProxy {

	private Context mContext;
	private LayoutInflater mInflater;
	private View mView;

	public ViewProxy(Context context, int layoutId){
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(layoutId, null);
	}

	public ViewProxy(Context context, int layoutId, ViewGroup parent){
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(layoutId, parent);
	}

	/**
	 * Utiliza la vista pasada como parametro, no infla una nueva vista.
	 * @param context
	 * @param view
	 */
	public ViewProxy(Context context, View view){
		mContext = context;
		mView = view;
	}

	public Button findButton(int id){
		return (Button)mView.findViewById(id);
	}
	
	public CheckBox findCheckBox(int id) {
		return (CheckBox) mView.findViewById(id);
	}

	public EditText findEditText(int id){
		return (EditText)mView.findViewById(id);
	}

	public FrameLayout findFrameLayout(int id){
		return (FrameLayout) mView.findViewById(id);
	}
	
	public TableRow findTableRow(int id){
		return (TableRow) mView.findViewById(id);
	}

	public RelativeLayout findRelativeLayout(int id){
		return (RelativeLayout) mView.findViewById(id);
	}

	public GridView findGridView(int id) {
		return (GridView) mView.findViewById(id);
	}
	
	public ImageButton findImageButton(int id){
		return (ImageButton)mView.findViewById(id);
	}

	public ImageView findImageView(int id) {
		return (ImageView) mView.findViewById(id);
	}

	public LinearLayout findLinearLayout(int id){
		return (LinearLayout) mView.findViewById(id);
	}

	public ListView findListView(int id) {
		return (ListView) mView.findViewById(id);
	}

	public ExpandableListView findExpandableListView(int id) {
		return (ExpandableListView) mView.findViewById(id);
	}

	public ProgressBar findProgressBar(int id) {
		return (ProgressBar) mView.findViewById(id);
	}
	
	public RadioButton findRadioButton(int id){
		return (RadioButton) mView.findViewById(id);
	}

	public RadioGroup findRadioGroup(int id){
		return (RadioGroup) mView.findViewById(id);
	}

	public RatingBar findRatingBar(int id){
		return (RatingBar) mView.findViewById(id);
	}

	public ScrollView findScrollView(int id) {
		return (ScrollView) mView.findViewById(id);
	}

	public Spinner findSpinner(int id){
		return (Spinner)mView.findViewById(id);
	}

	public TextView findTextView(int id){
		return (TextView)mView.findViewById(id);
	}

	public ToggleButton findToggleButton(int id) {
		return (ToggleButton) mView.findViewById(id);
	}

	public View findView(int id){
		return mView.findViewById(id);
	}

	public ViewGroup findViewGroup(int id){
		return (ViewGroup)mView.findViewById(id);
	}

	public ViewPager findViewPager(int id){
		return (ViewPager)mView.findViewById(id);
	}

	public ViewStub findViewStub(int id){
		return (ViewStub)mView.findViewById(id);
	}

	public ViewSwitcher findViewSwitcher(int id){
		return (ViewSwitcher)mView.findViewById(id);
	}

	public WebView findWebView(int id){
		return (WebView) mView.findViewById(id);
	}

	public View getView(){
		return mView;
	}

	public void setOnClickListener(int id, OnClickListener listener){
		findView(id).setOnClickListener(listener);
	}

	public void setText(int id, CharSequence text) {
		findTextView(id).setText(text);
	}

	public void setText(int id, int resId){
		findTextView(id).setText(mContext.getString(resId));
	}

	public void setText(int id, String text) {
		findTextView(id).setText(text);
	}

	public void setViewVisibility(int id, boolean visible){
		findView(id).setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public void setVisibility(int id, boolean visible){
		findView(id).setVisibility(visible ? View.VISIBLE : View.GONE);
	}
}
