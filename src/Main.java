import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

public class Main {
	
	public static void main(String[] args) throws IOException, DocumentException {
		System.out.println("-->Main started");

		SourceReader sr = new SourceReader();
		Sites sites = new Sites();
		PdfBuilder pdf = new PdfBuilder(sites.getSiteName());
		int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 17, 20, 21};
		
		for(int i = 1; i < days.length - 1; i++){
			ArrayList<String> sNames = sites.getSiteNames();
			ArrayList<Offer> offers = new ArrayList<Offer>();
			
			for (String site : sNames){
				Offer o = sr.getMinOffer(site);
				offers.add(o);
				System.out.println("Offer site:" + o.getSite() + '\n');
			}
			pdf.addOffer(offers, days[i]);
			sites.setDate(days[i+1]);
			sites.setCategories();
			sites.resetSiteName();
		}

		pdf.finishGenerating();
		pdf.saveDocument();
//		String str = ""
//				+"http://www.rentalcars.com/SearchResults.do;jsessionid=25DF0A5992ED8B04C0F46485CFC9E9C3.node315a?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&doHour=10&filterName=CarCategorisationSupplierFilter&locationName=Vilnius+Oro+Uostas&searchType=&doFiltering=false&doMonthYear=8-2015&puSameAsDo=on&city=Vilnius&puMonthYear=8-2015&chinese-license=on&tj_pe_exp=t%3D1437064610703.e%3D22651-B%40hash%401437064867918%2C22656-B%40hash%401437064867917%2C22358-A%40hash%401437408365248%2C22363-B%40hash%401437408365248&puHour=10&dropCountry=Lietuva&emptySearchResults=false&puDay=1&filterTo=1000&dropLocation=388026&doDay=2&dropLocationName=Vilnius+Oro+Uostas&enabler=&country=Lietuva&filterFrom=0&puMonth=8&puMinute=0&doMonth=8&doYear=2015&puYear=2015&filter_carclass=economy&filterAdditionalInfo=&advSearch=&exSuppliers=&ordering=recommended&filterTransmission=Manual";
//;
//		org.jsoup.nodes.Document doc = Jsoup.connect(str).timeout(0).get();
//		
//		ArrayList<Element> elems = null;
//		System.out.println(doc.body());
//		System.out.println(doc.select("#header-main > div.container > div > a").get(0).attr("href").toString());
		
		System.out.println("-->Main done" + "\n");
	}
}
