package com.growthbeat.message.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class Button extends Model {

	private String id;
	private String label;
	private Date created;
	private Intent intent;

	public Button() {
		super();
		intent = new Intent();
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("label", getLabel());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
			jsonObject.put("intent", intent.getJsonObject());
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to get JSON.");
		}

		return jsonObject;

	}

	@Override
	public void setJsonObject(JSONObject jsonObject) {

		if (jsonObject == null)
			return;

		try {
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "id"))
				setId(jsonObject.getString("id"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "label"))
				setLabel(jsonObject.getString("label"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "created"))
				setCreated(DateUtils.parseFromDateTimeString(jsonObject.getString("created")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "intent"))
			{
				Intent intent = new Intent();
				intent.setJsonObject(jsonObject.getJSONObject("intent"));
				setIntent(intent);
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


}
