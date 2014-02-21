package com.indigenedroid.app;

import com.indigenedroid.app.view.LoadingDialog;
import android.app.Activity;
import android.os.Bundle;

/**
 * 基本视图类，集成了加载弹框
 * @author Huatian Wang (wanghuatian1987@gmail.com)
 *
 */
public class IDActivity extends Activity {
	/** 加载弹框 */
	protected LoadingDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new LoadingDialog(IDActivity.this);
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
}
