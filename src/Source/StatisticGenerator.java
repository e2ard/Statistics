package Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import GetInfoMultiThread.AirBaltic;
import GetInfoMultiThread.RentalThread;

import com.itextpdf.text.DocumentException;


public class StatisticGenerator {
	static int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
	
	public static void genABalticRCars() throws DocumentException, IOException{
		
		//load site from file sites.txt
		Sites sites = new Sites();
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		for (int index = 1; index < 2; index++){
			//create pdf with site name
//			System.out.println("-->genABalticRCars " + sites.sites.get(index));
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			//
//			sites.setDate(2);
			sites.setSiteName(sites.sites.get(index));
			
			System.out.println(sites.sites.size());
			for(int i = 0; i < days.length - 1; i++){//d
				sites.initDate(Main.year, Main.month, Main.pickupDay);
				sites.setDate(Main.year, Main.month, days[i] + Main.pickupDay);
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
	
	
public static void getPdfFast() throws DocumentException, IOException{
		
		//load site from file sites.txt
		Sites sites = new Sites();
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		for (int index = 0; index < 1; index++){
			//create pdf with site name
//			System.out.println("-->genABalticRCars " + sites.sites.get(index));
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			
//			sites.setDate(2);
			sites.setSiteName(sites.sites.get(index));
			
			System.out.println(sites.sites.size());
			for(int i = 0; i < days.length - 1; i++){//d
				sites.initDate(Main.year, Main.month, Main.pickupDay);
				sites.setDate(Main.year, Main.month, days[i] + Main.pickupDay);
				sites.setCategories();

				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie increased date
				ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
				HashMap<String, ArrayList<Offer>> map;
				switch (index) {
					case 0 :
						//airbaltic reltal cars
						ExecutorService es = Executors.newCachedThreadPool();
						List<RentalThread> futures = new ArrayList<RentalThread>();
						for (String site : sNames){//for each category get min offer
							RentalThread downloadSite = new RentalThread(site);
							downloadSite.start();
							futures.add(downloadSite);
							es.execute(downloadSite);
						}
						es.shutdown();
						try {
							boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);
							if(finished){
								System.out.print("Done");
							}else{
								System.out.print("Shuted down");
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//					
						//retrieve filled arrays
						for (RentalThread f : futures){
				          offers.add(sr.getMinOffer(f.getArray()));
				          
						}	
						break;
					case 1 :
//						System.out.println("sNorwegian source: " + sites.getSiteName());
//						map = SourceReader.getNorwOffers(sites.getSiteName());
//						
//						for (String str : Sites.sNorwegian) {
//							ArrayList<Offer> offerList = map.get(str);
//							offers.add(sr.getMinOffer(offerList));
////							System.out.println("source: " + sites.setDate(days[i+1]));
//						}
//						//airbaltic reltal cars
//						ExecutorService es = Executors.newCachedThreadPool();
//						List<RentalThread> futures = new ArrayList<RentalThread>();
//						for (String site : sNames){//for each category get min offer
//							RentalThread downloadSite = new RentalThread(site);
//							downloadSite.start();
//							futures.add(downloadSite);
//							es.execute(downloadSite);
//						}
//						es.shutdown();
//						try {
//							boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);
//							if(finished){
//								System.out.print("Done");
//							}else{
//								System.out.print("Shuted down");
//							}
//							
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
////					
//						//retrieve filled arrays
//						for (RentalThread f : futures){
//				          offers.add(sr.getMinOffer(f.getArray()));
//				          
//						}
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
				
				pdf.addOffersRow(offers, days[i]);
				sites.resetSiteName();
			}
			
			pdf.finishGenerating();
			pdf.saveDocument();
			sites.setSiteName(sites.sites.get(index));
		}
	}

	public static void getPdfFastBeta() throws DocumentException, IOException{
		
		//load site from file sites.txt
		Sites sites = new Sites();
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		for (int index = 0; index < 1; index++){
			//create pdf with site name
	//		System.out.println("-->genABalticRCars " + sites.sites.get(index));
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			
	//		sites.setDate(2);
			sites.setSiteName(sites.sites.get(index));
			
			for(int i = 0; i < days.length - 1; i++){//d
				
				ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
				HashMap<String, ArrayList<Offer>> map = null;
				switch (index) {
					case 0 :
						//airbaltic reltal cars
						sites.initDate(Main.year, Main.month, Main.pickupDay);
						ExecutorService es = Executors.newCachedThreadPool();
						List<String> sNames = new ArrayList<String>();
						for(i = days[0]; i < days.length - 1; i++){
							sNames.add(sites.setDate(Main.year, Main.month, Main.pickupDay + i));
						}
						List<AirBaltic> futures = new ArrayList<AirBaltic>();
						for (String site : sNames){//for each category get min offer
							System.out.println(site);
							AirBaltic downloadSite = new AirBaltic(site);
							downloadSite.start();
							futures.add(downloadSite);
							es.execute(downloadSite);
						}
						es.shutdown();
						try {
							boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);
							if(finished){
								//retrieve filled arrays
								for (AirBaltic f : futures){
									for (String str : Sites.sAirbaltic) {
										ArrayList<Offer> offerList = f.getMap().get(str);
										offers.add(sr.getMinOffer(offerList));
										System.out.println(f.getMap().size());
										System.out.println("-->OFFERSIZEADDED" + offerList.size());
									}
									pdf.addOffersRow(offers, days[i]);
								}
								System.out.print("Done");
							}else{
								System.out.print("Shuted down");
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//					
						
				        break;
					
					default :
						System.out.println("Something went wrong");
				}		
				
				
				sites.resetSiteName();
			}
			
			pdf.finishGenerating();
			pdf.saveDocument();
			sites.setSiteName(sites.sites.get(index));
		}
	}
	
	
}
