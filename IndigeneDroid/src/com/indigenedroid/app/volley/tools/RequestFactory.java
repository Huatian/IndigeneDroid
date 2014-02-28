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

import java.lang.reflect.Type;
import org.json.JSONArray;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Request工厂类，根据不同的请求类型返回不同的Request实例.
 * </p>返回GsonRequest实例{@link #createGsonRequest(int, String, Object, String, Listener, ErrorListener)}
 * </p>返回JsonObjectRequest实例{@link #createJsonObjectRequest(int, String, JSONObject, Listener, ErrorListener)}
 * </P>返回JsonArrayRequest实例{@link #createJsonArrayRequest(String, Listener, ErrorListener)}
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 *
 */
public class RequestFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Request<T> createRequest(RequestOption options){
		switch (options.type) {
		case RequestOption.REQUEST_GSON:
			return RequestFactory.createGsonRequest(options.method, options.url, options.clsOrType, options.requestBody, options.listener, options.errorListener);
			
		case RequestOption.REQUEST_JSON_OBJECT:
			return (Request<T>) RequestFactory.createJsonObjectRequest(options.method, options.url, options.jsonRequest, (Listener<JSONObject>) options.listener, options.errorListener);

		case RequestOption.REQUEST_JSON_ARRAY:
			return (Request<T>) RequestFactory.createJsonArrayRequest(options.url, (Listener<JSONArray>) options.listener, options.errorListener);
			
		default:
			break;
		}
		
		return null;
	}
	
	/**
	 * 实例化一个GsonRequest对象
	 * 
	 * @see GsonRequest
	 * 
	 * @param method
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @param clsOrType
	 *            映射对象的class或者映射对象的type
	 * @param requestBody
	 *            请求参数
	 * @param listener
	 *            处理json的回调
	 * @param errorListener
	 *            处理错误的回调
	 * @return GsonRequest实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> GsonRequest<T> createGsonRequest(int method, String url, Object clsOrType, String requestBody, Listener<T> listener, ErrorListener errorListener) {
		GsonRequest<T> request = null;
		switch (method) {
		case Request.Method.GET:
			if (clsOrType instanceof Class) {
				request = new GsonRequest<T>(method, url, (Class<T>) clsOrType, requestBody, listener, errorListener);
			} else if (clsOrType instanceof Type) {
				request = new GsonRequest<T>(method, url, (Type) clsOrType, requestBody, listener, errorListener);
			}
			break;

		default:
			break;
		}

		return request;
	}

	/**
	 * 实例化一个JsonObjectRequest对象
	 * 
	 * @see JsonObjectRequest
	 * @param method
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @param jsonRequest
	 *            post请求上传参数
	 * @param listener
	 *            处理json的回调
	 * @param errorListener
	 *            处理错误的回调
	 * @return JsonObjectRequest实例
	 */
	public static JsonObjectRequest createJsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		return new JsonObjectRequest(method, url, jsonRequest, listener, errorListener);
	}

	/**
	 * 实例化一个JsonArrayRequest对象
	 * 
	 * @see JsonArrayRequest
	 * @param url
	 *            请求地址
	 * @param listener
	 *            处理json的回调
	 * @param errorListener
	 *            处理错误的回调
	 * @return JsonArrayRequest实例
	 */
	public static JsonArrayRequest createJsonArrayRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
		return new JsonArrayRequest(url, listener, errorListener);
	}
}
