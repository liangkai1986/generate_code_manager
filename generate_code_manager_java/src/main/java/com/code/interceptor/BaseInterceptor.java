package com.code.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class BaseInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		inv.getController().getResponse().addHeader("Access-Control-Allow-Origin", "*");	
		inv.getController().getResponse().setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
		    // Request headers you wish to allow
		inv.getController().getResponse().setHeader("Access-Control-Allow-Headers", "*");
		String method = inv.getController().getRequest().getMethod();
		if("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method) ){
			inv.invoke();
		}else{
			inv.getController().renderNull();
		}
		
	}

}
