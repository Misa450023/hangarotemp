package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pages.HangarooService.Pair;

public class HangarooMath {

	public static List<List<Integer>> groupKeysByValue(List<Pair> pairs) {
		return pairs.stream()
				.collect(Collectors.groupingBy(Pair::getY, LinkedHashMap::new,
						Collectors.mapping(Pair::getX, Collectors.toList())))
				.values().stream().collect(Collectors.toList());
	}
	

	public static int mostFrequentDistance(List<Integer> numbers) {

		if (numbers.size() <= 1) {
			return 0;
		}

		Map<Integer, Integer> distanceCounts = new HashMap<>();
		for (int i = 0; i < numbers.size() - 1; i++) {
			int distance = Math.abs(numbers.get(i) - numbers.get(i + 1));
			distanceCounts.put(distance, distanceCounts.getOrDefault(distance, 0) + 1);
		}
		int mostFrequentDistance = 0;
		int maxCount = 0;
		for (Map.Entry<Integer, Integer> entry : distanceCounts.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxCount = entry.getValue();
				mostFrequentDistance = entry.getKey();
			}
		}
		return mostFrequentDistance;

	}

	public static List<Integer> groupByEqualGap(List<Integer> numbers, int gap) {
	    List<Integer> groupSizes = new ArrayList<>();
	    if (numbers.size() <= 1)
	        return groupSizes;

	    if (gap == 0) {
	        return groupSizes;
	    }

	    int currentGroupSize = 1;
	    boolean isSingleElementGroup = false;

	    for (int i = 0; i < numbers.size() - 1; i++) {
	        int distance = Math.abs(numbers.get(i) - numbers.get(i + 1));
	        
	        if (distance >= gap - 2 && distance <= gap + 2) {
	            currentGroupSize++;
	            isSingleElementGroup = false;
	        } else {
	            if (currentGroupSize > 1) {
	                groupSizes.add(currentGroupSize);
	            } else if (!isSingleElementGroup) {
	                groupSizes.add(1);
	                isSingleElementGroup = true;
	            }
	            currentGroupSize = 1;
	        }
	    }
	    if (currentGroupSize > 1) {
	        groupSizes.add(currentGroupSize);
	    } else if (!isSingleElementGroup) {
	        groupSizes.add(1);
	    }
	    return groupSizes;
	}
	
	
    public static List<Integer> flattenList(List<List<Integer>> listOfLists) {
        return listOfLists.stream()                
                .flatMap(List::stream)             
                .collect(Collectors.toList());    
    }
    
    public static HashMap<Integer,List<Integer>> answerStructure(List<Integer>list){
    	
    	HashMap<Integer,List<Integer>>map=new HashMap<Integer, List<Integer>>();
    	map.put(list.size(), list);
    	return map;    	
    }


}
