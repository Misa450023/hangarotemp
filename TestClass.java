package tests;

import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HangaroAnswerFinder;
import pages.Hangaroo;
import pages.HangarooMath;
import pages.HangarooService;
import pages.NewGeoQuiz;
import pages.SequenceMemory;
import pages.SequenceMemoryService;







public class TestClass extends BaseTest{
	SequenceMemory sm;
	Hangaroo hangaro;
	NewGeoQuiz ng;
	@Test
	public void hangaroPlay() throws FindFailed, InterruptedException, AWTException {
		
		hangaro=new Hangaroo(driver);
		hangaro.playHangaro();
		
//		List<String>fo=
//				Arrays.asList("gazim","ziat","kabaz","kkesr","jeni","jzkub","jdkup");
//		char cha = HangaroAnswerFinder.findMostFrequentCharacter(fo);
//		
//		System.out.println(cha=='\u0000');
//		System.out.println(cha);
	
	}
	
	
	
	
//	@Test
//	public void sikuliOperations() {
//		
//		List<Integer>list=Arrays.asList(1,2,3,4,5,7,9,10,11,12);
//		
//		List<Integer> gap = HangarooMath.groupByEqualGap(list, 1);
//		
//		System.out.println(gap);
//		
//	}

	

	
};