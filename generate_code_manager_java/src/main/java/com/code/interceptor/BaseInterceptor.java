package com.code.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class BaseInterceptor implements Interceptor{

	public void intercept(Invocation inv) {
		inv.getController().getResponse().addHeader("Access-Control-Allow-Origin", "*");	
		inv.invoke();
	}

}
