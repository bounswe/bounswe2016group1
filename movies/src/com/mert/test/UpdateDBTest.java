package com.mert.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mert.UpdateDB;

/**
 * Tests for UpdateDB class
 */

public class UpdateDBTest {

	@Test
	public void parseCountTest() {

		assertEquals(UpdateDB.parseCount("123^456"), UpdateDB.parseCount("123"));
		assertEquals(UpdateDB.parseCount("67^stringfortest^test"), 67);
	}

	@Test
	public void parseGenreTest() {

		assertEquals(UpdateDB.parseGenre("123@456"), UpdateDB.parseGenre("123"));
		assertEquals(UpdateDB.parseGenre("stringfortest^test@asd"), "stringfortest^test");

	}

	@Test
	public void parseLinkTest(String s) {
		assertEquals((UpdateDB.parseLink("asd/ada/test/test2")), "test2");

	}

}
