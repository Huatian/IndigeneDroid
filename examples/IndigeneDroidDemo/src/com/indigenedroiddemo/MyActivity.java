package com.indigenedroiddemo;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.indigenedroid.app.IDActivity;
import com.indigenedroid.app.volley.tools.RequestOption;
import com.indigenedroiddemo.adapter.EfficientAdapter;
import com.indigenedroiddemo.entity.DataModel;
import com.indigenedroiddemo.entity.FlickrImage;
import com.indigenedroiddemo.entity.FlickrResponse;
import com.indigenedroiddemo.entity.FlickrResponsePhotos;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class MyActivity extends IDActivity {
	private ListView mListView;
	private List<DataModel> mDataList;
	private EfficientAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my);
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public IDActivity getCurrentContext() {
		// TODO Auto-generated method stub
		return MyActivity.this;
	}
	
	@Override
	public void initViewAndField() {
		mListView = (ListView) findViewById(R.id.image_list);
		mDataList = new ArrayList<DataModel>();
		mAdapter = new EfficientAdapter(mDataList);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public int[] getViewIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RequestOption<FlickrResponsePhotos> createRequestOptions() {
		String url = "http://api.flickr.com/services/rest";
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("api_key", "5e045abd4baba4bbcd866e1864ca9d7b");
		builder.appendQueryParameter("method", "flickr.interestingness.getList");
		builder.appendQueryParameter("format", "json");
		builder.appendQueryParameter("nojsoncallback", "1");
		
		return new RequestOption<FlickrResponsePhotos>(RequestOption.REQUEST_GSON, Request.Method.GET, builder.toString(), new Listener<FlickrResponsePhotos>() {

			@Override
			public void onResponse(FlickrResponsePhotos response) {
				dismissLoadingDialog();
				parseFlickrImageResponse(response);
				mAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
			
		}, FlickrResponsePhotos.class, null, null, null);
	}

	@Override
	public OnClickListener instanceOnClickListener() {
		// TODO Auto-generated method stub
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.send_http:
					
					break;

				default:
					break;
				}
			}
		};
	}

	private void parseFlickrImageResponse(FlickrResponsePhotos response) {

		mDataList.clear();
		FlickrResponse photos = response.getPhotos();
		for (int index = 0; index < photos.getPhotos().size(); index++) {

			FlickrImage flkrImage = photos.getPhotos().get(index);

			String imageUrl = "http://farm" + flkrImage.getFarm() + ".static.flickr.com/" + flkrImage.getServer() + "/" + flkrImage.getId() + "_" + flkrImage.getSecret() + "_t.jpg";
			DataModel model = new DataModel();
			model.setImageUrl(imageUrl);
			model.setTitle(flkrImage.getTitle());
			mDataList.add(model);

		}
	}
}
