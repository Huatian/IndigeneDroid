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
package com.indigenedroid.app.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.View.OnClickListener;

import com.android.volley.Request;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.indigenedroid.app.IDApplication;
import com.indigenedroid.app.IDListActivity;
import com.indigenedroid.app.volley.tools.GsonRequest;
import com.indigenedroid.app.volley.tools.RequestFactory;
import com.indigenedroid.app.volley.tools.RequestOption;

/**
 * activity帮助类，使activity在onCreate()中调用
 * {@link #onActCreate(Activity, RequestOptions, int[], OnClickListener)}
 * 即可完成大部分业务
 * <p>使用{@link #refreshListView(RequestOption)}
 * 
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 * 
 */
public class IDListActHelper {
	private IDListActivity mAct;

	public IDListActHelper(IDListActivity act) {
		mAct = act;
	}

	/**
	 * 在activity初始化视图后调用，完成获取网络数据和刷新UI的功能
	 * 
	 * @param act
	 *            所在的activity的引用
	 * @param options
	 *            请求参数对象的实例
	 * @param ids
	 *            要设置点击监听的控件的引用的数组
	 * @param listener
	 *            点击监听对象的实例
	 * @see RequestOption
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onActCreate(RequestOption options, int[] ids, OnClickListener listener) {

		if (options == null) {
			return;
		}

		if(ids != null && listener != null && ids.length > 0){
			for (int id : ids) {
				mAct.findViewById(id).setOnClickListener(listener);
			}
		}

		switch (options.type) {
		case RequestOption.REQUEST_GSON:
			GsonRequest sr = RequestFactory.createGsonRequest(options.method, options.url, options.clsOrType, options.requestBody, options.listener, options.errorListener);
			mAct.showLoadingDialog();
			((IDApplication) mAct.getApplication()).addToRequestQueue(sr, options.requestTag);
			break;

		case RequestOption.REQUEST_JSON_OBJECT:
			JsonObjectRequest or = RequestFactory.createJsonObjectRequest(options.method, options.url, options.jsonRequest, (Listener<JSONObject>) options.listener, options.errorListener);
			mAct.showLoadingDialog();
			((IDApplication) mAct.getApplication()).addToRequestQueue(or, options.requestTag);
			break;

		case RequestOption.REQUEST_JSON_ARRAY:
			JsonArrayRequest ar = RequestFactory.createJsonArrayRequest(options.url, (Listener<JSONArray>) options.listener, options.errorListener);
			mAct.showLoadingDialog();
			((IDApplication) mAct.getApplication()).addToRequestQueue(ar, options.requestTag);
			break;
		default:
			break;
		}
	}

	/**
	 * 重新获取列表数据并刷新列表
	 * @param options 请求参数对象
	 * @see RequestOption
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refreshListView(RequestOption options) {
		Request request = RequestFactory.createRequest(options);
		mAct.showLoadingDialog();
		((IDApplication) mAct.getApplication()).addToRequestQueue(request, options.requestTag);
	}
}
