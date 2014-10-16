package com.payless.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.payless.BaseFragment;
import com.payless.R;


public abstract class WebViewFragment extends BaseFragment {
	
	public static final String BASE_URL = "http://m.payless.com";
	public static final String TERMS_AND_CONDITIONS = BASE_URL+"/terms";

	protected WebView mWebView;
	
	@Override
	protected int getLayout() {
		return R.layout.fragment_web_view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mWebView = mViewProxy.findWebView(R.id.webView);
		
		setSettings();
		
		mWebView.loadUrl(getUrl());
		
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void setSettings() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		
		mWebView.setWebViewClient(new WebViewClient() {
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		    	mWebView.loadUrl(url);
		        return true;
		    }
		    
		    @Override
		    public void onPageStarted(WebView view, String url, Bitmap favicon) {
		    	super.onPageStarted(view, url, favicon);
		    	getBaseActivity().showLoading();
		    }
		    
		    @Override
		    public void onPageFinished(WebView view, String url) {
		    	super.onPageFinished(view, url);
		    	getBaseActivity().hideLoading();
		    }
		    
		});
	}
	
	protected abstract String getUrl();

}
