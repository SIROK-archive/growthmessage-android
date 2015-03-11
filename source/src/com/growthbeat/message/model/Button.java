package com.growthbeat.message.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.JSONObjectUtils;

public class Button extends Model {

	private String label;
	private String intent; //TODO
	private String event;

	private Button(JSONObject jsonObject) {
		setJsonObject(jsonObject);
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("label", getLabel());
			jsonObject.put("event", getEvent());
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
				setEvent(jsonObject.getString("event"));
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}
	}


}
