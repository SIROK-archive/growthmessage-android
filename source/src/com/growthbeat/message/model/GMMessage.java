package com.growthbeat.message.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class GMMessage extends Model {

	private String id;
	private Date created;
	private String caption;
	private String text;
	private ArrayList<GMButton> buttons;
	private String type;
	private GMTask task;
	
	public GMMessage() {
		super();
		buttons = new ArrayList<GMButton>();
		setTask(new GMTask());
	}

	private GMMessage(JSONObject jsonObject) {
		buttons = new ArrayList<GMButton>();
		setJsonObject(jsonObject);
	}

	public static GMMessage find(String clientId, String credentialId) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (clientId != null)
			params.put("clientId", clientId);
		if (credentialId != null)
			params.put("credentialId", credentialId);
		params.put("eventId", "Event:P5C3vzoLOEijnlVj:Default:Open");

		JSONObject jsonObject = GrowthMessage.getInstance().getHttpClient().post("0/message", params);

		Log.d("json", jsonObject.toString());
		return new GMMessage(jsonObject);
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

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
			jsonObject.put("caption", getCaption());
			jsonObject.put("text", getText());
			jsonObject.put("type", getType());
			jsonObject.put("task", task.getJsonObject());
			JSONArray array = new JSONArray();
			for(GMButton button : buttons)
			{
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "caption"))
				setCaption(jsonObject.getString("caption"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "text"))
				setText(jsonObject.getString("text"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "type"))
				setType(jsonObject.getString("type"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "task"))
			{
				GMTask task = new GMTask();
				task.setJsonObject(jsonObject.getJSONObject("task"));
				setTask(task);
			}
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "buttons"))
			{
				JSONArray array = jsonObject.getJSONArray("buttons");
	            for(int i = 0; i < array.length(); i++)
	            {
	                JSONObject jsonButton = array.getJSONObject(i);
	                GMButton button = new GMButton();
	                button.setJsonObject(jsonButton);
	                buttons.add(button);
	            }
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}
	public String getText() {
		return text;
	}

	public void setText(String body) {
		this.text = body;
	}

	public ArrayList<GMButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<GMButton> buttons) {
		this.buttons = buttons;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	private static String getSampleJSON()
	{
		String sample = "{\"id\":\"P71l6apl8usq3cUA\",\"availableTo\":null,\"text\":\"本文\",\"eventId\":\"Event:P5C3vzoLOEijnlVj:Default:Open\",\"segmentId\":null,\"created\":\"2015-03-15T18:40:22+0000\",\"task\":{\"id\":\"P5MsD33SuHNJw1X5\",\"applicationId\":\"P5C3vzoLOEijnlVj\",\"updated\":\"2015-02-26T04:22:33+0000\",\"created\":\"2015-02-26T04:22:33+0000\",\"description\":\"アプリ起動時のメッセージです。\",\"name\":\"起動メッセージ\"},\"name\":\"P6cDLi0EC9Z0w6h3;btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY;Event:P5C3vzoLOEijnlVj:Default:Open\",\"availableFrom\":null,\"buttons\":[{\"message\":null,\"id\":\"P77jQYUqC0yu6G3i\",\"label\":\"ボタン1\",\"type\":\"plain\",\"intent\":{\"id\":\"P71nAJH1cujbA7om\",\"applicationId\":\"P5C3vzoLOEijnlVj\",\"type\":\"url\",\"created\":\"2015-03-15T18:48:33+0000\",\"url\":\"http://sirok.co.jp/\",\"name\":\"コーポレートサイト\"},\"created\":\"2015-03-16T19:11:20+0000\"},{\"message\":null,\"id\":\"P77jY7aFlJ3ixzCw\",\"label\":\"ボタン2\",\"type\":\"plain\",\"intent\":{\"id\":\"P71nD3JxgjXQCnUI\",\"applicationId\":\"P5C3vzoLOEijnlVj\",\"type\":\"noop\",\"created\":\"2015-03-15T18:48:44+0000\",\"name\":\"何もしない\"},\"created\":\"2015-03-16T19:11:49+0000\"}],\"caption\":\"タイトル\",\"type\":\"plain\",\"version\":0}";
		return sample;
		
	}
	
}