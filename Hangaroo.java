package pages;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.*;

import pages.HangarooService.Pair;

public class Hangaroo extends BasePage{
	
	
	
	public Hangaroo(WebDriver driver) {
		super(driver);
		HangarooService hs=new HangarooService(driver);
		
	}

	String path="C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\hard\\forSikuli\\startButton.png";
	String gameCanvasxp="//div[@class='games-player']";
	HangarooService hs=new HangarooService(driver);
	
	public void playHangaro() throws InterruptedException, AWTException {
		
		driver.get("https://arcadethunder.com/word-games/hangaroo#google_vignette");
		scrollAndClick(driver.findElement(By.xpath("//div[@class='games-player']/div/ruffle-object")));
		
		
		try {
			clickOn(By.xpath("//*[text()='Got it!']"));
		} catch (Exception e) {
			System.out.println("GOT TI NOT CLICKED PASS TO SIKULI");
			hs.sikuliClick("C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\hard\\forSikuli\\gotitbutton.png");
		}
		
		
		
		Thread.sleep(2017);
		
		
		hs.sikuliClick(path);
		
		Thread.sleep(13000);
		// GAME STARTS HERE
		String base64String = hs.getBASE64ImageOfElement2(driver.findElement(By.xpath(gameCanvasxp)));
		String extractedText=hs.getTextFromImage(base64String);
		//String question = hs.getQuestion(extractedText);
		
		System.out.println(extractedText);
		
		
		int count = hs.countMatchesWithScalingAndSimilarity();
//		List<Pair> pairs = hs.countMatchesWithScalingAndSimilarity3();
//		
//		List<List<Integer>> listOfRowedCordinates = HangarooMath.groupKeysByValue(pairs);
//		
//		
//		listOfRowedCordinates.forEach(rc->System.out.println(rc));
//		List<Integer>averageDistances=new ArrayList<>();
//		List<List<Integer>>words=new ArrayList<>(); 
//		
//		listOfRowedCordinates.forEach(l->{
//			int avgForList=HangarooMath.mostFrequentDistance(l);
//			averageDistances.add(avgForList);
//			List<Integer> currentRow = HangarooMath.groupByEqualGap(l, avgForList);
//			words.add(currentRow);
//		});
//		
//		System.out.println(words);
//		List<Integer> wordsFinal = HangarooMath.flattenList(words);
		
		List<Integer> wordsFinal = getQuestionPattern();  
		System.out.println(wordsFinal);
		
		String path= HangaroAnswerFinder.getAnwserDepoPath2(extractedText);
		List<String> candidates = HangaroAnswerFinder.readFileLineByLine(path, wordsFinal);
	
		System.out.println(candidates);
		if(candidates.size()==1) {
		HangaroAnswerFinder.typeString(candidates.get(0)); }
		
		if(candidates.size()>1) {
			char c= HangaroAnswerFinder.typeCommonChar(candidates);
			List<Integer> rwordsFinal = getQuestionPattern();
			List<Integer> rpatternFinal = getQuestionPattern();
			
			System.out.println("IN RETRY");
			
			candidates.forEach(ca->{
				ca.replace(String.valueOf(c), " ");
				String[] splitted = ca.split("\\s+");
				for(String peace:splitted) {
					rpatternFinal.add(peace.length());
				}
				if(ca.equals(rwordsFinal)) {
					HangaroAnswerFinder.typeString(ca);
				}
			});
				
		}
	
	
	}
	
	
	public List<Integer> getQuestionPattern(){
		List<Pair> pairs = hs.countMatchesWithScalingAndSimilarity3();		
		List<List<Integer>> listOfRowedCordinates = HangarooMath.groupKeysByValue(pairs);				
		List<Integer>averageDistances=new ArrayList<>();
		List<List<Integer>>words=new ArrayList<>(); 		
		listOfRowedCordinates.forEach(l->{
			int avgForList=HangarooMath.mostFrequentDistance(l);
			averageDistances.add(avgForList);
			List<Integer> currentRow = HangarooMath.groupByEqualGap(l, avgForList);
			words.add(currentRow);
		});
		List<Integer> wordsFinal = HangarooMath.flattenList(words);
		
		return wordsFinal;
		
	}




}
