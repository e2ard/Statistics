package Source;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import GetInfoMultiThread.RentalThread;
import Logging.LogIn;
import Pricing.PricingManager;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.itextpdf.text.DocumentException;

public class Main {
	static int year = 2015;
	static int month = 9;
	static int pickupDay = 1;
	
	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		long startTime = System.currentTimeMillis();
		System.out.println("-->Main started");
		
//		System.setProperty("https.proxyHost", "204.14.14.230");
//		System.setProperty("https.proxyPort", "8080");
		
//		StatisticGenerator.genABalticRCars();
		
//		String rental = "http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&doHour=10&searchType=&locationName=Vilnius+Airport&doFiltering=false&puSameAsDo=on&city=Vilnius&puHour=10&dropCountryCode=&dropCountry=Lithuania&puDay=1&filterTo=1000&dropLocation=388026&driverage=on&doDay=2&countryCode=&dropLocationName=Vilnius+Airport&country=Lithuania&enabler=&filterFrom=0&puMonth=9&puMinute=0&doMonth=9&doYear=2015&puYear=2015&fromLocChoose=true&filterName=CarCategorisationSupplierFilter";
//		String norwegian = "https://cars.cartrawler.com/norwegian/en/book?clientID=242447&elID=0726201134239873913&countryID=LT&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&__utma=66135985.2092701990.1437977508.1437977508.1437977508.1&__utmb=66135985.3.10.1437977508&__utmc=66135985&__utmx=-&__utmz=66135985.1437977508.1.1.utmcsr&__utmv=-&__utmk=218255774#/vehicles";
//
//		Sites s = new Sites();
//		s.setSiteName(rental);
//		for(int i = 0; i < 20; i++){
//			System.out.println(s.initDate(Main.year, Main.month, Main.pickupDay));
//			System.out.println(s.setDate(Main.year, Main.month, Main.pickupDay));
//		}
//		s.setSiteName(norwegian);
//		for(int i = 0; i < 20; i++){
//			System.out.println(s.initDate(Main.year, Main.month, Main.pickupDay));
//			System.out.println(s.setDate(Main.year, Main.month, Main.pickupDay));
//		}
		String site = 
				"http://www.rentalcars.com/SearchResults.do;jsessionid=45DAA2FD5413E2B05737DEB1FD9F67A2.node413a?tj_pe_exp=t%3D1439931119387.e%3D24031-A%40hash%401439991434551%2C24286-A%40hash%401439931124864&enabler=&country=Lithuania&city=Vilnius&location=388026&puDay=23&puMonthYear=8-2015&puDay=23&puMonth=8&puYear=2015&puHour=10&puMinute=0&dropCountry=Lithuania&dropCity=Vilnius&dropLocation=388026&puSameAsDo=on&doDay=24&doMonthYear=8-2015&doDay=24&doMonth=8&doYear=2015&doHour=10&doMinute=0&chinese-license=on&searchType=&doFiltering=&filterTo=1000&filterFrom=0&emptySearchResults=false&driversAge=25&locationName=Vilnius+Airport&dropLocationName=Vilnius+Airport&showAllCars=true";
		StatisticGenerator.getPdfFastBeta();
		
		//		for(int i = Main.pickupDay; i < 30 + pickupDay; i++){
//			Main.pickupDay = i;
//			if(pickupDay > 31)
//				pickupDay = (i + pickupDay) % 31;
//		}
	    
		
		
	      
		
//		String s1 = "http://www.rentalcars.com/SearchResults.do;jsessionid=410769DE51CD7D9A93CAC336386A94A2.node429a?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&exSuppliers=&doHour=10&filterName=CarCategorisationSupplierFilter&searchType=&locationName=Vilnius+Oro+Uostas&doFiltering=&doMonthYear=9-2015&puSameAsDo=on&city=Vilnius&puMonthYear=9-2015&chinese-license=on&tj_pe_exp=t%3D1438627890401.e%3D23303-B%40hash%401438627893010&puHour=10&ordering=recommended&dropCountry=Lietuva&emptySearchResults=false&puDay=1&filterTo=1000&dropLocation=388026&doDay=2&dropLocationName=Vilnius+Oro+Uostas&country=Lietuva&enabler=&filter_carclass=economy&advSearch=&filterAdditionalInfo=&filterFrom=0&puMonth=9&puMinute=0&doMonth=9&doYear=2015&puYear=2015&filterTransmission=Manual";
//		String s2 = "https://cars.cartrawler.com/norwegian/en/book?clientID=242447&elID=0726201134239873913&countryID=LT&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&__utma=66135985.2092701990.1437977508.1437977508.1437977508.1&__utmb=66135985.3.10.1437977508&__utmc=66135985&__utmx=-&__utmz=66135985.1437977508.1.1.utmcsr&__utmv=-&__utmk=218255774#/vehicles";
//		String s3 =	"http://cars-scanner.com/en/?_c=fnd.default&clientID=334782&elID=0807212554181994514&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&utm_source=google&utm_medium=poisk&utm_content=price_compare&utm_campaign=global_words&_c=fnd.default&gclid=CJTS9ojnl8cCFfHJtAodF8EANg#book";
//
//		
//		PdfBuilder pdf = new PdfBuilder(s1);
//		System.out.println(pdf.getSource());
//		
//		PdfBuilder pdf1 = new PdfBuilder(s2);
//		System.out.println(pdf1.getSource());
//		
//		PdfBuilder pdf2 = new PdfBuilder(s3);
//		System.out.println(pdf2.getSource());
//		SourceReader.getCarScanner();
		
//		try {
//			SourceReader.getScannerSilenium();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
			
		long endTime = System.currentTimeMillis();
		System.out.println("-->Main done" + "\n" + (endTime - startTime) / 1000);
	}
}
