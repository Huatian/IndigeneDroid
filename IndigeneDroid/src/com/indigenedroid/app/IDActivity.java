package com.indigenedroid.app;

import com.indigenedroid.app.helper.IDActHelper;
import com.indigenedroid.app.view.LoadingDialog;
import com.indigenedroid.app.volley.tools.RequestOption;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;

/**
 * 基本视图类，集成了加载弹框
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 * @param <T>
 *
 */
public abstract class IDActivity extends Activity {
	/** 加载弹框 */
	protected LoadingDialog mProgressDialog;
	
	protected IDActHelper mActHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new LoadingDialog(IDActivity.this);
		initViewAndField();
		mActHelper = new IDActHelper(getCurrentContext());
		OnClickListener l = instanceOnClickListener();
		Log.i("IDActivity", l.toString());
		mActHelper.onActCreate(createRequestOptions(), getViewIds(), l);
	}
	
	/**
	 * 显示加载弹框
	 */
	public void showLoadingDialog(){
		mProgressDialog.show();
	}
	
	/**
	 * 显示加载弹框，并指定其显示的文字
	 * @param content 加载弹框显示的文字
	 */
	public void showLoadingDialog(String content){
		mProgressDialog.setLoadingText(content);
		mProgressDialog.show();
	}
	
	/**
	 * 隐藏加载弹框
	 */
	public void dismissLoadingDialog(){
		if(mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
	}
	
	/**
	 * 获得运行中的IDActivity的实例
	 * @return 运行中的IDActivity的实例
	 */
	public abstract IDActivity getCurrentContext();
	
	/**
	 * 初始化控件和全局变量
	 */
	public abstract void initViewAndField();
	
	/**
	 * 获得需要设置点击监听的控件的ID
	 * @return 需要设置点击监听的控件的ID组成的数组
	 */
	public abstract int[] getViewIds();
	
	/**
	 * 创建一个RequestOption（http请求参数类）的实例
	 * @return RequestOption实例
	 * @see {@link RequestOption}
	 */
	public abstract <T> RequestOption<T> createRequestOptions();
	
	/**
	 * 创建一个点击监听的实例，这个监听为{@link #getViewIds()}的控件所设。
	 * @return 点击监听的实例
	 * @see {@link #getViewIds()}
	 * @see {@link IDActHelper#onActCreate(RequestOption, int[], OnClickListener)}
	 */
	public abstract OnClickListener instanceOnClickListener();
}
