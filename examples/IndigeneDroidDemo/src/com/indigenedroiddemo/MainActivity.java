package com.indigenedroiddemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getListView().setAdapter(new MyAdapter());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch (position) {
		case 0:
			startActivity(new Intent(this, MyActivity.class));
			break;
		
		case 1:
			startActivity(new Intent(this, MyListActivity.class));
			break;

		default:
			break;
		}
	}
	
}

class MyAdapter extends BaseAdapter{
	private String[] strs = {"Activity","ListActivity"};
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strs.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strs[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tx = new TextView(parent.getContext());
		tx.setText(strs[position]);
		tx.setTextSize(20);
		return tx;
	}
	
}