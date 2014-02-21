/**
 * Copyright (C) 2014 Huatian Wang (wanghuatian1987@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.indigenedroid.app.volley.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Custom implementation of Request<T> class which converts the HttpResponse
 * obtained to Java class objects. Uses GSON library, to parse the response
 * obtained. Ref - JsonRequest<T>
 * 
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 */

public class GsonRequest<T> extends Request<T> {

	/** Charset for request. */
	private static final String PROTOCOL_CHARSET = "utf-8";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

	private final Listener<T> mListener;

	private final String mRequestBody;

	private Gson mGson;
	private Class<T> mJavaClass;
	private Type mType;

	/**
	 * Gson请求构造方法
	 * 
	 * @param method
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @param cls
	 *            映射对象的class
	 * @param requestBody
	 *            请求参数
	 * @param listener
	 *            处理json的回调
	 * @param errorListener
	 *            处理错误的回调
	 */
	public GsonRequest(int method, String url, Class<T> cls, String requestBody, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		mJavaClass = cls;
		mListener = listener;
		mRequestBody = requestBody;
	}

	/**
	 * Gson请求构造方法
	 * 
	 * @param method
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @param type
	 *            映射对象的type
	 * @param requestBody
	 *            请求参数
	 * @param listener
	 *            处理json的回调
	 * @param errorListener
	 *            处理错误的回调
	 */
	public GsonRequest(int method, String url, Type type, String requestBody, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		mType = type;
		mListener = listener;
		mRequestBody = requestBody;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	private Map<String, String> headers = new HashMap<String, String>();

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.i("GsonRequest", "jsonStr:" + jsonString);
			
			T parsedGSON = null;
			if (mJavaClass != null) {
				parsedGSON = mGson.fromJson(jsonString, mJavaClass);
			} else {
				parsedGSON = mGson.fromJson(jsonString, mType);
			}
			return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));

		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, PROTOCOL_CHARSET);
			return null;
		}
	}

}
