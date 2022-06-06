package com.seoultech.dayo.utils.json;

import java.util.Map;
import org.json.JSONObject;

public class JsonData {

  private final JSONObject jsonObject = new JSONObject();

  public String make(Map<String, String> data) {
    for (String key : data.keySet()) {
      jsonObject.put(key, data.get(key));
    }
    return jsonObject.toString();
  }

}
