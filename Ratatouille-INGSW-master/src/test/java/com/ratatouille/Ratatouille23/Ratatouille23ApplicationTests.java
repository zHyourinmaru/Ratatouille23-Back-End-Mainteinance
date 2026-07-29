package com.ratatouille.Ratatouille23;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


@SpringBootTest
class Ratatouille23ApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddNumbers() {
		// given
		int numberOne = 20;
		int numberTwo = 30;

		// when
		int result = underTest.add(numberOne, numberTwo);

		// then
		Assertions.assertEquals(result, 51);
	}


	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}

}
