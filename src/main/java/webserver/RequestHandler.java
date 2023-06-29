package webserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.RequestMapping;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @SuppressWarnings("unused")
	public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
        	HttpRequest request = new HttpRequest(in);
        	HttpResponse response = new HttpResponse(out);
        	Controller controller = RequestMapping.getController(request.getPath());
        	
        	if(controller == null) {
        		String path = getDefaultPath(request.getPath());
        		response.forward(path);
        	} else {
        		controller.service(request, response);
        	}
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    private String getDefaultPath(String input) {
    	if(input.equals("/")) {
    		return "/index.html";
    	}
    	
    	return input;
    }
   
}
