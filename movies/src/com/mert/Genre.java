package com.mert;
/**
 * 
 * @author Mert
 * Basic class for Genres with a constructor that initializes variables.
 */
public class Genre {

	int ID;
	public String genre;
	public int count;
	public String genreID;
	public Genre(String g, int c,String s){
		ID=0;
		genre=g;
		count=c;
		genreID=s;
	}
	
}
