package pages;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HangarooService {

	WebDriver driver;
	String apiKey = "K76755389306594888957";
	String OCR_API_URL = "https://api.ocr.space/parse/image";
	String boxPath = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\hard\\forSikuli\\box.jpg";
	List<String> categories = new ArrayList<>();

	public HangarooService(WebDriver driver) {
		this.driver = driver;
		categories.add("OSCAR WINNER");
		categories.add("TELEVISION SHO");
		categories.add("BIGGEST MOUNTAIN PEAKS");
		categories.add("PHRASE");
		categories.add("SONG TITLE");
		categories.add("MOVIE TITLE");
		categories.add("BOOK");
		categories.add("PRESIDENTS OF THE USA");
		categories.add("VICE PRESIDENTS OF THE USA");
		categories.add("CAPITAL CITIES OF COUNTRIES");
		categories.add("CAPITAL CITIES OF THE USA");
		categories.add("SPACE AND THE UNIVERSE");
		categories.add("STAR WARS CHARACTER");
		categories.add("HOLIDAYS AROUND THE WORLD");
		categories.add("STAR TREK CHARACTER");
	}

	BiPredicate<String, HashMap<Integer, List<Integer>>> hasExactWordsAndLengths = (text, criteria) -> {

		String[] words = text.split("\\s+");
		int expectedWordCount = criteria.keySet().iterator().next();
		List<Integer> expectedWordLengths = criteria.get(expectedWordCount);
		if (words.length != expectedWordCount)
			return false;
		return IntStream.range(0, words.length).allMatch(i -> words[i].length() == expectedWordLengths.get(i));

	};

	public void sikuliClick(String path) {
		Screen s = new Screen();
		Pattern pattern = new Pattern(path);

		try {

			for (double scale = 0.8; scale <= 1.2; scale += 0.1) {
				System.out.println("IN A LOOP CURRENT SCALE==" + scale);
				pattern = pattern.resize((float) scale);
				Match match = s.wait(pattern.similar(0.4), 5);

				if (match != null) {
					System.out.println("MATCH IS NOT NULL");
					match.click();
					break;
				}
			}
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	public int countMatchesWithScalingAndSimilarity() {
		Screen screen = new Screen();
		Pattern pattern = new Pattern(boxPath);

		int matchCount = 0;

		try {

			for (double scale = 0.8; scale <= 1.2; scale += 0.1) {
				System.out.println("Checking for scale: " + scale);
				Pattern scaledPattern = pattern.resize((float) scale);
				Iterator<Match> matches = screen.findAll(scaledPattern.similar(0.4));
				while (matches.hasNext()) {
					matches.next();
					matchCount++;
				}

				System.out.println("Matches found at scale " + scale + ": " + matchCount);
			}

			System.out.println("Total matches found: " + matchCount);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
		return matchCount;
	}

	public String getBASE64ImageOfElement(WebElement element) {
		String script = "return arguments[0].toDataURL('image/png');";
		String base64Image = (String) ((JavascriptExecutor) driver).executeScript(script, element);

		return base64Image;
	}

	public String getBASE64ImageOfElement2(WebElement element) {

		File screenshot = element.getScreenshotAs(OutputType.FILE);
		String base64Image = null;
		try {

			byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
			base64Image = java.util.Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "data:image/png;base64," + base64Image;
	}

	public String getTextFromImage(String base64Image) {

		RequestSpecification spec = RestAssured.given();

		Response response = spec.multiPart("base64Image", base64Image).multiPart("apikey", apiKey)
				.formParam("isOverlayRequired", "true").formParam("detectOrientation", "true")
				.formParam("scale", "true").formParam("language", "eng").post(OCR_API_URL);

		response.then().log().all();

		String extractedText = response.jsonPath().getString("ParsedResults[0].ParsedText");
		return extractedText;
	}

	public String getQuestion(String input) {

		String[] lines = input.split("\n");

		for (String line : lines) {
			if (line.matches("[A-Z/\\-.?! ]+")) {
				return line;
			}
		}

		return Arrays.stream(lines).filter(line -> categories.stream().anyMatch(line::contains)).findFirst()
				.orElse("No matching line found.");
	}

	public int countMatchesWithScalingAndSimilarity2() {
		Screen screen = new Screen();
		Pattern pattern = new Pattern(boxPath);
		LinkedHashMap<Integer, Integer> cordinates = new LinkedHashMap<Integer, Integer>();
		Pattern scaledPattern = null;
		int matchCount = 0;
		Match mainMatch = null;
		try {
			for (double scale = 0.8; scale <= 1.2; scale += 0.1) {

				scaledPattern = pattern.resize((float) scale);
				Match mmatch = screen.wait(scaledPattern.similar(0.4), 5);

				if (mmatch != null) {
					pattern = scaledPattern;
					break;
				}

			}
			Iterator<Match> matches = screen.findAll(scaledPattern.similar(0.4));
			while (matches.hasNext()) {
				Match match = matches.next();
				int x = match.getX();
				int y = match.getY();
				Region matchRegion = new Region(match.getX(), match.getY(), match.getW(), match.getH());

				Image image = matchRegion.getImage();
				BufferedImage matchImage = image.get();

				if (hasNoWhitePixel(matchImage) && hasMajorityGreen(matchImage)) {

					cordinates.put(x, y);
					matchCount++;
				}

			}

		} catch (FindFailed e) {
			e.printStackTrace();
		}

		System.out.println(cordinates);
		return matchCount;
	}

	private boolean hasNoWhitePixel(BufferedImage image) {

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color pixelColor = new Color(image.getRGB(x, y));
				if (isWhite(pixelColor)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isWhite(Color color) {
		return color.getRed() > 240 && color.getGreen() > 240 && color.getBlue() > 240;
	}

	private boolean hasMajorityGreen(BufferedImage image) {
		int greenCount = 0;
		int totalPixels = image.getWidth() * image.getHeight();

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color pixelColor = new Color(image.getRGB(x, y));

				if (pixelColor.getGreen() > pixelColor.getRed() && pixelColor.getGreen() > pixelColor.getBlue()) {
					greenCount++;
				}
			}
		}

		return greenCount > (totalPixels / 2);
	}

	public List<Pair> countMatchesWithScalingAndSimilarity3() {
		Screen screen = new Screen();
		Pattern pattern = new Pattern(boxPath);
		Pattern scaledPattern = null;
		int matchCount = 0;
		Match mainMatch = null;
		List<Pair> pairList = new ArrayList<>();

		try {
			for (double scale = 0.8; scale <= 1.2; scale += 0.1) {

				scaledPattern = pattern.resize((float) scale);
				Match mmatch = screen.wait(scaledPattern.similar(0.4), 5);

				if (mmatch != null) {
					pattern = scaledPattern;
					break;
				}

			}

			List<Match> matchList = new ArrayList<>();
			Iterator<Match> matches = screen.findAll(scaledPattern.similar(0.4));
			while (matches.hasNext()) {
				Match match = matches.next();
				matchList.add(match);
			}

			Collections.sort(matchList, new Comparator<Match>() {
				@Override
				public int compare(Match match1, Match match2) {
					int result = Integer.compare(match1.getY(), match2.getY());
					if (result == 0) {
						return Integer.compare(match1.getX(), match2.getX());
					}
					return result;
				}
			});

			for (Match match : matchList) {
				int x = match.getX();
				int y = match.getY();
				Region matchRegion = new Region(x, y, match.getW(), match.getH());

				Image image = matchRegion.getImage();
				BufferedImage matchImage = image.get();

				if (hasNoWhitePixel(matchImage) && hasMajorityGreen(matchImage)) {
					pairList.add(new Pair(x, y));
					matchCount++;
				}
			}

		} catch (FindFailed e) {
			e.printStackTrace();
		}

		

		return pairList;
	}

	public class Pair {
		private int x;
		private int y;

		// Constructor
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// Getter for x
		public int getX() {
			return x;
		}

		// Setter for x
		public void setX(int x) {
			this.x = x;
		}

		// Getter for y
		public int getY() {
			return y;
		}

		// Setter for y
		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "Pair{" + "x=" + x + ", y=" + y + '}';
		}
	}

}
