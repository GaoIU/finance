package com.fanteng.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private final static HttpClient CLIENT = HttpClientBuilder.create().build();

	private final static int TIMEOUT = 5000;

	private final static RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(TIMEOUT)
			.setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

	/**
	 * 发送HTTP的GET请求
	 * 
	 * @param url
	 *            请求地址
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendGetByHttp(String url, String charset) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);
		HttpResponse response = CLIENT.execute(httpGet);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTP的GET请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendGetByHttp(String url, Map<String, String> headers, String charset)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);

		if (headers != null && !headers.isEmpty()) {
			headers.forEach((k, v) -> {
				httpGet.setHeader(k, v);
			});
		}

		HttpResponse response = CLIENT.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTP的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param json
	 *            请求参数，JSON字符串
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPostByHttp(String url, String json, String charset)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		StringEntity se = new StringEntity(json, charset);
		httpPost.setEntity(se);

		HttpResponse response = CLIENT.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTP的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param json
	 *            请求参数，JSON字符串
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPostByHttp(String url, Map<String, String> headers, String json, String charset)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		headers.forEach((k, v) -> {
			httpPost.setHeader(k, v);
		});

		StringEntity se = new StringEntity(json, charset);
		httpPost.setEntity(se);

		HttpResponse response = CLIENT.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTP的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求参数，Map<String, Object>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPostByHttp(String url, Map<String, Object> param, String charset)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>(0);
		param.forEach((k, v) -> {
			nvps.add(new BasicNameValuePair(k, v.toString()));
		});
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));

		HttpResponse response = CLIENT.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTP的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param param
	 *            请求参数，Map<String, Object>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String sendPostByHttp(String url, Map<String, String> headers, Map<String, Object> param,
			String charset) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);

		headers.forEach((k, v) -> {
			httpPost.setHeader(k, v);
		});

		List<NameValuePair> nvps = new ArrayList<NameValuePair>(0);
		param.forEach((k, v) -> {
			nvps.add(new BasicNameValuePair(k, v.toString()));
		});
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));

		HttpResponse response = CLIENT.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的GET请求
	 * 
	 * @param url
	 *            请求地址
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendGetByHttps(String url, String charset) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);
		CloseableHttpResponse response = createSSLClientDefault().execute(httpGet);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的GET请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendGetByHttps(String url, Map<String, String> headers, String charset)
			throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, KeyStoreException,
			IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);

		headers.forEach((k, v) -> {
			httpGet.setHeader(k, v);
		});

		CloseableHttpResponse response = createSSLClientDefault().execute(httpGet);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param json
	 *            请求参数，JSON字符串
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendPostByHttps(String url, String json, String charset) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		StringEntity se = new StringEntity(json, charset);
		httpPost.setEntity(se);

		HttpResponse response = createSSLClientDefault().execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求参数，Map<String, Object>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendPostByHttps(String url, Map<String, Object> param, String charset)
			throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, KeyStoreException,
			IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>(0);
		param.forEach((k, v) -> {
			nvps.add(new BasicNameValuePair(k, v.toString()));
		});
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));

		HttpResponse response = createSSLClientDefault().execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param json
	 *            请求参数，JSON字符串
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendPostByHttps(String url, Map<String, String> headers, String json, String charset)
			throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, KeyStoreException,
			IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");

		if (headers != null && !headers.isEmpty()) {
			headers.forEach((k, v) -> {
				httpPost.setHeader(k, v);
			});
		}

		StringEntity se = new StringEntity(json, charset);
		httpPost.setEntity(se);

		HttpResponse response = createSSLClientDefault().execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 发送HTTPS的POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求header，Map<String, String>集合
	 * @param param
	 *            请求参数，Map<String, Object>集合
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public static String sendPostByHttps(String url, Map<String, String> headers, Map<String, Object> param,
			String charset) throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException,
			KeyStoreException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);

		if (headers != null && !headers.isEmpty()) {
			headers.forEach((k, v) -> {
				httpPost.setHeader(k, v);
			});
		}

		List<NameValuePair> nvps = new ArrayList<NameValuePair>(0);
		param.forEach((k, v) -> {
			nvps.add(new BasicNameValuePair(k, v.toString()));
		});
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));

		HttpResponse response = createSSLClientDefault().execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			return EntityUtils.toString(response.getEntity(), charset);
		}

		return null;
	}

	/**
	 * 忽略HTTPS证书
	 * 
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 */
	public static CloseableHttpClient createSSLClientDefault()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();

		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

}