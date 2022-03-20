package com.next.strcal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
	
	@Test
	void sum() {
		
		StringCalculator strcal = new StringCalculator();
		
		// case 1
		// given, when , then
		assertEquals(3, strcal.cal("1,2"));
		
		// case 2
		// given, when, then
		assertEquals(11, strcal.cal("1:7:3"));
		
		// case 3
		// given, when , then
		assertEquals(16, strcal.cal("//%\n1%7%8"));

		// case 4
		// given, when, then
		assertEquals(14, strcal.cal("//^\n4^7^3"));
		
		// case 5
		// given, when, then
		assertThrows(RuntimeException.class, () -> {
			String ex = "-1:6";
			strcal.cal(ex);
		});
		
		// case 6
		// given
		// str = "//%\n1%-7%8";
		// when , then
		assertThrows(RuntimeException.class, () -> {
			String ex = "//%\n1%-7%8";
			strcal.cal(ex);
		});

		// case 7
		// given, when , then
		assertThrows(RuntimeException.class, () -> {
			String ex = "    //%\n1%-7%8";
			strcal.cal(ex);
		});

		// case 8
		// given,when, then
		assertEquals(0, strcal.cal(null));

		// case 9
		// given, when, then
		assertEquals(0, strcal.cal(""));
		
	}
	

}
