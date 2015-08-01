import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class Main {
	
	public static void main(String[] args) throws IOException, DocumentException {
		long startTime = System.currentTimeMillis();
		System.out.println("-->Main started");
		
					
//		StatisticGenerator.genABalticRCars();
		SourceReader.getNorwOffers();
		
//		String str = ""
//				+"http://www.rentalcars.com/SearchResults.do;jsessionid=25DF0A5992ED8B04C0F46485CFC9E9C3.node315a?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&doHour=10&filterName=CarCategorisationSupplierFilter&locationName=Vilnius+Oro+Uostas&searchType=&doFiltering=false&doMonthYear=8-2015&puSameAsDo=on&city=Vilnius&puMonthYear=8-2015&chinese-license=on&tj_pe_exp=t%3D1437064610703.e%3D22651-B%40hash%401437064867918%2C22656-B%40hash%401437064867917%2C22358-A%40hash%401437408365248%2C22363-B%40hash%401437408365248&puHour=10&dropCountry=Lietuva&emptySearchResults=false&puDay=1&filterTo=1000&dropLocation=388026&doDay=2&dropLocationName=Vilnius+Oro+Uostas&enabler=&country=Lietuva&filterFrom=0&puMonth=8&puMinute=0&doMonth=8&doYear=2015&puYear=2015&filter_carclass=economy&filterAdditionalInfo=&advSearch=&exSuppliers=&ordering=recommended&filterTransmission=Manual";
//;
		long endTime = System.currentTimeMillis();
		System.out.println("-->Main done" + "\n" + (endTime - startTime) / 1000);
		
	}
}
