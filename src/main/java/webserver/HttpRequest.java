package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.IOUtils;

public class HttpRequest {
	
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	
	private RequestLine line;
	private RequestParams requestParams = new RequestParams();
	private HttpHeaders headers;
	
	
	public HttpRequest(InputStream in) {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			line = new RequestLine(createRequestLine(br));
			
			requestParams.addQueryString(line.getQueryString());
			headers = processHeader(br);
			requestParams.addBody(IOUtils.readData(br,  headers.getContentLength()));
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
	}
	
	private String createRequestLine(BufferedReader br) throws IOException {
		String line = br.readLine();
		
		if(line == null) {
			throw new IllegalStateException();
		}
		
		return line;
	}
	
	private HttpHeaders processHeader(BufferedReader br) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String line;
		while(!(line = br.readLine()).equals("")) {
			headers.add(line);
		}
		
		return headers;
	}
	
	public HttpMethod getMethod() {
		return line.getMethod();
	}
	
	public String getPath() {
		return line.getPath();
	}
	
	public String getHeader(String s) {
		return headers.getHeader(s);
	}
	
	public String getParameter(String s) {
		return requestParams.getParameter(s);
	}

}
