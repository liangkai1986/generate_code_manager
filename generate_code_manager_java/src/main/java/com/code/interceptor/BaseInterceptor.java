package com.code.interceptor;

import com.code.bean.ResultData;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class BaseInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		try {
			inv.getController().getResponse().addHeader("Access-Control-Allow-Origin", "*");
			inv.getController().getResponse().setHeader("Access-Control-Allow-Methods",
					"GET, POST, OPTIONS, PUT, PATCH, DELETE");
//			try {
//				Thread.sleep(2000);// 记得删掉
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			inv.getController().getResponse().setHeader("Access-Control-Allow-Headers", "*");
			String method = inv.getController().getRequest().getMethod();
			if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method)) {
				inv.invoke();
			} else {
				inv.getController().renderNull();
			}
		} catch (Exception e) {
			e.printStackTrace();
			inv.getController().renderJson(new ResultData().setOk(false).setMsg("未知异常，你看下后台日志。"));
		}
	}

}
