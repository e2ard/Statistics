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
		for (int index = 0; index < 1; index++){
			//create pdf with site name
//			System.out.println("-->genABalticRCars " + sites.sites.get(index));
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 17, 20, 25, 26};
			//
//			sites.setDate(2);
			sites.setSiteName(sites.sites.get(index));
			
			System.out.println(sites.sites.size());
			for(int i = 1; i < days.length - 12; i++){//d
				sites.setDate(Main.year, Main.month, days[i]);
				sites.setCategories();

				
				
				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie increased date
				ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
				HashMap<String, ArrayList<Offer>> map;
				switch (index) {
					case 0 :
						//airbaltic reltal cars
						for (String site : sNames){//for each category get min offer
							Offer o = sr.getMinOffer(sr.getTags(site));// 
							offers.add(o);//save offer to list
//							System.out.println("Offer site:" + "/n" + o.getSite() + '\n');
						}
						break;
					case 1 :
						System.out.println("sNorwegian source: " + sites.getSiteName());
						map = SourceReader.getNorwOffers(sites.getSiteName());
						
						for (String str : Sites.sNorwegian) {
							ArrayList<Offer> offerList = map.get(str);
							offers.add(sr.getMinOffer(offerList));
//							System.out.println("source: " + sites.setDate(days[i+1]));
						}
						break;
					case 2 :
						System.out.println("sScanner source: " + sites.getSiteName());
						map = SourceReader.getScannerSilenium(sites.getSiteName());
						
						for (String str : Sites.sScanner) {
							ArrayList<Offer> offerList = map.get(str);
							offers.add(sr.getMinOffer(offerList));
//							System.out.println("source: " + sites.setDate(days[i+1]));
						}
						break;
					default :
						System.out.println("Something went wrong");
				}		
				
//				if(index < 1){	
//					for (String site : sNames){//for each category get min offer
//						Offer o = sr.getMinOffer(sr.getTags(site));// 
//						offers.add(o);//save offer to list
////						System.out.println("Offer site:" + "/n" + o.getSite() + '\n');
//					}
//				}else{// Norvegian
//					System.out.println("source: " + sites.getSiteName());
//					HashMap<String, ArrayList<Offer>> map = SourceReader.getNorwOffers(sites.getSiteName());
//					
//					for (String str : Sites.sNorwegian) {
//						ArrayList<Offer> offer = map.get(str);
//						offers.add(sr.getMinOffer(offer));
////						System.out.println("source: " + sites.setDate(days[i+1]));
//					}
////					pdf.addOffer(ofs, 1);
//				}
				//--
				pdf.addOffersRow(offers, days[i]);
				sites.resetSiteName();
			}
			
			pdf.finishGenerating();
			pdf.saveDocument();
			sites.setSiteName(sites.sites.get(index));
		}
	}
	
}
