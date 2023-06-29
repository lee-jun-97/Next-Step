package controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ListUserController extends AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);
	
	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		try {
    		if(!isLogin(request.getHeader("Cookie"))) {
    			response.sendRedirect("/user/login.html");
    			return;
    		}
    		
    		StringBuilder sb = new StringBuilder();
    		Collection<User> users = DataBase.findAll();
    		
    		for(User i : users) {
    			sb.append("<tr>");
    			sb.append("<td>" + i.getUserId() + "</td>");
    			sb.append("<td>" + i.getName() + "</td>");
    			sb.append("<td>" + i.getEmail() + "</td>");
    			sb.append("<tr>");
    		}
    		response.forwardBody(sb.toString());
    	} catch(Exception e) {
    		log.error(e.getMessage());
    	}
	}
	
	public boolean isLogin(String str) {
		return false;
	}

}
