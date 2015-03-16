package com.growthbeat.message.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.JSONObjectUtils;

public class GMButton extends Model {

	private String label;
	private GMIntent intent; //TODO
	private String action;

	public GMButton() {
	}

/*	private Button(JSONObject jsonObject) {
		setJsonObject(jsonObject);
	}*/
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String event) {
		this.action = event;
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
			jsonObject.put("event", getAction());
			jsonObject.put("data", intent.getJsonObject());
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "event"))
				setAction(jsonObject.getString("event"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "data"))
			{
				GMIntent intent = new GMIntent();
				intent.setJsonObject(jsonObject.getJSONObject("data"));
				setIntent(intent);
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}
	}


}
