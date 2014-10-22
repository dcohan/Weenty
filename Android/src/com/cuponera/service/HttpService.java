package com.cuponera.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class HttpService {

	private Context mContext;
	private final static String LOG_TAG_SERVICE = "com.cuponera";
	private final static int CONNECTION_TIME_OUT = 35000;
	private final static int MAX_CONNECTIONS = 1;
	private final static int RESPONSE_200 = 200;
	private final static int RESPONSE_400 = 400;
	private final static int RESPONSE_403 = 403;
	private final static int RESPONSE_500 = 500;

	public final static String NO_INTERNET = "no_internet";
	public final static String TIMEOUT = "timeout";
	public final static String SSLEXEPTION = "ssl_failed";

	private enum paramsInBodyMethod {
		Post, Put
	}

	private HttpClient getClient(HttpClient client) {

		HttpParams params = client.getParams();
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				CONNECTION_TIME_OUT);

		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				CONNECTION_TIME_OUT);
		params.setLongParameter(ConnManagerPNames.TIMEOUT, CONNECTION_TIME_OUT);
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS,
				MAX_CONNECTIONS);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
				new ConnPerRouteBean(MAX_CONNECTIONS));

		return new DefaultHttpClient(params);

	}

	public void readResponseBody(StringBuffer sb, HttpResponse httpResponse)
			throws IOException {
		BufferedReader in;
		InputStream inputStream = httpResponse.getEntity().getContent();
		Header contentEncoding = httpResponse
				.getFirstHeader("Content-Encoding");

		if (contentEncoding != null
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			inputStream = new GZIPInputStream(inputStream);
		}

		in = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";

		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
	}

	private String basePool(HttpRequestBase request) {
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer("");

		try {

			HttpClient client = getClient(new DefaultHttpClient());
			client.getParams().setIntParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT,
					CONNECTION_TIME_OUT);
			client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
					CONNECTION_TIME_OUT);
			client.getParams().setLongParameter(ConnManagerPNames.TIMEOUT,
					CONNECTION_TIME_OUT);

			HttpResponse httpResponse = client.execute(request);

			if (httpResponse.getStatusLine().getStatusCode() == RESPONSE_200
					|| httpResponse.getStatusLine().getStatusCode() == RESPONSE_400) {
				readResponseBody(sb, httpResponse);
			} else {
				if (httpResponse.getStatusLine().getStatusCode() == RESPONSE_500
						|| httpResponse.getStatusLine().getStatusCode() == RESPONSE_403) {
					readResponseBody(sb, httpResponse);
				}
				Log.e(ServiceConfig.LOG_TAG, "Server response error: "
						+ httpResponse.getStatusLine().getStatusCode() + ", "
						+ httpResponse.getStatusLine().getReasonPhrase());
			}

		} catch (SocketTimeoutException socketTimeoutException) {

			try {
				Log.e(ServiceConfig.LOG_TAG, "Timeout on request: "
						+ request.getURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			sb = new StringBuffer(TIMEOUT);
		} catch (UnknownHostException e) {

			try {
				Log.e(ServiceConfig.LOG_TAG, "Internet Lost on request: "
						+ request.getURI().toURL());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

			sb = new StringBuffer(NO_INTERNET);

		} catch (SSLException e) {
			Log.e(ServiceConfig.LOG_TAG,
					"error SSL: " + request.getURI().getPath() + ", message: "
							+ e.getMessage(), e);
			sb = new StringBuffer(SSLEXEPTION);
		} catch (Exception e) {
			Log.e(ServiceConfig.LOG_TAG,
					"error executing request: " + request.getURI().getPath()
							+ ", message: " + e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.w(LOG_TAG_SERVICE, e.getMessage(), e);
				}
			}
		}

		return sb.toString();
	}

	public String poolGet(String protocol, String host, String path,
			Map<String, Object> uriParams) {
		String response = null;
		try {
			HttpGet request = new HttpGet(getUri(protocol, host, path,
					uriParams));
			request.addHeader("Accept-Encoding", "gzip");
			response = basePool(request);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String poolPost(String protocol, String host, String path,
			Map<String, Object> uriParams) {
		return poolParamsInBody(protocol, host, path, uriParams, paramsInBodyMethod.Post);
	}

	public String poolPut(String protocol, String host, String path,
			Map<String, Object> uriParams) {
		return poolParamsInBody(protocol, host, path, uriParams, paramsInBodyMethod.Put);
	}

	private String poolParamsInBody(String protocol, String host, String path,
			Map<String, Object> uriParams, paramsInBodyMethod method) {
		String response = null;
		try {

			HttpEntityEnclosingRequestBase request = null;
			switch (method) {
				case Post:
					request = new HttpPost(getUri(protocol, host, path, null));
					break;
				case Put:
					request = new HttpPut(getUri(protocol, host, path, null));
					break;
			}

			if (uriParams != null) {
				JSONObject json = new JSONObject();
				for (Map.Entry<String, Object> entry : uriParams.entrySet()) {
					if (entry.getValue() instanceof List) {
						JSONArray array = new JSONArray();
						for (Object obj : (List<?>) entry.getValue()) {
							array.put(obj);
						}
						json.put(entry.getKey(), array);
					} else {
						json.put(entry.getKey(), entry.getValue());
					}
				}
				StringEntity entity = new StringEntity(json.toString());
				entity.setContentType("application/json");
				request.setEntity(entity);
			}
			response = basePool(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private URI getUri(String protocol, String host, String path,
			Map<String, Object> uriParams) throws URISyntaxException {
		String queryString = null;
		if (uriParams != null && uriParams.size() > 0) {
			List<NameValuePair> listParams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : uriParams.entrySet()) {
				listParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue().toString()));
			}
			queryString = URLEncodedUtils.format(listParams, "UTF-8");
		}
		URI uri = URIUtils.createURI(protocol, host, -1, path, queryString,
				null);
		return uri;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
}
