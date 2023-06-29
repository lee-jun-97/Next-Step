package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginController extends  AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@SuppressWarnings("unused")
	private void login(HttpRequest request, HttpResponse response) {
		
		try {
			User user = DataBase.findUserById(request.getParameter("userId"));
			
			if(user.login(request.getHeader("Cookie"))) {
				response.addHeader("Set-Cookie", "logined=true");
				response.sendRedirect("/index.html");
			}
			// user 존재 할 시 
			if(user != null) {
				// userId와 password 모두 일치할 경우
				if(user.getUserId().equals(request.getParameter("userId")) && user.getPassword().equals(request.getParameter("password"))) {
					response.sendRedirect("/user/index.html");
					// userId와 password 두 가지 중 하나라도 일치하지 않을 경우
				} else {
					response.sendRedirect("/user/login_failed.html");
				}
				// user 존재하지 않을 시
			} else {
				response.sendRedirect("/user/login_failed.html");
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
}
