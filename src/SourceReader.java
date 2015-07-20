import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

	private String siteName;
	public ArrayList<Offer> getTags(String siteName) throws IOException{
		
		Sites site = new Sites();

		Document doc = null;
        try {
        	doc = Jsoup.connect(siteName).timeout(0).get();
//        	doc = Jsoup.parse(downloadHtml(site.getSiteName()));
         } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-->Something went wrong");
            return null;
        }
		ArrayList<Offer> offers = new ArrayList<Offer>();
		
		//get prices and suppliers
		Elements prices = doc.select("p.now");
		System.out.println("--> Prices num:" + prices.size());
		Elements suppliers = doc.select("div.supplier_id img[title]");
		System.out.println("--> Prices num:" + suppliers.size());
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
	public void getMinOffer(String siteName) throws IOException{
		
		ArrayList<Offer> carResults = getTags(siteName);
		float minPrice = 999999;
		String minSupplier = null;
		float minGMPrice = 9999999;
		String minGMSupplier = null;
		
		if(carResults != null){
			for(Offer e:carResults){
	
				//find min supplier
				if(e.getPrice() < minPrice){
					minPrice = e.getPrice();
					minSupplier = e.getSupplier();
				}
				if((e.getPrice() < minGMPrice)&&(e.getSupplier().equals("Green Motion"))){
					minGMPrice = e.getPrice();
					minGMSupplier = e.getSupplier();
				}
			}
			Offer minOffer = new Offer(minPrice, minSupplier);
			System.out.println(minOffer.toString());
			
			Offer GMOffer = new Offer(minGMPrice, minGMSupplier);
			System.out.println(GMOffer.toString());
		}else{
			System.out.println("-->NO RESULTS FOUND");
		}
	}
	
	private String downloadHtml(String path) {
	    InputStream is = null;
	    try {
	        String result = "";
	        String line;

	        URL url = new URL(path);
	        is = url.openStream();  // throws an IOException
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        return result;
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	    return "";
	}
	

}
