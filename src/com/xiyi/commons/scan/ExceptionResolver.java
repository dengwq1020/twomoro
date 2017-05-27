package com.xiyi.commons.scan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.xiyi.commons.result.Result;
import com.xiyi.commons.utils.BeanUtils;
import com.xiyi.commons.utils.WebUtils;

/**
 * 异常处理，对ajax类型的异常返回ajax错误，避免页面问题
 * Created by L.cm
 * Date: 2016-24-03 16:18
 */
@Component
@SuppressWarnings("unchecked")
public class ExceptionResolver implements HandlerExceptionResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
		 HttpServletResponse response, Object handler, Exception e) {
		// log记录异常
		LOGGER.error(e.getMessage(), e);
		// 非控制器请求照成的异常
		if (!(handler instanceof HandlerMethod)) {
			return new ModelAndView("error/500");
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		if (WebUtils.isAjax(handlerMethod)) {
			Result result = new Result();
			result.setMsg(e.getMessage());
			return new ModelAndView(new MappingJackson2JsonView(), BeanUtils.toMap(result));
		}

		// 页面指定状态为500，便于上层的resion或者nginx的500页面跳转，由于error/error不适合对用户展示
//		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new ModelAndView("error/500");
	}

}