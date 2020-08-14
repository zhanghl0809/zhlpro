package com.example.controller;

import com.example.base.BaseAppletController;
import com.example.common.shortUrl.ShortUrlService;
import com.example.entity.Student;
import com.example.entity.User;
import com.example.sensitiveWordFilter.annotation.SensitiveWordsFilter;
import com.example.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

/**
 * 测试专用
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/V1")
public class BaseController extends BaseAppletController {
    public Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private ShortUrlService shortUrlService;


    /**
     *  短地址测试，重定向跳转
     * @param shortUrl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET)
    public ModelAndView GetUser1(@PathVariable String shortUrl) throws Exception {
        return new ModelAndView(new RedirectView("http://www.baidu.com"));
    }


    /**
     *  查询学生 （请求controller拦截验证SensitiveWordsFilter）
     * @param student
     * @return
     * @throws Exception
     */
    @PostMapping("/SC001")
    @SensitiveWordsFilter
    public Object addStudent(Student student) throws Exception{
        User user = userService.Sel(1);
        logger.info("查询学生SC001>>>>>>>>>>>>{}",user);
        return user;
    }

    /**
     * 查询学生01（请求每个参数校验Valid）
     * @param student
     * @return
     * @throws Exception
     */
    @PostMapping("/SC002")
    public Object addStudent1(@Valid Student student) throws Exception{
        User user = userService.Sel(1);
        logger.info("查询学生SC002>>>>>>>>>>>>{}",user);
        return user;
    }


    /**
     * 长链接转短连接
     * @param longUrl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/SC003",method = RequestMethod.GET)
    public String makeShortUrl(@Param("longUrl") String longUrl) throws Exception{
        logger.debug("传入的长连接>>>>>>>>>>>>>>>>>：{}",longUrl);
        String shortUrl = shortUrlService.mrwShortUrlService(longUrl);
        logger.debug("生成短连接>>>>>>>>>>>>>>>>>>：{}",shortUrl);
        return shortUrl;
    }

}
