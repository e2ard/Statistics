package Source;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import GUI.GuiDMain;
import GetInfoMultiThread.RentalThread;
import Logging.LogIn;
import Pricing.PricingManager;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.itextpdf.text.DocumentException;

public class Main {
//	public static int year = 2015;
//	public static int doMonth = 8;
//	public static int puMonth = 8;
//	public static int puDay = 30, doDay = 31;
//	public static int daysNum = 30;
//	public static int daysPerDoc = 5;
//	public static String cityName = " Vilnius";
	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		long startTime = System.currentTimeMillis();
		System.out.println("-->Main started");
		//init date
//		int nowDay = 29;
//		int nowMonth = 9;
//		int nowYear = 2015;
//		
		System.setProperty("https.proxyHost", "204.14.14.230");
		System.setProperty("https.proxyPort", "8080");
		
		JFrame window = new GuiDMain();
		
		
		
//		String rental = "http://www.rentalcars.com/SearchResults.do?dropCity=Ryga&doMinute=0&location=1373298&driversAge=25&exSuppliers=&doHour=10&filterName=CarCategorisationSupplierFilter&searchType=&locationName=Ryga+Oro+Uostas&doFiltering=&doMonthYear=8-2015&puSameAsDo=on&city=Ryga&puMonthYear=8-2015&chinese-license=on&tj_pe_exp=t%3D1440097272269.e%3D24031-B%40hash%401440230056249%2C24286-A%40hash%401440097276086&puHour=10&dropCountry=Latvija&puDay=03&filterTo=1000&dropLocation=1373298&doDay=04&dropLocationName=Ryga+Oro+Uostas&enabler=&country=Latvija&filter_carclass=economy&advSearch=&filterAdditionalInfo=&filterFrom=0&puMonth=10&puMinute=0&doMonth=10&doYear=2015&puYear=2015&filterTransmission=Automatic";
//		rental = "http://www.rentalcars.com/SearchResults.do;jsessionid=DE2574FFEFE06FF009AF4FAAA2368A0E.node226a?&dropCity=Vilnius&doMinute=0&cor=fr&location=14159&driversAge=25&exSuppliers=&doHour=10&locationName=Vilnius+Airport&city=Vilnius&page=SearchResults.do&puHour=10&preflang=en&dropCountry=Lithuania&affiliateCode=airbaltic&puDay=12&dropLocation=14159&doDay=2&dropLocationName=Vilnius+Airport&country=Lithuania&filter_carclass=economy&filterAdditionalInfo=&advSearch=&puMonth=9&puMinute=0&doMonth=9&doYear=2015&puYear=2015&filterTransmission=Manual";
//		
//		Calendar cal = Calendar.getIntance();
//		cal.setPuDay(nowDay);
//		cal.setPuMonth(nowMonth);
//		cal.setPuYear(nowYear);
//		
//		StatisticGenerator sg = new StatisticGenerator();
//		sg.getPdfFast(rental, 2);
//		
//		Set<String> letter = new HashSet<String>();
//		letter.add("aab");
//		letter.add("aa");
//		System.out.println(letter.size());
//		
//		
//		SourceReader sr = new SourceReader();
//		sr.connectionTest(rental);
//		sr.connectionTest1(rental);
//		//		ArrayList<Offer> offers = sr.getTags(rental);
//		for(Offer o : offers){
//			System.out.println(o.toString());
//			
//		}
		
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

			
		long endTime = System.currentTimeMillis();
		System.out.println("-->Main done" + "\n" + (endTime - startTime) / 1000);
	}
}
