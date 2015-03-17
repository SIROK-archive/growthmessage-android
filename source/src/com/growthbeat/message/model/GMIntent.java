package com.growthbeat.message.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class GMIntent extends Model{

	private String type;
	private String url;
//	private HashMap<String, String> data;
	
	public GMIntent() {
		super();
//		setData(new HashMap<String, String>());
	}

	@Override
	public JSONObject getJsonObject() { //TODO test

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("type", getType());
			jsonObject.put("url", getUrl());

/*			JSONArray array = new JSONArray();
			Iterator entries = data.entrySet().iterator();
			while (entries.hasNext())
			{
				Map.Entry entry = (Map.Entry)entries.next();
				JSONObject json = new JSONObject();
				json.put((String)entry.getKey(), (String)entry.getValue());
				array.put(json);
			}
			jsonObject.put("data", array);*/
		} catch (JSONException e) {
			return null;
		}
		return jsonObject;
	}

	@Override
	public void setJsonObject(JSONObject jsonObject) { //TODO test

		if (jsonObject == null)
			return;

		try {
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "type"))
				setType(jsonObject.getString("type"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "url"))
				setUrl(jsonObject.getString("url"));
/*			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "data"))
			{
				HashMap<String, String> map = new HashMap<String, String>();
				JSONArray array = jsonObject.getJSONArray("data");
				for (int i = 0; i < array.length(); i++)
				{
					JSONObject json = array.getJSONObject(i);
					Iterator keys = json.keys();
					String key = (String)keys.next();
					map.put(key, json.getString(key));
				}
			}*/
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
