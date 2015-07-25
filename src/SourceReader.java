import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SourceReader {
	
	public ArrayList<Offer> getTags(String siteName) throws IOException{
		Document doc = null;
        try {
        	doc = Jsoup.connect(siteName).timeout(0).get();
         } catch (IOException e) {
            e.printStackTrace();
            System.out.println("-->Something went wrong");
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
	
}
