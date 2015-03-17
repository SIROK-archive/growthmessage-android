package com.growthbeat.message.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.JSONObjectUtils;

public class GMButton extends Model {

	private String label;
	private GMIntent intent;

	public GMButton() {
		super();
		intent = new GMIntent();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public GMIntent getIntent() {
		return intent;
	}

	public void setIntent(GMIntent intent) {
		this.intent = intent;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("label", getLabel());
			jsonObject.put("intent", intent.getJsonObject());
		} catch (JSONException e) {
			return null;
		}

		return jsonObject;

	}

	@Override
	public void setJsonObject(JSONObject jsonObject) {

		if (jsonObject == null)
			return;

		try {
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "label"))
				setLabel(jsonObject.getString("label"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "intent"))
			{
				GMIntent intent = new GMIntent();
				intent.setJsonObject(jsonObject.getJSONObject("intent"));
				setIntent(intent);
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}
	}


}
