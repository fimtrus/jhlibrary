package com.jhlibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhlibrary.R;
import com.jhlibrary.model.IconTextItem;

public class IconTextAdapter extends ArrayAdapter<IconTextItem> {

	public IconTextAdapter(Context context) {
		super(context, 0);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.row, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
		int res = getItem(position).iconRes;
		if (res != -1) {
			icon.setImageResource(res);
		}
		TextView title = (TextView) convertView.findViewById(R.id.row_title);
		title.setText(getItem(position).tag);
		return convertView;
	}
}
