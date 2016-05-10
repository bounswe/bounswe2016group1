package com.mert.test;
import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

import com.mert.*;
/**
 * 
 * @author Mert
 * Test file for Genre class
 */
public class GenreTest {

	Genre testG = new Genre("test string 1",27,"test string 2");
	@Test
	/**
	* Test constructor
	*/
	public void testGenre(){
		
		assertEquals(testG.genre,"test string 1");
		assertEquals(testG.count,27);
		assertEquals(testG.genreID,"test string 2");
	}
	
}
