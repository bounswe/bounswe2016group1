package com.mert;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
/**
 * 
 * @author Mert
 * Test file for Genre class
 */
public class GenreTest {


	@Test
	/**
	* Test constructor
	*/
	public void testGenre(){
		Genre testG = new Genre("test string 1",27,"test string 2");
		assertEquals(testG.g=="test string 1");
		assertEquals(testG.c==27);
		assertEquals(testG.s=="test string 2");
	}
	
}
