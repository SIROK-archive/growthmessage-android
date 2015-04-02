package com.growthbeat.message.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class Message extends Model {

	private String id;
	private int version;
	private Type type;
	private String eventId;
	private int frequency;
	private String segmentId;
	private int cap;
	private Date created;
	private Task task;
	private ArrayList<Button> buttons = new ArrayList<Button>();

	public Message() {
		super();
	}

	public Message(JSONObject jsonObject) {
		super(jsonObject);
	}

	public static Message receive(String clientId, String eventId, String credentialId) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (clientId != null)
			params.put("clientId", clientId);
		if (eventId != null)
			params.put("eventId", eventId);
		if (credentialId != null)
			params.put("credentialId", credentialId);

		JSONObject jsonObject = GrowthMessage.getInstance().getHttpClient().post("/1/receive", params);

		// TODO Determine message type
		return new PlainMessage(jsonObject);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
			jsonObject.put("type", getType());
			jsonObject.put("task", task.getJsonObject());
			JSONArray array = new JSONArray();
			for (Button button : buttons) {
				JSONObject buttonJson = button.getJsonObject();
				array.put(buttonJson);
			}
			jsonObject.put("buttons", array);
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "created"))
				setCreated(DateUtils.parseFromDateTimeString(jsonObject.getString("created")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "type"))
				setType(Type.valueOf(jsonObject.getString("type")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "task")) {
				Task task = new Task();
				task.setJsonObject(jsonObject.getJSONObject("task"));
				setTask(task);
			}
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "buttons")) {
				JSONArray array = jsonObject.getJSONArray("buttons");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonButton = array.getJSONObject(i);
					Button button = new Button();
					button.setJsonObject(jsonButton);
					buttons.add(button);
				}
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}

	public static enum Type {
		plain
	}

}