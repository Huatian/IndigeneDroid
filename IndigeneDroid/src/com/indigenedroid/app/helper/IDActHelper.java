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

import android.app.Activity;
import android.view.View.OnClickListener;
import com.android.volley.Request;
import com.indigenedroid.app.IDActivity;
import com.indigenedroid.app.IDApplication;
import com.indigenedroid.app.volley.tools.RequestFactory;
import com.indigenedroid.app.volley.tools.RequestOption;

/**
 * activity帮助类，使activity在onCreate()中调用
 * {@link #onActCreate(Activity, RequestOptions, int[], OnClickListener)}
 * 即可完成大部分业务
 * 
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 * 
 */
public class IDActHelper {
	protected IDActivity mAct;

	public IDActHelper(IDActivity act) {
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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onActCreate(RequestOption options, int[] ids, OnClickListener listener) {

		
		if(ids != null && listener != null && ids.length > 0){
			for (int id : ids) {
				mAct.findViewById(id).setOnClickListener(listener);
			}
		}
		
		if (options != null) {
			Request request = RequestFactory.createRequest(options);
			mAct.showLoadingDialog();
			((IDApplication) mAct.getApplication()).addToRequestQueue(request, options.requestTag);
		}
	}

}
