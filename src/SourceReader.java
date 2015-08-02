import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.NoHttpResponseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SourceReader {
	
	public ArrayList<Offer> getTags(String siteName) throws IOException{
		Document doc = null;
        try {
        	doc = Jsoup.connect(siteName).timeout(0).get();
         } catch (IOException e) {
            System.out.println("-->Something went wrong" + "\n" + siteName + ".");
            e.printStackTrace();
            return null;
        }
		ArrayList<Offer> offers = new ArrayList<Offer>();
		
		//get prices and suppliers
//		Elements prices = doc.select("div.car-result.group div.car-result-r div.price div.dis div.dis-inner p.now");
		Elements prices = doc.select("div.dis-inner > p.now");
		if(prices.size() == 0){
			prices = doc.select("div.saving-included > p.now");
		}

		Elements suppliers = doc.select("div.supplier_id img[title]");
		System.out.println("--> Prices num:" + prices.size() + " " +  suppliers.size());
		
//		System.out.println("--> Suppliers num:" + suppliers.size());
		String[] price = null;
		
		if(prices.size() == suppliers.size() && prices.size() != 0 || (prices.size() == suppliers.size()*2)){
//			System.out.println("-->OK, PRICES EQUAL SUPPLIER NUM");
			for (int i = 0; i < suppliers.size(); i++){
				
				price = prices.get(i).text().replace(',', '.').split(" ");
				float ownPrice = Float.parseFloat(price[0]);
				
				String supplier = suppliers.get(i).attr("title");
				Offer offer = new Offer(ownPrice, supplier);
				offers.add(offer);
			}
			if((prices.size() == suppliers.size()*2)&& prices.size() != 0){
				PrintWriter writer = new PrintWriter("output.html", "UTF-8");
				writer.println(doc.body());
				writer.close();
				System.out.println("---->CHECK OUTPUT");
			}
			suppliers = null;
			prices = null;
			
		}else{
			System.out.println("-->getTags DATA NOT FOUND");
		}
		return offers;
	}
	
	public Offer getMinOffer(String siteName) throws IOException{
		
		ArrayList<Offer> carResults = getTags(siteName);
		float minPrice = 999999;
		String minSupplier = null;
		float minGMPrice = 9999999;
		String minGMSupplier = null;
		if(carResults != null){
//			carResults = carResults.subList(0, carResults.size()-1);
			for(Offer e:carResults){
	
				//find min not green Motion
				if(!e.getSupplier().equals("Green Motion")&&(e.getPrice() < minPrice)){
					minPrice = e.getPrice();
					minSupplier = e.getSupplier();
					continue;
				}else 
				//find min green Motion
				if(e.getSupplier().equals("Green Motion")&&(e.getPrice() < minGMPrice)){
					System.out.println("GM found price is less" + e.getSupplier() + e.getPrice());
					minGMPrice = e.getPrice();
					minGMSupplier = e.getSupplier();
				}
			}
			Offer offer = new Offer();
			offer.setSite(siteName);
			
	//			System.out.println("-->getTags" + offer.getSite());
			
			offer.setPrice(minPrice);
			offer.setSupplier(minSupplier);
	//			System.out.println("-->gmo" + minPrice + minSupplier);
			
			offer.setGMPrice(minGMPrice);
			offer.setGMSupplier(minGMSupplier);
	//			System.out.println("-->gmo size:" + carResults.size() + minGMPrice + minGMSupplier);
			return offer;
			
		}else{
			System.out.println("-->NO RESULTS FOUND");
		}
		return null;
	}
	
public Offer getMinOffer(ArrayList<Offer> carResults, String siteName) throws IOException{
		
		float minPrice = 999999;
		String minSupplier = null;
		float minGMPrice = 9999999;
		String minGMSupplier = null;
		if(carResults != null){
			for(Offer e : carResults){
	
				//find min not green motion
				if(!e.getSupplier().toLowerCase().equals("green motion")&&(e.getPrice() < minPrice)){
					minPrice = e.getPrice();
					minSupplier = e.getSupplier();
					continue;
				}else 
				//find min green Motion
				if(e.getSupplier().toLowerCase().equals("green motion")&&(e.getPrice() < minGMPrice)){
					System.out.println("GM found price is less" + e.getSupplier() + e.getPrice());
					minGMPrice = e.getPrice();
					minGMSupplier = e.getSupplier();
				}
			}
			Offer offer = new Offer();
			offer.setSite(siteName);
			
	//			System.out.println("-->getTags" + offer.getSite());
			
			offer.setPrice(minPrice);
			offer.setSupplier(minSupplier);
	//			System.out.println("-->gmo" + minPrice + minSupplier);
			
			offer.setGMPrice(minGMPrice);
			offer.setGMSupplier(minGMSupplier);
	//			System.out.println("-->gmo size:" + carResults.size() + minGMPrice + minGMSupplier);
			return offer;
			
		}else{
			System.out.println("-->NO RESULTS FOUND");
		}
		return null;
	}
	public static HashMap<String, ArrayList<Offer>> getNorwOffers(){
		org.jsoup.nodes.Document doc = null;
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();
		
		String site =
				"https://cars.cartrawler.com/norwegian/en/book?clientID=242447&elID=0726201134239873913&countryID=LT&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&__utma=66135985.2092701990.1437977508.1437977508.1437977508.1&__utmb=66135985.3.10.1437977508&__utmc=66135985&__utmx=-&__utmz=66135985.1437977508.1.1.utmcsr&__utmv=-&__utmk=218255774#/vehicles";
		try {
//			System.out.println(site);

//			String strURL = "https://www.checkmytrip.com" ;
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			try {
				WebClient webClient = new WebClient(BrowserVersion.CHROME);
			    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			    webClient.waitForBackgroundJavaScript(10 * 1000);
			    HtmlPage myPage =  webClient.getPage(site);
			    String theContent = myPage.asText();
			    System.out.println(theContent);
			    doc = Jsoup.parse(theContent);
				Elements elems = doc.select("h2");
//				webClient.close();
				int tries = 0;
				while(elems.size() == 0){
					webClient.waitForBackgroundJavaScript(1000);
				    myPage = webClient.getPage(site);
				    
				    theContent = myPage.asXml();
				    doc = Jsoup.parse(theContent);
				    System.out.println(theContent);
					elems = doc.select("div.ct-grid.ct-relative");
					System.out.println(elems.size());
					if(elems.size() > 0){
						System.out.println(elems.size());
						for(Element e : elems){
							String category = e.getElementsByClass("ct-no-margin-bottom").last().text();//get category
//							System.out.println(category);
							String transmission = e.getElementsByClass("ct-grid").text();
							System.out.println(transmission);
							System.out.println(transmission.contains("Manual"));
							
							String catTrans = null;
							if(transmission.contains("Manual")){
								catTrans = category.toLowerCase() + "m";
								System.out.println("transmission works m: " + catTrans);
							}else{
								catTrans = category.toLowerCase() + "a";
								System.out.println("transmission works a: " + catTrans);
							}
						
							Element temp = e.getElementsByClass("ct-padding-top").first();//get supplier 	
							String supplier = temp.getElementsByTag("img").attr("alt").toString();
							String priceStr = e.getElementsByTag("h2").first().text();
							String[] price = priceStr.replace(",","").split(" ");
							try{
								float ownPrice = Float.parseFloat(price[1]);// get price
								Offer o = new Offer(ownPrice, supplier);
								System.out.print(o.toString());
								System.out.println(supplier + " " + ownPrice);
								for(String s : Sites.sNorwegian){
									// if null initialize
									if(map.get(s) == null){
										map.put(s, new ArrayList<Offer>());
									}
									
									if(catTrans.toLowerCase().equals(s.toLowerCase())){
										System.out.println("transmission works");
										map.get(s).add(o);
									}else{
										
									}
								}
							}catch(NumberFormatException nfe){
								System.out.println("-->Something went wrong1" + nfe.getMessage());
							}
						}
					}
				}
			}catch (NoHttpResponseException ne){
				System.out.println("-->Something went wrong1" + ne.getMessage());
			}
			
		} catch (IOException e) {
			System.out.println("-->Something went wrong" + e.getMessage()); 
			e.printStackTrace();
	    }
		for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
				+ entry.getValue().toString());
		}
		return map;
	}
}
