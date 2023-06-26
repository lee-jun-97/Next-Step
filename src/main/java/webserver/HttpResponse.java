package webserver;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class HttpResponse {
	
	DataOutputStream dos;
	
	public HttpResponse(OutputStream out) {
		 dos = new DataOutputStream(out);
	}
	
	
	public void addHeader(String key, String value) throws Exception {
		dos.writeBytes(key + ": " + value + "\r\n");
	}
	
	public void forward(String fileName) throws Exception {
		
		if(fileName.endsWith(".css")) {
			dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
		} else {
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		}
		dos.writeBytes("HTTP/1.1 200 OK\r\n");
		dos.writeBytes("Location: " + fileName + "\r\n");
		dos.writeBytes("\r\n");
	}
	
	public void forwardBody(String str) {
		
	}
	
	public void response200Heaer(int i) {
		
	}
	
	public void responseBody(byte[] body) throws Exception {
		dos.write(body, 0, body.length);
		dos.flush();
	}
	
	public void sendRedirect(String url) throws Exception {
		dos.writeBytes("HTTP/1.1 302 Found\r\n");
		dos.writeBytes("Location: " + url + "\r\n");
		dos.writeBytes("\r\n");
	}

	public void processHeader() {
		
	}

}
