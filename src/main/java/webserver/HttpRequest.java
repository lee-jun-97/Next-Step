package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import util.HttpRequestUtils;
import util.HttpRequestUtils.Pair;

public class HttpRequest {
	
	Map<String, String> headerMap = new HashMap<>();
	Map<String, String> bodyMap;
	String method;
	String url;
	
	public HttpRequest(InputStream in) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		try {
			String input = br.readLine();
			
			method = input.split(" ")[0];
			
			String qs ;
			
			String[] temp = input.split(" ");
			
			// method == GET
			if(method.equals("GET")) {
				int index = temp[1].indexOf("?");
				url = temp[1].substring(0, index);
				
				qs = temp[1].substring(index + 1);
				bodyMap = HttpRequestUtils.parseQueryString(qs);
			} else if(method.equals("POST")) {
				url = temp[1];
			}
			
			input = br.readLine();
			
			while(!input.equals("")) {
				Pair pair = HttpRequestUtils.parseHeader(input);
				headerMap.put(pair.getKey(), pair.getValue());
				input = br.readLine();
			}
			
			if(method.equals("POST")) {
				bodyMap = HttpRequestUtils.parseQueryString(br.readLine());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getPath() {
		return url;
	}
	
	public String getHeader(String s) {
		return headerMap.get(s);
	}
	
	public String getParameter(String s) {
		return bodyMap.get(s);
	}

}
