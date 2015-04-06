package com.growthbeat.message.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	private List<Button> buttons = new ArrayList<Button>();

	protected Message() {
		super();
	}

	protected Message(JSONObject jsonObject) {
		super(jsonObject);
	}

	public static Message getFromJsonObject(JSONObject jsonObject) {

		Message message = new Message(jsonObject);
		switch (message.getType()) {
		case plain:
			return new PlainMessage(jsonObject);
		default:
			return null;
		}

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

		return Message.getFromJsonObject(jsonObject);

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

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("id", getId());
			jsonObject.put("version", getVersion());
			jsonObject.put("type", getType().toString());
			jsonObject.put("eventId", getEventId());
			jsonObject.put("frequency", getFrequency());
			jsonObject.put("segmentId", getSegmentId());
			jsonObject.put("cap", getCap());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
			jsonObject.put("task", task != null ? task.getJsonObject() : null);
			JSONArray buttonJsonArray = new JSONArray();
			for (Button button : buttons) {
				JSONObject buttonJson = button.getJsonObject();
				buttonJsonArray.put(buttonJson);
			}
			jsonObject.put("buttons", buttonJsonArray);
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "version"))
				setVersion(jsonObject.getInt("version"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "type"))
				setType(Type.valueOf(jsonObject.getString("type")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "eventId"))
				setEventId(jsonObject.getString("eventId"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "frequency"))
				setFrequency(jsonObject.getInt("frequency"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "segmentId"))
				setSegmentId(jsonObject.getString("segmentId"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "cap"))
				setCap(jsonObject.getInt("cap"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "created"))
				setCreated(DateUtils.parseFromDateTimeString(jsonObject.getString("created")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "task"))
				setTask(new Task(jsonObject.getJSONObject("task")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "buttons")) {
				List<Button> buttons = new ArrayList<Button>();
				JSONArray buttonJsonArray = jsonObject.getJSONArray("buttons");
				for (int i = 0; i < buttonJsonArray.length(); i++)
					buttons.add(Button.getFromJsonObject(buttonJsonArray.getJSONObject(i)));
				setButtons(buttons);
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}

	public static enum Type {
		plain
	}

}