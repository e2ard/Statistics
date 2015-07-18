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
	private String siteName = "http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&location=-1&driversAge=25&exSuppliers=&doHour=10&filterName=CarCategorisationSupplierFilter&locationName=Vilnius+%28All+areas%29+&searchType=allareasgeosearch&doFiltering=true&doMonthYear=7-2015&puSameAsDo=on&city=Vilnius&puMonthYear=7-2015&chinese-license=on&tj_pe_exp=t%3d1437064610703.e%3d22651-B%40hash%401437064867918%2c22656-B%40hash%401437064867917&puHour=10&dropCountry=Lithuania&puDay=18&filterTo=49&dropLocation=-1&doDay=19&dropLocationName=Vilnius+%28All+areas%29+&enabler=&country=Lithuania&filter_carclass=compact&advSearch=&filterAdditionalInfo=&filterFrom=0&puMonth=7&puMinute=0&doMonth=7&doYear=2015&puYear=2015&filterCoordinates=54.6333%2c25.2833";
	
	public String getUrlSource(String url) throws IOException {
        URL yahoo = new URL(url);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }
	
	public String getContent(String myUrl) throws IOException{
		URL url = new URL(myUrl);
		URLConnection conn = url.openConnection();
		
		BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
		
		return br.readLine().toString();
	}
	
	public ArrayList<Offer> getTags(String className, String tag) throws IOException{

		Document doc = Jsoup.connect(siteName).get();

		ArrayList<Offer> offers = new ArrayList<Offer>();

		//get prices and suppliers
		Elements prices = doc.select("p.now");
		Elements suppliers = doc.select("img[title]");
		String[] price = null;
		
		if(prices.size() == suppliers.size()){
			System.out.println("-->OK, PRICES EQUAL SUPPLIER NUM");
			for (int i = 0; i < prices.size(); i++){
				price = prices.get(i).text().replace(',', '.').split(" ");
				float ownPrice = Float.parseFloat(price[0]);
				String supplier = suppliers.get(i).attr("title");
				Offer offer = new Offer(ownPrice, supplier);
				offers.add(offer);
			}
		}
		
		
		return offers;

	}
	

}
