package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	
	private DataOutputStream dos = null;
	private Map<String, String> header = new HashMap<String, String>();
	
	public HttpResponse(OutputStream out) {
		 dos = new DataOutputStream(out);
	}
	
	
	public void addHeader(String key, String value) throws Exception {
		header.put(key, value);
	}
	
	public void forward(String input) throws Exception {
		
		try {
			byte[] body = Files.readAllBytes(new File("./webapp" + input).toPath());
			
			if(input.endsWith(".css")) {
				header.put("Content-Type",  "text/css");
			} else if(input.endsWith(".js")){
				header.put("Content-Type", "application/jaccascript");
			} else {
				header.put("Content-Type", "text/html;charset=utf-8");
			}
			
			header.put("Content-Length", body.length + "");
			
			response200Hedaer(body.length);
			responseBody(body);
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void forwardBody(String body) {
		try {
			byte[] contents = body.getBytes();
			
			header.put("Content-Type", "text/html;charset=utf-8");
			header.put("Content-Length", contents.length + "");
			
			response200Hedaer(contents.length);
			responseBody(contents);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void sendRedirect(String url) throws Exception {
		try {
			dos.writeBytes("HTTP/1.1 302 Found\r\n");
			processHeader();
			dos.writeBytes("Location: " + url + "\r\n");
			dos.writeBytes("\r\n");
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void response200Hedaer(int length) {
        try {
        	dos.writeBytes("HTTP /1.1 200 OK \r\n");
        	processHeader();
        	dos.writeBytes("\r\n");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	public void responseBody(byte[] body) throws Exception {
		try {
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	

	public void processHeader() {
		try {
			Set<String> keys = header.keySet();
			for(String i : keys) {
				dos.writeBytes(i + ": " + header.get(i) + "\r\n");
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
	}

}
