package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

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
        	
        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
        	
        	String line = br.readLine();
        	
        	String[] input = line.split(" ");
        	
        	int contentLength = 0;
        	boolean logined = false;
        	
        	while(!line.equals("")) {
        		
        		if(line == null) {
        			break;
        		}
        		
        		log.info("Header : {}", line);
        		
        		if(line.split(" ")[1].endsWith(".css")) {
             		
             		byte[] body = Files.readAllBytes(new File("./webapp" + line.split(" ")[1]).toPath());
             		
             		DataOutputStream dos = new DataOutputStream(out);
             		reponse200CssHeader(dos, body.length);
             		responseBody(dos, body);
             	
         		}
        		
        		
        		if(line.contains("Content-Length:")) {
        			contentLength = getContentLength(line);
        		} else if(line.contains("Cookie:")) {        			
        			logined = isLogined(line);
        		} 
        		
        		line = br.readLine();
        		
        	}
        	
        	if(input[1].equals("/index.html")) {
    			
    			byte[] body = Files.readAllBytes(new File("./webapp" + input[1]).toPath());
    			
    			DataOutputStream dos = new DataOutputStream(out);
    			
    			response200Header(dos, body.length);
                responseBody(dos, body);
             // 회원가입 페이지 이동	
    		} else if(input[1].equals("/user/form.html")) {
    			
    			byte[] body = Files.readAllBytes(new File("./webapp" + input[1]).toPath());
    			
    			DataOutputStream dos = new DataOutputStream(out);
    			
    			response200Header(dos, body.length);
                responseBody(dos, body);
             // GET 방식 회원가입 
    		} else if(input[1].contains("?")) {
        			int index = input[1].indexOf("?");
        			
        			String url = input[1].substring(0, index);
        			String qs = input[1].substring(index+1);
        			
        			Map<String, String> map = HttpRequestUtils.parseQueryString(qs);
        			
        			User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
        			
        			log.info(user.toString());
        			
        			DataOutputStream dos = new DataOutputStream(out);
        			
        			response302Header(dos, "/index.html");
                    responseBody(dos);
                    
            // POST 방식 회원가입 	
    		} else if(input[1].equals("/user/create")) {
	        	
	        	String bodyData = IOUtils.readData(br, contentLength);
	        	
	        	Map<String,String> map = HttpRequestUtils.parseQueryString(bodyData);
	        	
	        	DataBase.addUser(new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email")));
	        	
	        	DataOutputStream dos = new DataOutputStream(out);
	        	
	        	response302Header(dos, "/index.html");
	        	responseBody(dos);
	        // 로그인 페이지 이동
        	} else if(input[1].equals("/user/login.html")) {
    		
    			byte[] body = Files.readAllBytes(new File("./webapp" + input[1]).toPath());
    			
    			DataOutputStream dos = new DataOutputStream(out);
    			
    			response200Header(dos, body.length);
                responseBody(dos, body);
    		// 로그인 시도	
    		} else if(input[1].equals("/user/login")) {
    			
    			String bodyData = IOUtils.readData(br, contentLength);
    			
    			Map<String,String> map = HttpRequestUtils.parseQueryString(bodyData);
    			
    			User user = DataBase.findUserById(map.get("userId")); 
    			
    			DataOutputStream dos = new DataOutputStream(out);

    			// user 존재 할 시 
    			if(user != null) {
    				// userId와 password 모두 일치할 경우
	    			if(user.getUserId().equals(map.get("userId")) && user.getPassword().equals(map.get("password"))) {
	    				loginSuccessResponseHeader(dos);
	    			// userId와 password 두 가지 중 하나라도 일치하지 않을 경우
	    			} else {
	    				loginFailResponseHeader(dos);
	    			}
	    		// user 존재하지 않을 시
    			} else {
    				loginFailResponseHeader(dos);
    			}
    			
    			responseBody(dos);
        		// 로그인 실패 시 실패 페이지로 이동	
    		} else if(input[1].equals("/user/login_failed.html")) {
    			
    			byte[] body = Files.readAllBytes(new File("./webapp" + input[1]).toPath());
    			
    			DataOutputStream dos = new DataOutputStream(out);
    			
    			response200Header(dos, body.length);
                responseBody(dos, body);
    		
             // list 조회 시
    		} else if(input[1].equals("/user/list")) {

        			if(logined) {
        				
        				StringBuilder sb = new StringBuilder();
        				
        				for(User i : DataBase.findAll()) {
        					sb.append("<p>" + i.getUserId() + " / " + i.getName() + "</p>");
        					sb.append("</br>");
        				}
        				
        				byte[] body = sb.toString().getBytes();
        				
        				DataOutputStream dos = new DataOutputStream(out);
            			
            			response200Header(dos, body.length);
                        responseBody(dos, body);
        					
        				
        			} else {
            			
            			DataOutputStream dos = new DataOutputStream(out);
            			
            			response302Header(dos, "/login.html");
                        responseBody(dos);
        			}
    			
    			DataOutputStream dos = new DataOutputStream(out);
    			
    			response302Header(dos, "/index.html");
    			responseBody(dos);
    		} else {
    			responseResource(out, input[1]);
    		}
    		
    		
        	
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private int getContentLength(String line) {
    	String[] token = line.split(":");
    	
    	return Integer.parseInt(token[1].trim());
    }
    
    private boolean isLogined(String line) {
    	String[] token = line.split(":");
    	
    	Map<String, String> map = HttpRequestUtils.parseQueryString(token[1]);
    	
    	return Boolean.parseBoolean(map.get("logined"));
    }
    
    private void reponse200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
    	 try {
             dos.writeBytes("HTTP/1.1 200 OK \r\n");
             dos.writeBytes("Content-Type: text/css\r\n");
             dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
             dos.writeBytes("\r\n");
         } catch (IOException e) {
             log.error(e.getMessage());
         }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void response302Header(DataOutputStream dos, String url) {
    	
    	try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    	
    }
    
    private void loginFailResponseHeader(DataOutputStream dos) {
    	
    	try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: /user/login_failed.html\r\n");
            dos.writeBytes("Set-Cookie: logined=false\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    	
    }
    
    private void loginSuccessResponseHeader(DataOutputStream dos) {
    	
    	try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("Set-Cookie: logined=true\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    	
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void responseBody(DataOutputStream dos) {
    	
    	try {
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    	
    }
    
    private void responseResource(OutputStream out, String url) throws IOException{
    	
    	DataOutputStream dos = new DataOutputStream(out);
    	
    	byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
    	response200Header(dos, body.length);
    	responseBody(dos, body);
    	
    	
    }
}
