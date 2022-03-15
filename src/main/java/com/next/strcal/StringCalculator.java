package com.next.strcal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringCalculator {
	
	public int split(String str) {

		String[] strList = {};

		if ( str.contains(",") == true) {
			strList = str.split(",");
		} else if ( str.contains(":") == true ) {
			strList = str.split(":");
		}
		
		return sum(strList);
	}

	public int sum(String[] str) {

		int sum = 0;

		for ( String i : str ) {
			sum += Integer.parseInt(i) ;
		}

		return sum;
	}
	
}
