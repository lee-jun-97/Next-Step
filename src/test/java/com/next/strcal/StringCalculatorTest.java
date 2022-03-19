package com.next.strcal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		assertEquals(3, strcal.cal(str));
		
		// case 2
		// given
		str = "1:7:3";
		//when, then
		assertEquals(11, strcal.cal(str));
		
		// case 3
		// given
		str = "//%\n1%7%8";
		// when , then
		assertEquals(16, strcal.cal(str));

		// case 4
		// given
		// str = "//^\n4^7^3";
		str = "//%\n1%7%8";
		assertEquals(16, strcal.cal(str));
		
		// case 5
		// given
		// "-1:6"
		// when, then
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
		// given
		// str = "    //%\n1%-7%8";
		// when , then
		assertThrows(RuntimeException.class, () -> {
			String ex = "    //%\n1%-7%8";
			strcal.cal(ex);
		});

		
	}
	

}
