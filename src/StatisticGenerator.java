import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.text.DocumentException;


public class StatisticGenerator {
	
	public static void genABalticRCars() throws DocumentException, IOException{
		
		//load site from file sites.txt
		Sites sites = new Sites();
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		for (int index = 2; index < 3; index++){
			//create pdf with site name
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 17, 20, 21};
			//
			sites.setDate(2);
			sites.setSiteName(sites.sites.get(index));
			
			System.out.println(sites.sites.size());
			for(int i = 0; i < days.length - 7; i++){
				sites.setDate(days[i+1]);
				sites.setCategories();
				sites.resetSiteName();

				//airbaltic reltal cars
				
				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie according to date
				ArrayList<Offer> offers = new ArrayList<Offer>();//ini array where all offers will be
				if(index < 2){	
					for (String site : sNames){//for each category get get min offer
						Offer o = sr.getMinOffer(site);
						offers.add(o);//save offer to list
						System.out.println("Offer site:" + "/n" + o.getSite() + '\n');
					}
				}else{
					System.out.println("source: " + sites.setDate(days[i+1]));
					HashMap<String, ArrayList<Offer>> map = SourceReader.getNorwOffers(sites.setDate(days[i+1]));
					
					for (String str : Sites.sNorwegian) {
						ArrayList<Offer> offer = map.get(str);
						offers.add(sr.getMinOffer(offer, sites.sites.get(index)));
						System.out.println("source: " + sites.setDate(days[i+1]));
					}
//					pdf.addOffer(ofs, 1);
				}
				//--
				pdf.addOffer(offers, days[i]);
			}
			
			pdf.finishGenerating();
			pdf.saveDocument();
			sites.setSiteName(sites.sites.get(index));
		}
	}
	public static void getNorwegian( ) throws IOException, DocumentException{
		SourceReader sr = new SourceReader();
		String source = 
				"https://cars.cartrawler.com/norwegian/en/book?clientID=242447&elID=0726201134239873913&countryID=LT&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&__utma=66135985.2092701990.1437977508.1437977508.1437977508.1&__utmb=66135985.3.10.1437977508&__utmc=66135985&__utmx=-&__utmz=66135985.1437977508.1.1.utmcsr&__utmv=-&__utmk=218255774#/vehicles";
		PdfBuilder pdf = new PdfBuilder(source);
		
		HashMap<String, ArrayList<Offer>> map = SourceReader.getNorwOffers(source);
		
		ArrayList<Offer> ofs = new ArrayList<Offer>();
		for (String str : Sites.sNorwegian) {
			ArrayList<Offer> offer = map.get(str);
			ofs.add(sr.getMinOffer(offer, source));
//			System.out.println("MainKey : " + entry.getKey() + " Value : "
		}
		pdf.addOffer(ofs, 1);
		
		pdf.finishGenerating();
		pdf.saveDocument();
		
	}
}
