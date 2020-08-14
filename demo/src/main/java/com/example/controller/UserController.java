package com.example.controller;

import com.example.base.BaseAppletController;
import com.example.common.shortUrl.ShortUrlService;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.vo.UserVo;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.DateUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

@RestController
@RequestMapping("/V2")
public class UserController extends BaseAppletController {

	@Autowired
	private UserService userService;

	@Autowired
	StringRedisTemplate redis;

	@PostMapping("/SC001")
	public Object query() throws Exception{
        Boolean name = redis.hasKey("name");
        System.out.println(name);
		return null;
	}


	@PostMapping("/SC002")
	public Object add() throws Exception{
		redis.opsForValue().set("2", "1");
		return null;
	}



}
