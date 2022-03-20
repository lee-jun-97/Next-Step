package com.next.strcal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	
	public int cal(String str) {
		
		String[] strList = {};

		if ( isBlank(str) ) {
			return 0;
		}

		if ( isContains(str)) {
			strList = basicString(str.trim());
		}
		

		Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str.trim());
		if ( m.find()) {
			strList = customString(m);
		}

		numCheck(strList);
		
		return sum(toInt(strList));
	}

	private boolean isBlank(String str) {
		// str.isEmpty() 를 앞조건에 넣었을 시 NPE 발생.
		// null 인지 먼저 check 한 후 str.isEmpty() 실행.
		return str == null || str.isEmpty();
	}

	private boolean isContains(String str) {
		return str.contains(",") || str.contains(":") ;
	}

	private int sum(int[] intList) {

		int sum = 0;

		for ( int i : intList ) {
			sum += i ;
		}

		return sum;
	}

	private int[] toInt(String[] strList) {

		int[] intList = new int[strList.length];

		for ( int i=0; i<strList.length ; i++ ) {
			intList[i] = Integer.parseInt(strList[i]);
		}

		return intList;
	}


	private void numCheck(String[] strList) {

		for ( String i : strList ) {
			if ( Integer.parseInt(i) < 0 ) {
				throw new RuntimeException();
			}
		}
	}

	private String[] basicString(String str) {
		return str.split(",|:");
	}

	private String[] customString(Matcher m) {
		// split 특정 문자 동작 안 하는 문제 해결 위해 구분자 앞에 \\ 추가.
		return m.group(2).split("\\"+m.group(1));
	}

}
