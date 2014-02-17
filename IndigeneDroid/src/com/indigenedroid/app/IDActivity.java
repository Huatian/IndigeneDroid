package com.indigenedroid.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

public class IDActivity extends Activity {
	
	private OnCreateListener mOnCreateListener;
	
	protected void setClickListener(int[] ids, OnClickListener listener){
		for(int id:ids){
			findViewById(id).setOnClickListener(listener);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mOnCreateListener.giveYouBundle(savedInstanceState);
		mOnCreateListener.initViewAndFields();
		mOnCreateListener.getData();
		mOnCreateListener.setClickListener();
	}
	
	public interface OnCreateListener{
		public void giveYouBundle(Bundle savedInstanceState);
		
		public void initViewAndFields();
		
		public void getData();
		
		public void setClickListener();
	}
	
	public void setOnCreateListener(OnCreateListener listener){
		this.mOnCreateListener = listener;
	}
}
