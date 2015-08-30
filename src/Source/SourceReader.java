package Source;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.apache.http.NoHttpResponseException;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SourceReader {
	WebDriver driver;
	
	public static ArrayList<Offer> getTags(String siteName) {
		Document doc = null;
		Response res = null;
        try {
        	res = Jsoup
    			.connect(siteName)
			    .timeout(0)
			    .method(Method.POST)
			    .execute();
			
		Map<String, String> cookies = res.cookies();

		doc = Jsoup.connect(siteName).cookies(cookies).timeout(0).get();

         } catch (IOException e) {
            System.out.println("--> GETTAGS Not connected to:" + "\n" + siteName + ".");
            e.printStackTrace();
            return null;
        }
		ArrayList<Offer> offers = new ArrayList<Offer>();
		
		//get prices and suppliers
		Elements prices = doc.select("div.dis-inner > p.now");
		if(prices.size() == 0){
			prices = doc.select("div.saving-included > p.now");
		}

		Elements suppliers = doc.select("div.supplier_id img[title]");
//		System.out.println("--> Prices num:" + prices.size() + " " +  suppliers.size());
		
//		System.out.println("--> Suppliers num:" + suppliers.size());
		String[] price = null;
		
		if(prices.size() == suppliers.size() && prices.size() != 0){
			System.out.println("-->SUPPLIERS SIZE" + suppliers.size());
			for (int i = 0; i < suppliers.size(); i++){
				
				price = prices.get(i).text().replace(',', '.').split(" ");
				float ownPrice = Float.parseFloat(price[0]);
				
				String supplier = suppliers.get(i).attr("title");
				Offer offer = new Offer(ownPrice, supplier);
				
				offer.setSite(siteName);
				if(siteName == null){
					System.out.println("------------------------------------------------------------------------------------NULL");
				}
				offers.add(offer);
			}
			if((prices.size() == suppliers.size()*2)&& prices.size() != 0){
				PrintWriter writer;
				try {
					writer = new PrintWriter("output.html", "UTF-8");
					writer.println(doc.body());
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("---->CHECK OUTPUT");
			}
			suppliers = null;
			prices = null;
			
		}else{
			System.out.println("-->getTags DATA NOT FOUND" + "\n" + siteName);
		}
		return offers;
	}
	

	public Offer getMinOffer(ArrayList<Offer> carResults) throws IOException{
		
		float minPrice = 999999;
		String minSupplier = null;
		float minGMPrice = 9999999;
		String minGMSupplier = null;
		Offer offer = new Offer();
		String site = null;
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
//					System.out.println("GM found price is less" + e.getSupplier() + e.getPrice());
					minGMPrice = e.getPrice();
					minGMSupplier = "GM ";
				}
			}
			
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
	public static HashMap<String, ArrayList<Offer>> getNorwOffers(String site){
		org.jsoup.nodes.Document doc = null;
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();// all offers
		
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			try {
				WebClient webClient = new WebClient(BrowserVersion.CHROME);
			    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			    webClient.waitForBackgroundJavaScript(3 * 1000);
			    HtmlPage myPage = null; 
//			    		webClient.getPage(site);
			    String theContent = null;
////			    System.out.println(theContent);
//			    doc = Jsoup.parse(theContent);
				Elements elems = new Elements();
				while(elems.size() == 0){
					webClient.waitForBackgroundJavaScript(1000);
				    myPage = webClient.getPage(site);
				    
				    theContent = myPage.asXml();
				    doc = Jsoup.parse(theContent);
//				    System.out.println(theContent);
					elems = doc.select("div.ct-grid.ct-relative");
//					System.out.println(elems.size());
					//--
					@SuppressWarnings("unchecked")
					ArrayList<HtmlElement> dmList= 
							(ArrayList<HtmlElement>) myPage.getByXPath("/html/body/div[2]/div/section/div/div/div/div[2]/div[2]/div[3]/div/div[1]/div/ul");

//				    System.out.println(dmList.size());
				    if(dmList.size() > 0){
				    	for(HtmlElement he : dmList.get(0).getElementsByTagName("li")){
				    		myPage = he.click();//click specified category 
				    		Document tempDoc = Jsoup.parse(myPage.asXml());
				    		Elements tempElems = tempDoc.select("div.ct-grid.ct-relative");
//				    		System.out.println(tempElems.size());
				    		for(Element e : tempElems){
				    			String category = e.getElementsByClass("ct-no-margin-bottom").last().text();//get category
//								System.out.println(category);
								String transmission = e.getElementsByClass("ct-grid").text();
//								System.out.println(transmission);
//								System.out.println(transmission.contains("Manual"));
								
								String catTrans = null;
								if(transmission.contains("Manual")){
									catTrans = category.toLowerCase() + "m";
//									System.out.println("transmission works m: " + catTrans);
								}else{
									catTrans = category.toLowerCase() + "a";
//									System.out.println("transmission works a: " + catTrans);
								}
							
								Element temp = e.getElementsByClass("ct-padding-top").first();//get supplier 	
								String supplier = temp.getElementsByTag("img").attr("alt").toString();
								String priceStr = e.getElementsByTag("h2").first().text();
								String[] price = priceStr.replace(",","").split(" ");
								try{
									float ownPrice = Float.parseFloat(price[1]);// get price
									Offer o = new Offer(ownPrice, supplier);
//									System.out.print(o.toString());
//									System.out.println(supplier + " " + ownPrice);
									o.setSite(site);
									for(String s : Sites.sNorwegian){
										// if null initialize
										if(map.get(s) == null){
											map.put(s, new ArrayList<Offer>());
										}
										
										if(catTrans.toLowerCase().equals(s.toLowerCase())){
//											System.out.println(o.toString() + " added.");
											map.get(s).add(o);
										}else{
											
										}
									}
								}catch(NumberFormatException nfe){
									System.out.println("-->Ilegal number format" + nfe.getMessage());
								}
							}
				    	}
					}
				}
				webClient.close();
			}catch (NoHttpResponseException ne){
				System.out.println("-->Not http response" + ne.getMessage());
			}
			
		} catch (IOException e) {
			System.out.println("-->GETNORWOFFERS Something went wrong" + e.getMessage()); 
			e.printStackTrace();
	    }
//		for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : "
//				+ entry.getValue().toString());
//		}
		return map;
	}
	
	public static HashMap<String, ArrayList<Offer>> getScannerSilenium(String site){
//		String site = ""
//				+ "http://cars-scanner.com/en/?_c=fnd.default&clientID=334782&elID=0807212554181994514&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&utm_source=google&utm_medium=poisk&utm_content=price_compare&utm_campaign=global_words&_c=fnd.default&gclid=CJTS9ojnl8cCFfHJtAodF8EANg#book";
//		String showMoreStr = "/html/body/table/tbody/tr[1]/td/div[7]/div/div/div[1]/div[2]/div[2]/div/div/section/div[2]/div/div[2]/div[26]";
	    
		DesiredCapabilities caps = new DesiredCapabilities();
	    caps.setJavascriptEnabled(true); 
//	    caps.chrome();
//	    String phantombinary = "C:\\bin\\phantomjs\\bin";
//	    caps.setCapability(
//	        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//	        phantombinary//"/home/p/phantomjs-1.9.2-linux-x86_64/bin/phantomjs"
//	    );
		WebDriver driver = new PhantomJSDriver(caps);
        boolean connecting = true;

        int delayedTime = 2000;
        while(connecting){
            try{
                WebDriver driver1 = new PhantomJSDriver(caps);
                WebDriverWait wait = new WebDriverWait(driver1, delayedTime);
                driver1.get(site);
//                driver1.manage().timeouts().pageLoadTimeout(4000, TimeUnit.SECONDS);
                driver = driver1;
//                driver1.close();
        	}catch(Exception e){
            	delayedTime += 100;
                System.out.println("CAUGHT");
                continue;
            }
            connecting = false;
            
            System.out.println("Connected");
        }
 
        WebElement showMore = null;
        int elemNum = 1;
        //--loadding full page
        while(elemNum > 0){
        	elemNum = driver.findElements(By.linkText("Show more cars")).size();
            
            if(elemNum > 0){
                showMore = driver.findElement(By.linkText("Show more cars"));
                System.out.println(showMore);
                showMore.click();
            }
            System.out.println(elemNum);
        }
    	//--
		Document tempDoc = Jsoup.parse(driver.getPageSource());
		
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();//all offers
		
		LinkedHashSet<String> categories = new LinkedHashSet<String>();// unique categories

		Elements elems = tempDoc.select("div.ct-car.ct-box.ct-transparent"); // select cat elements
		System.out.println(elems.size());
		if (elems.size() > 0){
			for(Element he : elems){
				
				String transm = he.select("div.ct-car-rate-btn-wrapper button[data-cta-value]").attr("data-cta-value").toString().substring(2, 3);
				
//				System.out.println(transm);//transmission
				String supplier = he.select("li.ct-icon-vendor [title]").attr("title").toString();//supplier
				String category = he.select("h6.ct-car-type span").text();//category
				categories.add(category);
//				he.select("div.ct-car-rate-btn-wrapper button[data-cta-value]").attr("data-cta-value").toString());category + transm
				
				String catTransm = null;
				if(transm.contains("M")){
					catTransm = category.toLowerCase() + "m";
//					System.out.println("transmission works m: " + catTrans);
				}else{
					catTransm = category.toLowerCase() + "a";
//					System.out.println("transmission works a: " + catTrans);
				}
				
				String priceStr = he.select("h2.ct-bdo-left.ct-text-right span").text();//price
				String[] price = priceStr.replace(",","").split(" ");
				try{
					float ownPrice = Float.parseFloat(price[1]);// get price
					Offer o = new Offer(ownPrice, supplier);
//					System.out.print(o.toString());
//					System.out.println(supplier + " " + ownPrice);
					
					for(String s : Sites.sScanner){
						// if null initialize
						if(map.get(s) == null){
							map.put(s, new ArrayList<Offer>());
						}
						s.replace("-", " ");
						System.out.println(catTransm + " " + s );
						if(catTransm.toLowerCase().contains(s.toLowerCase())){
							System.out.println(o.toString() + " added.");
							map.get(s).add(o);
						}else{
							
						}
					}
					for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
						System.out.println("Key : " + entry.getKey() + " Value : "
							+ entry.getValue().toString());
					}
					for (String s : categories) {
						System.out.print(s + ", ");
					}
				}catch(NumberFormatException nfe){
					System.out.println("-->Ilegal number format" + nfe.getMessage());
				}
			}
			
		}else{
			PrintWriter writer;
			try {
				writer = new PrintWriter("output.html", "UTF-8");
				writer.println(tempDoc.body());
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("---->CHECK OUTPUT");
		}
		driver.close();
		return map;
	}
	public static void getHireMarket(String site){
//	    site = "http://www.carhiremarket.com/liveoffers.aspx?Search_ID=1933080320";
		        
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	   
	            	
	            	
	    WebClient webClient = new WebClient(BrowserVersion.CHROME);
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    webClient.waitForBackgroundJavaScript(3 * 1000);
	    
	    HtmlPage myPage = null;
		try {
			myPage = webClient.getPage(site);
		} catch (FailingHttpStatusCodeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	   //get page num count
	    
	    HtmlElement pageNumsElem = myPage.getFirstByXPath("/html/body/form/div[3]/div[2]/div[2]/div[2]/div[6]/div/span");
//        System.out.println(pageNums.getChildElementCount());  
        List<HtmlElement> elemList = pageNumsElem.getHtmlElementsByTagName("a");
        HtmlElement next = null;
        
        //get offrers count 

        String ofrsNum = "/html/body/form/div[3]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[1]/span/span";
		HtmlElement ofrsNumCount = myPage.getFirstByXPath(ofrsNum);
		System.out.println("ofrs num is "  + ofrsNumCount.asText());
		
		while(ofrsNumCount.asText().isEmpty()){
			webClient.waitForBackgroundJavaScript(2000);
	        ofrsNumCount = myPage.getFirstByXPath(ofrsNum);
	        System.out.println("Counting");
			
		}
		int offersNum = Integer.parseInt(ofrsNumCount.asText());
		System.out.println("ofrs num is "  + offersNum);
		
        int num = 0;
        int loopNum = 0;
        int pageNum = pageNumsElem.getChildElementCount();
        int loopCondition = 0;
        
        
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("output.html", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HtmlPage tempPage = myPage;
		boolean quit = true;
//		while(quit)
		{
			
	        while(loopCondition < elemList.size()){
	        	System.out.println(loopNum++ + " Loop");
	        	webClient.waitForBackgroundJavaScript(3000);
	        	
	        	
				String offersStr = "/html/body/form/div[3]/div[2]/div[2]/div[2]/div[4]";
				next = myPage.getFirstByXPath("/html/body/form/div[3]/div[2]/div[2]/div[2]/div[6]/div/a[2]");
				
				HtmlElement offers = myPage.getFirstByXPath(offersStr);
				
				System.out.println(offersNum + " " + num);
				
				HtmlElement tempPageOffers = tempPage.getFirstByXPath(offersStr);
				System.out.println("Before Continue: " + offers.getChildElementCount() + " " + loopCondition + " " + tempPageOffers.getChildElementCount());
				
				
				
				if(!offers.equals(tempPageOffers)){
					writer.println(tempPageOffers.asText() + "\n--temp offer ----" );
					writer.println(offers.asText() + "\n---- offer ----" );
					 writer.close();
					Scanner keyboard = new Scanner(System.in);
					String text= keyboard.nextLine();
					System.out.println("Continue");
					continue;
				}
				num += offers.getChildElementCount();
				loopCondition++;
				//----proceed data
				
//				System.out.println(elems.size());
//				HtmlElement elems = offers; 
				Document doc = Jsoup.parse(myPage.asXml());
				List<Element> offerList = doc.select("div.offer.offerPkg2");
				
				System.out.println(offerList.size());
				for(Element elem : offerList){
					System.out.println(elem.attr("data-price").toString());//price
					
					String category = elem.select("div.category").text();
					System.out.println(category);//category
					
					String transm = elem.select("ul.features li.transmission strong").text();
					System.out.println(transm);//transmission
					
					Elements supplierElem = elem.select("a.btnLogo img[title]");
					System.out.println(supplierElem.attr("title").toString());//supplier
				}
				
				//--
				try {
					System.out.println("Click");
					tempPage = next.click();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				pageNumsElem = myPage.getFirstByXPath("/html/body/form/div[3]/div[2]/div[2]/div[2]/div[6]/div/span");
	            
				System.out.println(pageNumsElem.getChildElementCount() + " " + pageNum);
				
				
				System.out.println("num: " + num);
				
	        }
	        ofrsNumCount = myPage.getFirstByXPath(ofrsNum);
	        offersNum = Integer.parseInt(ofrsNumCount.asText());
	        if(num == offersNum){
	        	quit = false;
	        }else{
	        	loopCondition = 0;
	        }
		}
       
        System.out.println("offers found: " + num);
 
           
           
//       //click select button    
//           
//           int tries = 0;
//           HtmlElement select = myPage.getAnchorByText("Select");
//           HtmlPage page;
//		try {
//			page = select.click();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//           webClient.waitForBackgroundJavaScript(10000);
//   
//           
//           
//           String searchStr = "/html/body/form/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/p[5]/a/span[1]";
//           HtmlElement search  = myPage.getFirstByXPath(searchStr);
//           page =  search.click();
//           System.out.println(myPage.getTitleText());
//           
//        
//        
//        while(myPage.getFirstByXPath(searchStr) != null){
//     	  webClient.waitForBackgroundJavaScript(10000);
//     	  System.out.println(page.getTitleText());
//     	  System.out.println("select is displayed " + select.isDisplayed());
//         
//     	  System.out.println("search is displayed " + search.isDisplayed());
//         
//     	
//     	  search  = myPage.getFirstByXPath(searchStr);
//     	  System.out.println("select is displayed " + search.asXml());
//     	  try {
//			page =  search.click();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//     	   
//        }	
	 }
	public HashMap<String, ArrayList<Offer>> getAirbaltic(String siteName) throws IOException{
		Document doc = null;
        try {
        	doc = Jsoup.connect(siteName).timeout(0).get();
        	
         } catch (IOException e) {
//            System.out.println("--> GETTAGS Something went wrong" + "\n" + siteName + ".");
            e.printStackTrace();
            return null;
        }
		
        //get prices and suppliers
//		Elements prices = doc.select("div.car-result.group div.car-result-r div.price div.dis div.dis-inner p.now");
//		Elements offersOnPage = doc.getElementsByClass("car-result"); 
		Elements offersOnPage = doc.getElementsByAttributeValueContaining("class", "car-result group");
		offersOnPage.remove(offersOnPage.size() - 1);
		System.out.println("--NUM-->offers found " + offersOnPage.size());
		
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();
		LinkedHashSet<String> categories = new LinkedHashSet<String>();// unique categories
		
		for(Element e : offersOnPage){
//			System.out.println(e.select("p.now").text());
//			System.out.println(e.select("div.supplier_id img[title]").attr("title").toString());
//			System.out.println(e.select("span.class.mini").text());
//			System.out.println();
//			
			String supplier = e.select("div.supplier_id img[title]").attr("title").toString();
			String category = e.select("span.class.mini").text();
			String transm = e.select("li.result_trans").text();
		
//			System.out.println("-->TRANSMISSION " + transm);//transmission
			if(transm.length() < 2){
				PrintWriter writer = new PrintWriter("output.html", "UTF-8");
				writer.println(doc.html() + "\n--temp offer ----" );
				writer.close();
			}
			
			transm = e.select("li.result_trans").text().toString().substring(0, 1);
			
			categories.add(category);
			String catTransm = null;
			if(transm.equals("M")){
				catTransm = category.toLowerCase() + "m";
//					System.out.println("transmission works m: " + catTrans);
			}else{
				
				catTransm = category.toLowerCase() + "a";
//					System.out.println("transmission works a: " + catTrans);
			}
			String priceStr = e.select("p.now").text();
//			if (priceStr.length() == 0)
//				priceStr = e.select("div.saving-included > p.now").text();
			
//			Elements prices = doc.select("div.dis-inner > p.now");
//			if(prices.size() == 0){
//				prices = doc.select("div.saving-included > p.now");
//			}
//			System.out.print(priceStr);
			String[] price = priceStr.replaceAll(",",".").split(" ");
			
			try{
				float ownPrice = Float.parseFloat(price[0]);// get price
				Offer o = new Offer(ownPrice, supplier);
	//					System.out.print(o.toString());
	//					System.out.println(supplier + " " + ownPrice);
				o.setSite(siteName);
				for(String s : Sites.sAirbalticLt){
					// if null initialize
					if(map.get(s) == null){
						map.put(s, new ArrayList<Offer>());
					}
					s.replace("-", " ");
//					System.out.println(catTransm + " " + s );
					if(catTransm.toLowerCase().contains(s.toLowerCase())){
						System.out.println(o.toString() + " added.");
						map.get(s).add(o);
					}else{
						
					}
				}
//				for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
//					System.out.println("Key : " + entry.getKey() + " Value : "
//						+ entry.getValue().toString());
//				}
//				for (String s : categories) {
//					System.out.print(s + ", ");
//				}
			}catch(NumberFormatException nfe){
				System.out.println(priceStr);
				System.out.println("-->Ilegal number format"  + nfe.getMessage());
			}
		
		}
		return map;
	}
	public int connectionTest(String siteName){
		int num = 0;
		Document doc = null;
		Response res = null;
		try {
			res = Jsoup
				    .connect(siteName)
				    .timeout(0)
				    .method(Method.GET)
				    .execute();
			
			//This will get you cookies
			Map<String, String> cookies = res.cookies();

			//And this is the easieste way I've found to remain in session
			doc = Jsoup.connect(siteName).timeout(0).cookies(cookies).get();
			if(doc == null){
				System.out.println("is null");
			}else{
				PrintWriter writer;
				writer = new PrintWriter("test(cookie).html", "UTF-8");
				writer.println(doc.body());
				writer.close();
//				System.out.println(doc.html());
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}
	public int connectionTest1(String siteName){
		int num = 0;
		Document doc = null;
		Response res = null;
		try {
			res = Jsoup
				    .connect(siteName)
				    .timeout(0)
				    .method(Method.POST)
				    .execute();
			
			//This will get you cookies
			Map<String, String> cookies = res.cookies();

			//And this is the easieste way I've found to remain in session
			doc = Jsoup.connect(siteName).timeout(0).get();
			if(doc == null){
				System.out.println("is null");
			}else{
				PrintWriter writer;
				writer = new PrintWriter("test(no_cookie).html", "UTF-8");
				writer.println(doc.body());
				writer.close();
//				System.out.println(doc.html());
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}
};
