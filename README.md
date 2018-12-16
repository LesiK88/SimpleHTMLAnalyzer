# SimpleHTMLAnalyzer
Simple application that searches for the target element in a slightly changed html page

Uses the fllowing algorithm:
on diff document,
-selects elements with the same tag name
- among the selected elements, chooses elements with the best matching set of available attributes 
(so far the algorithm doesn't compare the values of attributes, it pays attention only to keys. This is because 
the sample documents suggest the values of the attributes can differ significantly from the original ones, 
hence, some more clever analysis is needed)
- among elements selected on the previous step the algorithm then chooses closest elements to target 
based on Levenshtein distance between XPaths. The latter is the resulting set.
