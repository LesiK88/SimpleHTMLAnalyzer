package com.lesia.htmlanalyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ElementComparator {
	
	private static int getNumOfDifferentAttributeKeys(Set<String> target, Set<String> current) {

		Set<String> diffTC = new HashSet<String>(target);
		Set<String> diffCT = new HashSet<String>(current);
		diffTC.removeAll(current);
		diffCT.removeAll(target);
		return diffTC.size() + diffCT.size();
	}
	
	private static Set<String> getKeysSet(Iterable<Attribute> iterable) {
		
		Set<String> result = new HashSet<String>();
		
		iterable.forEach(x -> result.add(x.getKey()));
		return result;
	}
	
	public static List<Element> getCandidatesByTagAndAttributes(Element target, Document doc) {
		
		List<Element> result = new ArrayList<>();		
		
		Iterable<Element> tagCandidates = doc.getElementsByTag(target.tagName());
		
		Set<Element> candidatesList = new HashSet<Element>();
		tagCandidates.forEach(x -> candidatesList.add(x));
		
		if (!candidatesList.isEmpty()) {
			
			Set<String> ta = getKeysSet(target.attributes());
			
			Map<Element, Integer> attributeDistances = candidatesList.stream()
					.collect(Collectors.toMap(Function.identity(), 
							el -> getNumOfDifferentAttributeKeys(ta, getKeysSet(el.attributes()))));
			
			Integer minAttrDistance = attributeDistances.values().stream().min((x, y) -> Integer.compare(x, y)).get();
			
			attributeDistances.forEach((el, dist) -> {
				if (dist.equals(minAttrDistance)) {
					result.add(el);
				}
			});	
		}
		
		return result;					
	}
	
	
	public static List<Element> getLevenshteinClosestElements(List<Element> elements, Element target, Document doc) {
		
		List<Element> result = new ArrayList<>();
		List<String> targetXPath = XPathHelper.getXPathElements(target, doc);
		
		Map<Element, Integer> levDistances = elements.stream()
				.collect(Collectors.toMap(Function.identity(), x -> LevenshteinDistanceUtil
						.getLevenshteinDistance(XPathHelper.getXPathElements(x, doc), targetXPath)));
		
		Integer minLevDistance = levDistances.values().stream().min((x, y) -> Integer.compare(x, y)).get();
		
		levDistances.forEach((el, dist) -> {
			if (dist.equals(minLevDistance)) {
				result.add(el);
			}
		});	
		
		return result;
	}	
	
}
