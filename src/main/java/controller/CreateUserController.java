package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class CreateUserController extends AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		try {
			User user = new User(
					request.getParameter("userId"),
					request.getParameter("pasword"),
					request.getParameter("name"),
					request.getParameter("email")
					);
			DataBase.addUser(user);
			response.sendRedirect("/index.html");
		} catch(Exception e) {
			log.error(e.getMessage());
		}	
	}

}
