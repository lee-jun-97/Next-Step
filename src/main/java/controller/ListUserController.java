package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class ListUserController extends AbstractController {
	
	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		
	}
	
	public boolean isLogin(String str) {
		return false;
	}

}
