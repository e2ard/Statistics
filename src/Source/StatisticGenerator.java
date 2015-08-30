package Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import GetInfoMultiThread.AirBaltic;
import GetInfoMultiThread.RentalThread;

import com.itextpdf.text.DocumentException;



public class StatisticGenerator {
	
//	public static void genABalticRCars() throws DocumentException, IOException{
//		
//		//load site from file sites.txt
//		Sites sites = new Sites();
//		//create source reader for reading site source
//		SourceReader sr = new SourceReader();
//		for (int index = 1; index < 2; index++){
//			//create pdf with site name
////			System.out.println("-->genABalticRCars " + sites.sites.get(index));
//			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
//			//ini days to count for
//			//
////			sites.setDate(2);
//			sites.setSiteName(sites.sites.get(index));
//			
//			System.out.println(sites.sites.size());
//			for(int i = 0; i < Sites.days.length - 1; i++){//d
//				sites.initDate(Main.year, Main.puMonth, Main.puDay);
//				sites.addDays(Sites.days[i]);
//				sites.setCategories();
//
//				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie increased date
//				ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
//				HashMap<String, ArrayList<Offer>> map;
//				switch (index) {
//					case 0 :
//						//airbaltic reltal cars
//						for (String site : sNames){//for each category get min offer
//							Offer o = sr.getMinOffer(SourceReader.getTags(site));// 
//							offers.add(o);//save offer to list
////							System.out.println("Offer site:" + "/n" + o.getSite() + '\n');
//						}
//						break;
//					case 1 :
//						System.out.println("sNorwegian source: " + sites.getSiteName());
//						map = SourceReader.getNorwOffers(sites.getSiteName());
//						
//						for (String str : Sites.sNorwegian) {
//							ArrayList<Offer> offerList = map.get(str);
//							offers.add(sr.getMinOffer(offerList));
////							System.out.println("source: " + sites.setDate(days[i+1]));
//						}
//						break;
//					case 2 :
//						System.out.println("sScanner source: " + sites.getSiteName());
//						map = SourceReader.getScannerSilenium(sites.getSiteName());
//						
//						for (String str : Sites.sScanner) {
//							ArrayList<Offer> offerList = map.get(str);
//							offers.add(sr.getMinOffer(offerList));
////							System.out.println("source: " + sites.setDate(days[i+1]));
//						}
//						break;
//					default :
//						System.out.println("Something went wrong");
//				}		
//				pdf.addOffersRow(offers, Sites.days[i]);
//				sites.resetSiteName();
//			}
//			
//			pdf.finishGenerating();
//			pdf.saveDocument();
//			sites.setSiteName(sites.sites.get(index));
//		}
//	}
//	
	
public void getPdfFast(String siteName, int docNum){
		/*from sites file, replaces economy to other categories
		 * 
		 */
		
		Sites sites = new Sites();//load site from file sites.txt
		
		SourceReader sr = new SourceReader(); //create source reader for reading site source
		
		Calendar cal = Calendar.getIntance();
		
		boolean suppliersAdded = false;
		for (int docIndex = 0; docIndex < docNum; docIndex++){
			sites.setSiteName(siteName);
			
			sites.initDate(cal.getPuYear(), cal.getPuMonth(), cal.getPuDay());
			PdfBuilder pdf = new PdfBuilder(sites.getSiteName());
			
			
			
			for(int i = 0; i < 19; i++){
				cal.incDoDay();
				sites.newDate(cal.getDoYear(), cal.getDoMonth(), cal.getDoDay());
				sites.setCategories();
	
				
				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie increased date
				ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
				
				ExecutorService es = Executors.newCachedThreadPool();
				List<RentalThread> futures = new ArrayList<RentalThread>();
				for (String site : sNames){						//for each category get min offer
//					System.out.println(site);
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
					e.printStackTrace();
				}
				
				//retrieve filled arrays
				for (RentalThread f : futures){
		          try {
		        	  Offer o = sr.getMinOffer(f.getArray());
		        	  o.setSite(f.getSiteName());
					offers.add(o);
		          } catch (IOException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
		          }
		        }	
				pdf.addOffersRow(offers, Sites.days[i]);
				
			}
			cal.incPuDay();
			cal.reset();
			if(!suppliersAdded){
				pdf.setSuppliers(sr.getSupliers(sites.getSiteName()));
			}
			pdf.finishGenerating();
			pdf.saveDocument();
		}
		JOptionPane.showMessageDialog(null, "Baigta");
		
	}
	
	public static void AirBalticBeta(String siteName) throws DocumentException, IOException{
		/* works fast but there are some  mistypes, example categories, suvs as standart. 
		 * drawbag: removes last element!
		 * 
		 * http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&doHour=10&locationName
		 * =Vilnius+Airport&searchType=&doFiltering=&doMonthYear=8-2015&puSameAsDo=on&city=Vilnius&puMonthYear=8-2015&chinese-license=
		 * on&tj_pe_exp=t%3d1440097272269.e%3d24286-A%40hash%401440097276086&puHour=10&dropCountry=Lithuania&puDay=23&filterTo=1000&dro
		 * pLocation=388026&doDay=24&dropLocationName=Vilnius+Airport&enabler=&country=Lithuania&filterFrom=0&puMonth=8&puMinute=0&doMo
		 * nth=8&doYear=2015&puYear=2015&showAllCars=true
		 * 
		 * */
		siteName = "http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&location=388026&driversAge=25&doHour=10&locationName=Vilnius+Airport&searchType=&doFiltering=&doMonthYear=8-2015&puSameAsDo=on&city=Vilnius&puMonthYear=8-2015&chinese-license=on&tj_pe_exp=t%3d1440097272269.e%3d24286-A%40hash%401440097276086&puHour=10&dropCountry=Lithuania&puDay=23&filterTo=1000&dropLocation=388026&doDay=24&dropLocationName=Vilnius+Airport&enabler=&country=Lithuania&filterFrom=0&puMonth=8&puMinute=0&doMonth=8&doYear=2015&puYear=2015&showAllCars=true";
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		//create pdf with site name
		PdfBuilder pdf = new PdfBuilder(siteName);

		//init days to count for
		Sites sites = new Sites();
		sites.setSiteName(siteName);
		
		Calendar cal = Calendar.getIntance();
		
		//airbaltic reltal cars
		sites.initDate(cal.getPuYear(), cal.getPuMonth(), cal.getPuDay());
		ExecutorService es = Executors.newCachedThreadPool();
		List<String> sNames = new ArrayList<String>();
		
		for(int i = 1; i < 29; i++){
			sNames.add(sites.newDate(cal.getDoYear(), cal.getDoMonth(), cal.getDoDay()));
		}
		
		System.out.println("sNames is " + sNames.size());
		List<AirBaltic> futures = new ArrayList<AirBaltic>();
		for (String site : sNames){//for each category get min offer
//							System.out.println(site);
			AirBaltic downloadSite = new AirBaltic(site);
//							downloadSite.start();
			futures.add(downloadSite);
			es.execute(downloadSite);
		}
		es.shutdown();
		try {
			boolean finished = es.awaitTermination(2, TimeUnit.MINUTES);
			if(finished){
				//retrieve filled arrays
				int tempDay = 1;
				for (int offrNum = 0; offrNum < sNames.size(); offrNum++){
					AirBaltic f = futures.get(offrNum);
					ArrayList<Offer> offers = new ArrayList<Offer>();//init array where all offers will be
					
//									HashMap<String, ArrayList<Offer>> map = sr.getAirbaltic(sNames.get(offrNum));
					
					HashMap<String, ArrayList<Offer>> map = f.getMap();
					for (String str : Sites.sAirbalticLt) {
						ArrayList<Offer> offerList = map.get(str);
						offers.add(sr.getMinOffer(offerList));
						pdf.addOffersRow(offers, tempDay++);
						System.out.print("Added to pdf");
					}
				}
				System.out.print("Done");
			}else{
				System.out.print("Shuted down");
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdf.finishGenerating();
		pdf.saveDocument();
	}
};
