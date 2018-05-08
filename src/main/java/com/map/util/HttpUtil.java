package com.map.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.map.api.API;

import net.sf.json.JSONObject;

public class HttpUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	public static JSONObject get(String url, Map<String, Object> params)
			throws Exception {

		log.info("params={}", params);

		if (params.size() > 0) {
			url += "?";
		}
		for (String key : params.keySet()) {
			url += key + "=" + params.get(key) + "&";
		}
		url = url.substring(0, url.length() - 1);
		log.info("url={}", url);

		HttpURLConnection urlConn = (HttpURLConnection) new URL(url).openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setConnectTimeout(5 * 1000);
		urlConn.connect();
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = urlConn.getInputStream();
		byte[] b = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(b)) != -1) {
			sb.append(new String(b, 0, length, "utf-8"));
		}
		inputStream.close();
		urlConn.disconnect();
		String resp = sb.toString();
		log.info("resp={}", resp);
		return JSONObject.fromObject(resp);

	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("key", API.key);
		get(API.onLocation, map);
	}

}
