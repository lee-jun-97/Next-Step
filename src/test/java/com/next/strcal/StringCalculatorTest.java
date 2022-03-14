package com.next.strcal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
	
	@Test
	void sum() {
		
		StringCalculator strcal = new StringCalculator();
		String str ="";
		
		// case 1
		// given
		str = "1,2";
		// when , then
		assertEquals(3, strcal.sum(str));
		
		str = "1:7:3";
		assertEquals(11, strcal.sum(str));
		
		// case 2
		// given
		str = "//%\n1%7%8";
		// when , then
		assertEquals(16, strcal.sum(str));
		
		// case 3
		// given
		str = "-1:6";
		// when, then
		assertEquals(new RuntimeException(), strcal.sum(str));
		
		// case 4
		// given
		str = "//%\n1%-7%8";
		// when , then
		assertEquals(new RuntimeException(), strcal.sum(str));
		
	}
	

}
