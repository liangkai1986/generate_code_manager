package com.code.controller;

public class TestController extends BaseController{
	public void index() {
		renderText("这是index");
	}
	public void test() {
		renderText("这是test");
	}
}
