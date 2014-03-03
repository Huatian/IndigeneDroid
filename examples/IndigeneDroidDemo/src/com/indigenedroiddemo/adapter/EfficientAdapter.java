package com.indigenedroiddemo.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.indigenedroid.app.view.IDWebImageView;
import com.indigenedroiddemo.R;
import com.indigenedroiddemo.entity.DataModel;

public class EfficientAdapter extends BaseAdapter {

	private List<DataModel> mDataList;

	public EfficientAdapter(List<DataModel> list) {
		mDataList = list;
	}

	public int getCount() {
		return mDataList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.image = (IDWebImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.title.setTextColor(Color.BLACK);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(mDataList.get(position).getTitle());
		holder.image.setImageUrl(mDataList.get(position).getImageUrl(), R.drawable.flickr);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		IDWebImageView image;
	}

}
