package com.kimjongwoo.paintapplication;

import org.json.JSONException;
import org.json.JSONObject;

class JsonUtil {

    static JSONObject getJSONObjectFrom(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String getStringFrom(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}