package com.growthbeat.message.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class Task extends Model {
	private String id;
	private String applicationId;
	private String name;
	private String description;
	private Date created;
	private Date updated;

	public Task() {
		super();
	}

	@Override
	public JSONObject getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("applicationId", getApplicationId());
			jsonObject.put("name", getName());
			jsonObject.put("description", getDescription());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
			jsonObject.put("updated", DateUtils.formatToDateTimeString(updated));
		} catch (Exception e) {
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "applicationId"))
				setApplicationId(jsonObject.getString("applicationId"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "name"))
				setName(jsonObject.getString("name"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "description"))
				setDescription(jsonObject.getString("description"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "created"))
				setCreated(DateUtils.parseFromDateTimeString(jsonObject.getString("created")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "updated"))
				setUpdated(DateUtils.parseFromDateTimeString(jsonObject.getString("updated")));
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

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
