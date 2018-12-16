package com.lesia.htmlanalyzer;

import java.util.List;

public class LevenshteinDistanceUtil {
	
	/*
	 * This method computesLevenshtein distance between two lists of String objects
	 * It is a well-known algorithm
	 * I found a library that implements this algorithm for lists of characters (i.e. strings) -- StringUtils,
	 * but didn't find anything handy which would implement the algorithm for lists of other objects
	 * Hence I copied an algorithm for lists of characters that I took from open sources 
	 * and adjusted it for my purposes
	 */
	
	public static Integer getLevenshteinDistance(List<String> el1, List<String> el2) {
		
		int[][] distance = new int[el1.size() + 1][el2.size() + 1];        
        
        for (int i = 0; i <= el1.size(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= el2.size(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= el1.size(); i++)                                 
            for (int j = 1; j <= el2.size(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((el1.get(i - 1).equals(el2.get(j - 1))) ? 0 : 1));
                                                                                 
        return distance[el1.size()][el2.size()];
	}
	
	private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    } 

}
