package com.example.vo;

import com.example.common.ReqCommon;
import com.example.sensitiveWordFilter.annotation.Sensitive;
import lombok.Data;

import java.util.Date;

/**
 * Demo class
 *
 * @author zhl
 * @date 2016/10/31
 */
@Data
public class UserVo extends ReqCommon {

	private Integer id;
	@Sensitive
	private String userName;
	private String passWord;
	private String birthday;
	private String sex;
	private String address;
//	private String oid;
//	private String hid;


}
