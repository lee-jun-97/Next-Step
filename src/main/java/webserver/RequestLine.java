package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {
	
	private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
	
	private HttpMethod method;
	private String queryString;
	private String path;
	
	public RequestLine(String line) {
		log.debug("request lilne : {}", line);
		String[] tokens = line.split(" ");
		this.method = HttpMethod.valueOf(tokens[0]);
		
		String[] url = tokens[1].split("\\?");
		this.path = url[0];
		
		if(url.length == 2) {
			this.queryString = url[1];
		}
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getQueryString() {
		return queryString;
	}

}
