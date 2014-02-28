package com.indigenedroid.app.volley.tools;

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.indigenedroid.app.helper.IDActHelper;

/**
 * 网络请求参数实体类
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 *
 * @param <T>
 */
public class RequestOption<T> {
	
	public static final int REQUEST_GSON = 0x001;
	public static final int REQUEST_JSON_OBJECT = 0x002;
	public static final int REQUEST_JSON_ARRAY = 0x003;
	
	// 不同的请求共有的参数
	public int type;
	public int method;
	public String url;
	public Listener<T> listener;
	public ErrorListener errorListener;

	//gson请求所用参数
	public Object clsOrType;
	public String requestBody;
	
	//JSONObject的post请求所用参数
	public JSONObject jsonRequest;
	
	public String requestTag;

	/**
	 * RequestOptions构造方法
	 * @see RequestFactory
	 * @see IDActHelper
	 * @param type 要选择的Request类型
	 * @param method 请求类型
	 * @param url 请求地址
	 * @param listener 处理json的回调
	 * @param errorListener 处理错误的回调
	 * @param clsOrType Gson请求时的java bean的Class或者reflect.Type对象
	 * @param requestBody Gson请求时的请求参数
	 * @param jsonRequest post请求上传参数
	 * @param requestTag 把Request加入队列时，标示的标签
	 */
	public RequestOption(int type, int method, String url, Listener<T> listener, ErrorListener errorListener, Object clsOrType, String requestBody, JSONObject jsonRequest, String requestTag) {
		this.type = type;
		this.method = method;
		this.url = url;
		this.listener = listener;
		this.errorListener = errorListener;
		this.clsOrType = clsOrType;
		this.requestBody = requestBody;
		this.jsonRequest = jsonRequest;
		this.requestTag = requestTag;
	}
}
