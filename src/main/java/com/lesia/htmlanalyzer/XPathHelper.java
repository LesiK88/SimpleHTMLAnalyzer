package com.lesia.htmlanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class XPathHelper {
	
	private static int getIndex(Element el, Iterable<Element> amongList) {
		
		int index = 0;
		for(Element e : amongList) {
			if (!e.equals(el) && e.tagName().equals(el.tagName())) {
				index++;
			}
		}
		
		return index;
	}
	
	public static List<String> getXPathElements(Element element, Document doc) {
		
		List<String> result = new ArrayList<>();
		
		Element current = element;
		
		while (current.parent() != null) {
			
			Element parent = current.parent();
			
			result.add(current.tagName() + "[" + getIndex(current, parent.children()) + "]");			
			current = parent;
		}
		
		result.add(current.tagName() + "[" + getIndex(current, doc.children()) + "]");
		Collections.reverse(result);
		
		return result;		
	}
}
