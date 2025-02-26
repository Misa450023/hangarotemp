package pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HangaroAnswerFinder {
	
//	categories.add("OSCAR WINNER");
//	categories.add("TELEVISION SHO");
//	categories.add("BIGGEST MOUNTAIN PEAKS"); ? ? ? 
//	categories.add("PHRASE");
//	categories.add("SONG TITLE");
//	categories.add("MOVIE TITLE");
//	categories.add("BOOK");
//	categories.add("PRESIDENTS OF THE USA");
//	categories.add("VICE PRESIDENTS OF THE USA"); ???
//	categories.add("CAPITAL CITIES OF COUNTRIES");
//	categories.add("SPACE AND THE UNIVERSE");
//	categories.add("STAR WARS CHARACTER");
//	categories.add("HOLIDAYS AROUND THE WORLD");
	
//	\BOOK TITLE AUTHOR.txt
//	\CONSTELATIONS.txt
//	\EVENTS.txt
//	\FAMOUS PLACES AND LANDMARKS.txt
//	\FIRST LADIES OF THE USA.txt
//	\MOVIE TITLE.txt
//	\OSCAR WINNERS.txt
//	\PHRASES.txt
//	\PRESIDENTS OF THE USA.txt
//	\SHAKESPEARE.txt
//	\SONG TITLE ARTIST.txt
//	\TELEVISION SHOW.txt
	
	
	public static String getAnwserDepoPath2(String question) {
	    String path = "";
	    
	    System.out.println(question);
	    
	    if (question.contains("OSCAR WINNER")) {
	        path = "\\OSCAR WINNERS.txt";
	    } else if (question.contains("TELEVISION SHOW")) {
	        path = "\\TELEVISION SHOW.txt";
	    } else if (question.contains("PHRASES")) {
	        path = "\\PHRASES.txt";
	    } else if (question.contains("SONG")&&question.contains("ARTIST")) {
	        path = "\\SONG TITLE ARTIST.txt";
	    } else if (question.contains("MOVIE TITLE")) {
	        path = "\\MOVIE TITLE.txt";
	    } else if (question.contains("BOOK")&&question.contains("AUTHOR")) {
	        path = "\\BOOK TITLE AUTHOR.txt";
	    } else if (question.contains("PRESIDENTS OF THE USA")) {
	        path = "\\PRESIDENTS OF THE USA.txt";
	    } else if (question.contains("CAPITAL")) {
	        path = "\\CAPITAL CITY AND COUNTRY.txt";
	    } else if (question.contains("STAR")&&question.contains("CAST")) {
	        path = "\\TV SHOW ACTORS.txt";    
	    } else if (question.contains("STAR")&&question.contains("CHARACTER")) {
	        path = "\\STAR TREK CHARACTER.txt";    
	    } else if (question.contains("CAPITAL")&&question.contains("USA")) {
	        path = "\\CAPITALS OF USA.txt";    
	    } else if (question.contains("VICE")&&question.contains("PRESIDENTS")&&question.contains("USA")) {
	        path = "\\VICE PRESIDENTS OF USA.txt";    
	    } else if (question.contains("FIRST")&&question.contains("LADIE")&&question.contains("USA")) {
	        path = "\\FIRST LADIES USA.txt";   
	    } else if (question.contains("TREK")&&question.contains("THIN")) {
	        path = "\\STAR TREK THINGS.txt";    
	    } else if (question.contains("GOVERNOR")&&question.contains("USA")) {
	        path = "\\GOVERNORS USA.txt"; 
	    } else if (question.contains("SPACE")&&question.contains("UNIVERSE")) {
	        path = "\\SPACE AND UNIVERSE.txt";
	        
	      
	            
	    } else {
	        System.out.println("Invalid question!");
	    }
	    
	    return "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\hard\\forSikuli" + path;
	}

	
	public static String getAnwserDepoPath(String question) {
		String path="";
		
		System.out.println(question);
		
        switch (question.trim()) {
        case "OSCAR WINNER":
            path="\\OSCAR WINNERS.txt";
            break;
        case "TELEVISION SHOW":
            path="\\TELEVISION SHOW.txt";
            break;
        case "PHRASES":
            path="\\PHRASES.txt";
            break;
        case "SONG TITLE LARTIST\r\n"
        		+ "":
            path="\\SONG TITLE ARTIST.txt";
            break;
        case "MOVIE TITLE":
            path="\\MOVIE TITLE.txt";
            break;
        case "BOOK-TITLE [AUTHOR\r\n"
        		+ "":
            path="\\BOOK TITLE AUTHOR.txt";
            break;
        case "PRESIDENTS OF THE USA":
            path="\\PRESIDENTS OF THE USA.txt";
            break;
        case "CAPITAL CITIES OF COUNTRIES":
            path="\\CAPITAL CITY AND COUNTRY.txt";
            break;
        default:
            System.out.println("Invalid day of the week!");
            break;
    }
        
        return "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\hard\\forSikuli"+path;
        
	}
	
	
    public static List<String> readFileLineByLine(String filePath,List<Integer>wordsLengths) {        
    	List<String>candidateAnswers=new ArrayList<>();
    	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;            
            while ((line = br.readLine()) != null) {
            	line=line.replace("'", " ");
            	String finalLine = line.replaceAll("[^a-zA-Z ]", "").trim();
            	
            	System.out.println(finalLine);
            	String[] words = finalLine.split("\\s+");
            	List<Integer>wordLength=new ArrayList<>();
            	for(String word:words) {
            		wordLength.add(word.length());
            	}
            	
            	System.out.println(wordLength);
            	System.out.println(wordsLengths);
            	
            	if(wordLength.equals(wordsLengths)) {
            		candidateAnswers.add(finalLine);
            	}
            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return candidateAnswers;
    }
	
	public static char typeCommonChar(List<String>candidates) {
		char c;
		c = findCommonCharacter(candidates);
		if(c=='\u0000') {
			c=findMostFrequentCharacter(candidates);
		}
		typeString(String.valueOf(c));
		return c;

	}
	
	
	public static char findMostFrequentCharacter(List<String> strings) {
	    if (strings == null || strings.isEmpty()) {
	        return '\u0000';
	    }

	    Map<Character, Set<Integer>> charOccurrenceMap = new HashMap<>();

	    for (int i = 0; i < strings.size(); i++) {
	        String str = strings.get(i);
	        Set<Character> uniqueCharsInString = new HashSet<>();

	        for (char c : str.toCharArray()) {
	            uniqueCharsInString.add(c);
	        }

	        for (char c : uniqueCharsInString) {
	            charOccurrenceMap
	                .computeIfAbsent(c, k -> new HashSet<>())
	                .add(i);
	        }
	    }

	    char resultChar = '\u0000';
	    int maxCount = 0;

	    for (Map.Entry<Character, Set<Integer>> entry : charOccurrenceMap.entrySet()) {
	        int count = entry.getValue().size();

	        if (count > maxCount) {
	            maxCount = count;
	            resultChar = entry.getKey();
	        }
	    }

	    return resultChar;
	}

	
	
    
	public static char findCommonCharacter (List<String> strings) {

		if (strings == null || strings.isEmpty()) {
		return 0;
		}

		Set<Character> commonChars = new HashSet<>();
		for (char c: strings.get(0).toCharArray()) {
		commonChars.add(c);
		}
		for (int i = 1; i < strings.size(); i++) {
		Set<Character> currentStringChars = new HashSet<>();
		for (char c: strings.get(i).toCharArray()) {
		currentStringChars.add(c);
		}
		commonChars.retainAll(currentStringChars);
		if (commonChars.isEmpty()) {
		return 0;
		}

		}
		return commonChars.iterator().next();

	}
	
    
    public static void typeString(String input) {
        try {
            Robot robot = new Robot();
            String typedCharacters = "";

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);

                if (Character.isLetter(c)) {

                    if (typedCharacters.indexOf(c) != -1) {
                        continue;
                    }

                    typedCharacters += c;

                    if (Character.isUpperCase(c)) {
                        robot.keyPress(KeyEvent.VK_SHIFT); // Press SHIFT key
                        robot.keyPress(Character.toUpperCase(c)); // Press the corresponding uppercase key
                        robot.keyRelease(Character.toUpperCase(c)); // Release the uppercase key
                        robot.keyRelease(KeyEvent.VK_SHIFT); // Release the SHIFT key
                    }
                    
                    else if (Character.isLowerCase(c)) {
                        robot.keyPress(Character.toLowerCase(c)); // Press the corresponding lowercase key
                        robot.keyRelease(Character.toLowerCase(c)); // Release the lowercase key
                    }
                    robot.delay(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	

}
