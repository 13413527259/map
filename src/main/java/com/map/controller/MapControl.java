package com.map.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.map.api.API;
import com.map.util.HttpUtil;

import net.sf.json.JSONObject;

@Controller
public class MapControl {

	@GetMapping("/location")
	public ModelAndView onLocation(ModelAndView modelAndView) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("key", API.key);
		JSONObject json = HttpUtil.get(API.onLocation, params);
		if (json.getInt("status") == 0) {
			JSONObject location = json.getJSONObject("result").getJSONObject("location");
			modelAndView.addObject("location", location);
		}
		modelAndView.setViewName("location");
		return modelAndView;
	}

}
