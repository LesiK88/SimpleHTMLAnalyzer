package com.lesia.htmlanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLAnalyzer {

	public static void main(String[] args) throws IOException {

		try {
			
			
			File target = new File(args[0]);
	        Document docT = Jsoup.parse(target, "UTF-8", "http://example.com/");
	        
	        File diff = new File(args[1]);
	        Document docD = Jsoup.parse(diff, "UTF-8", "http://example.com/");

	        Element targetEl = docT.getElementById(args[2]);
	        
	        if (targetEl == null) {
	        	
	        	System.out.println("Failed to find the specified target element by id");
	        } else {
	        	
	        	List<Element> taCandidates = ElementComparator.getCandidatesByTagAndAttributes(targetEl, docD);
	            List<Element> closest = ElementComparator.getLevenshteinClosestElements(taCandidates, targetEl, docT);
	            List<String> xPaths = closest.stream().map(x -> XPathHelper.getXPathElements(x, docD)
	            		.stream().collect(Collectors.joining(">")))
	            		.collect(Collectors.toList());
	     
	            if(xPaths.isEmpty()) {
	            	System.out.println("Sorry, analyzer has found no suitable elements");
	            } else {
	            	System.out.println("Analyzer has found the following matches based on: "
	            			+ "\n"
	            			+ "tag of the target element: set T of elements with the same tag was chosen"
	            			+ "\n"
	            			+ "similarity of attributes: set A of elements from T with closest list of attributes was chosen"
	            			+ "\n"
	            			+ "Levenshtein distance: Levenshtein-closest (by XPath) to target elements from A were chosen"
	            			+ "\n");
	                xPaths.forEach(x -> System.out.println(x));
	            }
	        }
			
			
	    }  catch (Exception ex) {
	        	
	        	System.out.println("Exception while processing request: " + ex.getMessage());
	    }     
        
	}

}
