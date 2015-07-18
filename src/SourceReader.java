import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SourceReader {

	private String siteName = "http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&location=-1&driversAge=25&doHour=10&filterName=CarCategorisationSupplierFilter&locationName=Vilnius+%28Visi+rajonai%29+&searchType=allareasgeosearch&doFiltering=true&puSameAsDo=on&city=Vilnius&puHour=10&dropCountryCode=&dropCountry=Lietuva&puDay=19&filterTo=49&dropLocation=-1&driverage=on&doDay=22&countryCode=&dropLocationName=Vilnius+%28Visi+rajonai%29+&country=Lietuva&enabler=&filterFrom=0&puMonth=7&puMinute=0&doMonth=7&doYear=2015&puYear=2015&fromLocChoose=true&filterCoordinates=54.6333%2c25.2833";
	
	public ArrayList<Offer> getTags(String className, String tag) throws IOException{
		
		Sites site = new Sites();
		System.out.println(site.getSiteName());
		
		Document doc = null;
        try {
            doc = Jsoup.connect(siteName).get();
         } catch (IOException e) {
            e.printStackTrace();
        }
		ArrayList<Offer> offers = new ArrayList<Offer>();

		//get prices and suppliers
		Elements prices = doc.select("p.now");
		Elements suppliers = doc.select("img[title]");
		String[] price = null;
		
		if(prices.size() == suppliers.size() && prices.size() != 0){
			System.out.println("-->OK, PRICES EQUAL SUPPLIER NUM");
			for (int i = 0; i < prices.size(); i++){
				price = prices.get(i).text().replace(',', '.').split(" ");
				float ownPrice = Float.parseFloat(price[0]);
				String supplier = suppliers.get(i).attr("title");
				Offer offer = new Offer(ownPrice, supplier);
				offers.add(offer);
			}
		}else{
			System.out.println("-->getTags DATA NOT FOUND");
		}
		return offers;
	}
	

}
