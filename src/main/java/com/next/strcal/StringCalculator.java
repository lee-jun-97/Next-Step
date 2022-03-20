package com.next.strcal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	
	public int cal(String str) {
		
		if(str.isEmpty()) {
			return 0;
		}
		
		String[] strList = str.split(",|:");

		Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str.trim());
		if ( m.find()) {
			String sep = m.group(1);
			strList = m.group(2).split("\\"+sep);
		}

		numCheck(strList);
		
		return sum(strList);
	}

	public int sum(String[] strList) {

		int sum = 0;

		for ( String i : strList ) {
			sum += Integer.parseInt(i) ;
		}

		return sum;
	}

	public void numCheck(String[] strList) {

		for ( String i : strList ) {
			if ( Integer.parseInt(i) < 0 ) {
				throw new RuntimeException();
			}
		}
	}

}
