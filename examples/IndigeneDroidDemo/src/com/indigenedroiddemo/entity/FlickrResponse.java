package com.indigenedroiddemo.entity;

import java.util.List;

public class FlickrResponse {

	public String id;
	
	List<FlickrImage> photo;

	public List<FlickrImage> getPhotos() {
		return photo;
	}
}
