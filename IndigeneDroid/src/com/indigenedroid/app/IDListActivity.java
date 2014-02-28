package com.indigenedroid.app;

import com.indigenedroid.app.helper.IDActHelper;
import com.indigenedroid.app.helper.IDListActHelper;
import com.indigenedroid.app.view.LoadingDialog;
import com.indigenedroid.app.volley.tools.RequestOption;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public abstract class IDListActivity extends ListActivity{
	/** 加载弹框 */
	protected LoadingDialog mProgressDialog;

	protected IDListActHelper mActHelper;
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		itemClick(l, v, position, id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new LoadingDialog(IDListActivity.this);
		initViewAndField();
		mActHelper = new IDListActHelper(this);
		mActHelper.onActCreate(createRequestOptions(), getViewIds(), instanceOnClickListener());
	}

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
	
	/**
	 * 为listview设置item监听
	 * 
	 * @param l The ListView where the click happened
	 * @param v The view that was clicked within the ListView
	 * @param position The position of the view in the list
	 * @param id The row id of the item that was clicked
	 */
	public abstract void itemClick(ListView l, View v, int position, long id);
	
	/**
	 * 冲新获取数据并刷新listview
	 * @param options RequestOption（http请求参数类）
	 */
	@SuppressWarnings("rawtypes")
	protected void refreshListView(RequestOption options){
		mActHelper.refreshListView(options);
	}

	/**
	 * 显示加载弹框
	 */
	public void showLoadingDialog() {
		mProgressDialog.show();
	}

	/**
	 * 显示加载弹框，并指定其显示的文字
	 * 
	 * @param content
	 *            加载弹框显示的文字
	 */
	public void showLoadingDialog(String content) {
		mProgressDialog.setLoadingText(content);
		mProgressDialog.show();
	}

	/**
	 * 隐藏加载弹框
	 */
	public void dismissLoadingDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
}
