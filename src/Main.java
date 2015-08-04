import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.itextpdf.text.DocumentException;

public class Main {
	
	public static void main(String[] args) throws IOException, DocumentException {
		long startTime = System.currentTimeMillis();
		System.out.println("-->Main started");
					
		StatisticGenerator.genABalticRCars();
//		StatisticGenerator.getNorwegian();
		
//			System.out.println("MainKey : " + entry.getKey() + " Value : "
//				+ entry.getValue().toString());
//			
		
		
//		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();
//		int num = 1;
//		for(String s : Sites.sClasses){
//			if(map.get(s) == null){
//				map.put(s, new ArrayList<Offer>());
//			}
//			for(int index = 0; index < num; index++){
//				int i = (int)Math.random();
//				Offer o = new Offer(i, "GM");
//				map.get(s).add(o);
//			}
//			num++;
//		}
//		for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : "
//				+ entry.getValue().toString());
//		}

		String site =
				"https://cars.cartrawler.com/norwegian/en/book?clientID=242447&elID=0726201134239873913&countryID=LT&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&__utma=66135985.2092701990.1437977508.1437977508.1437977508.1&__utmb=66135985.3.10.1437977508&__utmc=66135985&__utmx=-&__utmz=66135985.1437977508.1.1.utmcsr&__utmv=-&__utmk=218255774#/vehicles";
//		int day = 19;
//		String doDay = Integer.toString(day);
//		if(day <= 9){
//			doDay = "0" + day;
//		}
//		System.out.println(doDay);
//		site = site.replace("returnDateTime=2015-09-02", "returnDateTime=2015-09-" + doDay);
		System.out.println(site);
		long endTime = System.currentTimeMillis();
		System.out.println("-->Main done" + "\n" + (endTime - startTime) / 1000);
		
	}
}
