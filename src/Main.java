import java.io.IOException;
import java.text.ParseException;

import Pricing.PricingManager;

import com.itextpdf.text.DocumentException;

public class Main {
	static int year = 2015;
	static int month = 9;
	
	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		long startTime = System.currentTimeMillis();
		System.out.println("-->Main started");
		
//		System.setProperty("https.proxyHost", "185.33.33.132");
//		System.setProperty("https.proxyPort", "8080");
		
//		StatisticGenerator.genABalticRCars();
		
//		SourceReader.getCarScanner();
		
		
		SourceReader.getScannerSilenium();
		long endTime = System.currentTimeMillis();
		System.out.println("-->Main done" + "\n" + (endTime - startTime) / 1000);
	}
}
