/*
 * Copyright 2014-2018 buyforyou.cn. All rights reserved.
 */
package com.example.aop;

import com.example.Utiles.JsonUtil;
import com.example.Utiles.ReflectionUtils;
import com.example.base.BaseLogger;
import com.example.checker.EnumType;
import com.example.checker.NumberType;
import com.example.checker.TextType;
import com.example.common.RspCommon;
import com.example.exception.BizException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.core.util.Assert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

/**
 * 参数校验AOP
 *
 * @author LGD
 * @since 1.8
 */
@Aspect @Component @Order(1) public class ParameterCalibration extends BaseLogger {

	private static Logger logger = LoggerFactory.getLogger(ParameterCalibration.class);

	private static final String ENCODING = "UTF-8";

	//	@Autowired
	//	private ITbSysCompanySetService bdsConstService;

	/**
	 * AOP 拦截所有controller
	 */
	@Pointcut("execution(public * com.example.controller..*.*(..))") public void pointCut() {

	}

	@SuppressWarnings("unchecked") @Around("pointCut()") public Object parameterCalibration(ProceedingJoinPoint pjp)
			throws Throwable {
		String className = pjp.getTarget().getClass().getName(); // 拦截类
		String methodName = pjp.getSignature().getName() + "()";

		Object[] args = pjp.getArgs();// Returns the arguments at this join point.

		StringBuffer params = new StringBuffer();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Object object : args) {
			Object obj = object;
			params.append(JsonUtil.formatJson(obj));
			resultMap = checking(obj);
		}
		Map<String, String> reqMap = new HashMap<>();
		if (!Assert.isEmpty(params)) {
			reqMap = JsonUtil.parseJson(params.toString(), Map.class);

			if (reqMap.containsKey("password")) {
				Map<String, String> map = new HashMap<>();
				map.putAll(reqMap);
				map.put("password", "******");
				logger.debug("{}.{} 业务请求报文RequestBody value:{}",
						new Object[] { className, methodName, JsonUtil.formatJson(map) });
			} else {
				logger.debug("{}.{} 业务请求报文RequestBody value:{}",
						new Object[] { className, methodName, JsonUtil.formatJson(reqMap) });
			}

			if ("true".equals(resultMap.get("paramError"))) {
				throw new BizException(String.format("{%s},参数格式错误！", resultMap.get("errorField")));
			}

			businessCheck(reqMap);
		}

		RspCommon rsp = new RspCommon();
//		//token 重复提交验证
//		//1.验证请求体 是否有token关键字
//		if (reqMap.containsKey("token")) {
//			//2.生成token的方法实现
//			TokenProccessor instance = TokenProccessor.getInstance();
//			String token = instance.makeToken();
//			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//			HttpServletRequest request = (HttpServletRequest) requestAttributes
//					.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//			logger.debug("生成的token码:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::{}", token);
//			//3.1 判断token是否有值。如没有值 代表第一次发起请求，服务端生产token给前台。
//			//3.2 若存在token，代表不是第一次请求，进行token的校验，验证是否是重复提交。
//			if (StringUtils.isEmpty(reqMap.get("token"))) {
//				//第一次提交请求。
//				System.out.println("token 为 null");
//				request.getSession().setAttribute("sessionToken", token);
//				rsp.getRspHead().setToken(token);
//			} else {
//				//非第一次提交请求。
//				//如token相等，代表第一次提交。然后更新session的token值。
//				if (reqMap.get("token").toString().equals(request.getSession().getAttribute("sessionToken"))) {
//					request.getSession().setAttribute("sessionToken", token);
//					rsp.getRspHead().setToken(reqMap.get("token").toString());
//				}else{
//					throw new BizException("token校验异常，表单重复提交");
//				}
//			}
//		}else{
//			throw new BizException("请求参数不正确！》》》无token关键字！");
//		}
		Object rspBody = pjp.proceed();
		rsp.setRspBody(rspBody);
		logger.debug("{}.{} 业务响应报文RspBody value:{}", new Object[] { className, methodName, JsonUtil.formatJson(rsp) });

		return rsp;
	}

	;

	/**
	 * Parameter business check
	 */
	private void businessCheck(Map<String, String> reqMap) throws Exception {
		// parameter service check
		// Small procedures belong to the body check
		if (reqMap.containsKey("distinguish")) {
			if (!StringUtils.isEmpty(reqMap.get("distinguish"))) {
				//				bdsConstService.getCompanyId(reqMap.get("distinguish").toString());
			}
		}
		// parameter service check
		// distinguish check
		if (reqMap.containsKey("identity")) {
			if (!StringUtils.isEmpty(reqMap.get("identity"))) {
				//				bdsConstService.getCompanyId(reqMap.get("identity").toString());
			}
		}
	}

	/**
	 * 参数校验
	 *
	 * @param obj
	 * @return
	 * @description 校验参数是否符合该参数所属注解
	 */
	private Map<String, String> checking(Object obj) throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();

		Field[] fields = ReflectionUtils.getDeclaredField(obj);

		annotationCalibration(resultMap, fields, obj);

		return resultMap;
	}

	/**
	 * 字段校验
	 *
	 * @param resultMap
	 * @param fields
	 * @param obj
	 * @throws Exception
	 */
	private void annotationCalibration(Map<String, String> resultMap, Field[] fields, Object obj) throws Exception {
		for (Field field : fields) {
			String filedName = field.getName();// 获得参数名称
			Object singleParam = ReflectionUtils.getInvoke(obj, filedName, false);

			// notNull校验
			if (field.isAnnotationPresent(NotNull.class)) {
				if (singleParam == null || "".equals(singleParam)) {
					logger.debug(filedName + " cannot be null");
					resultMap.put("paramError", "true");
					resultMap.put("errorField", filedName);
					break;
				}
			}

			if (field.isAnnotationPresent(TextType.class)) {

				TextType textType = field.getAnnotation(TextType.class);
				boolean notNull = textType.notNull();

				if (notNull) {
					if (singleParam == null || "".equals(singleParam)) {
						logger.debug(filedName + " cannot be empty");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}

				if (singleParam != null && !"".equals(singleParam)) {
					int max = textType.maxLength();
					int min = textType.minLength();
					if (singleParam.toString().getBytes(ENCODING).length < min) {
						logger.debug(
								filedName + " minLength is [" + min + "], but input [" + singleParam.toString().length()
										+ "]");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					} else if (singleParam.toString().getBytes(ENCODING).length > max) {
						logger.debug(
								filedName + " maxLength is [" + max + "], but input [" + singleParam.toString().length()
										+ "]");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}
			}

			if (field.isAnnotationPresent(EnumType.class)) {
				EnumType enumType = field.getAnnotation(EnumType.class);
				boolean notNull = enumType.notNull();
				if (notNull) {
					if (singleParam == null || "".equals(singleParam)) {
						logger.debug(filedName + " cannot be empty");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}
				if (singleParam != null && !"".equals(singleParam)) {
					String[] allows = enumType.allows();
					Arrays.sort(allows);
					int index = Arrays.binarySearch(allows, singleParam.toString());
					if (index < 0) {
						logger.debug(filedName + " must in [" + Arrays.toString(allows) + "], but input [" + singleParam
								.toString() + "] is not in");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}
			}

			if (field.isAnnotationPresent(NumberType.class)) {
				NumberType numberType = field.getAnnotation(NumberType.class);
				boolean notNull = numberType.notNull();
				if (notNull) {
					if (singleParam == null || "".equals(singleParam)) {
						logger.debug(filedName + " cannot be empty");
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}
				if (singleParam != null && !"".equals(singleParam)) {
					int length = numberType.length();
					Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
					// Pattern pattern =
					// Pattern.compile("^[-\\+]?(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$");
					if (!pattern.matcher(singleParam.toString()).matches()
							|| singleParam.toString().length() > length) {
						resultMap.put("paramError", "true");
						resultMap.put("errorField", filedName);
						break;
					}
				}
			}

		}
	}

}
