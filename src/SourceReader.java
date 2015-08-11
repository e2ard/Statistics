import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NoHttpResponseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
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
//		System.out.println("--> Prices num:" + prices.size() + " " +  suppliers.size());
		
//		System.out.println("--> Suppliers num:" + suppliers.size());
		String[] price = null;
		
		if(prices.size() == suppliers.size() && prices.size() != 0){
//			System.out.println("-->OK, PRICES EQUAL SUPPLIER NUM");
			for (int i = 0; i < suppliers.size(); i++){
				
				price = prices.get(i).text().replace(',', '.').split(" ");
				float ownPrice = Float.parseFloat(price[0]);
				
				String supplier = suppliers.get(i).attr("title");
				Offer offer = new Offer(ownPrice, supplier);
				offer.setSite(siteName);
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
				offer.setSite(e.getSite());
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
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();
		
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			try {
				WebClient webClient = new WebClient(BrowserVersion.CHROME);
			    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			    webClient.waitForBackgroundJavaScript(3 * 1000);
			    HtmlPage myPage =  webClient.getPage(site);
			    String theContent = myPage.asText();
//			    System.out.println(theContent);
			    doc = Jsoup.parse(theContent);
				Elements elems = doc.select("h2");
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
			System.out.println("-->Something went wrong" + e.getMessage()); 
			e.printStackTrace();
	    }
//		for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : "
//				+ entry.getValue().toString());
//		}
		return map;
	}
	
	public static HashMap<String, ArrayList<Offer>> getCarScanner(){
		org.jsoup.nodes.Document doc = null;
		HashMap<String, ArrayList<Offer>> map = new HashMap<String, ArrayList<Offer>>();
		String site = ""
				+ "http://cars-scanner.com/en/?_c=fnd.default&clientID=334782&elID=0807212554181994514&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&utm_source=google&utm_medium=poisk&utm_content=price_compare&utm_campaign=global_words&_c=fnd.default&gclid=CJTS9ojnl8cCFfHJtAodF8EANg#book";
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			try {
				WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
			    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			    webClient.waitForBackgroundJavaScript(1000);
			    webClient.getOptions().setThrowExceptionOnScriptError(false);
			    HtmlPage myPage =  webClient.getPage(site);
//			    String theContent = myPage.asText();
			    
			    String showMoreStr = "/html/body/table/tbody/tr[1]/td/div[7]/div/div/div[1]/div[2]/div[2]/div/div/section/div[2]/div/div[2]/div[26]";
			    ArrayList<HtmlElement> tempElems = (ArrayList<HtmlElement>) myPage.getByXPath(showMoreStr);
			    if(tempElems.size() > 0){
//			    	synchronized (webClient) {
//		        	    webClient.wait(2000);
//		        	}
			    	HtmlElement showMore = tempElems.get(0);
			    	System.out.println("Show more, found: size" + tempElems.size());
			    	webClient.waitForBackgroundJavaScript(2000);
			    	System.out.println(myPage.equals(showMore.click()));
			    	webClient.waitForBackgroundJavaScript(2000);
			    }
			    
//			    System.out.println(myPage.asXml());
			    String elemsStr = "/html/body/table/tbody/tr[1]/td/div[7]/div/div/div[1]/div[2]/div[2]/div/div/section/div[2]/div/div[2]";
				List<HtmlElement> dmList= (List<HtmlElement>) myPage.getByXPath(elemsStr);//get all offer div element
//				System.out.println(dmList.size());

//				myPage = dmList.get(0).getHtmlPageOrNull();
//				Document tempDoc = Jsoup.parse(myPage.asXml());
				Document tempDoc = Jsoup.parse(myPage.asXml());
				Elements elems = tempDoc.select("div.ct-car.ct-box.ct-transparent");
				System.out.println(elems.size());
				if (elems.size() > 0){
					for(Element he : elems){
						System.out.println(he.select("li.ct-icon-vendor [title]").attr("title").toString());//supplier
	//					System.out.println(he.select("h6.ct-car-type span").text());//category
						System.out.println(he.select("div.ct-car-rate-btn-wrapper button[data-cta-value]").attr("data-cta-value").toString());
						System.out.println(he.select("h2.ct-bdo-left.ct-text-right span").text());//price
					}
				}else{
					PrintWriter writer = new PrintWriter("output.html", "UTF-8");
					writer.println(tempDoc.body());
					writer.close();
					System.out.println("---->CHECK OUTPUT");
				}
//				while(dmList.size() == 0){
//					dmList= (List<HtmlElement>) myPage.getByXPath(elemsStr);
//					webClient.waitForBackgroundJavaScript(1000);
//				    myPage = webClient.getPage(site);
//				    theContent = myPage.asText();
//				    System.out.println(theContent);
//				   	
//				    System.out.println(dmList.size());
//				    if(dmList.size() > 0){
//				    	for(HtmlElement he : dmList.get(0).getElementsByTagName("li")){
//				    		myPage = he.click();
//				    		Document tempDoc = Jsoup.parse(myPage.asXml());
//				    		Elements tempElems = tempDoc.select("div.ct-grid.ct-relative");
////				    		System.out.println(tempElems.size());
//				    		for(Element e : tempElems){
//				    			String category = e.getElementsByClass("ct-no-margin-bottom").last().text();//get category
////								System.out.println(category);
//								String transmission = e.getElementsByClass("ct-grid").text();
////								System.out.println(transmission);
////								System.out.println(transmission.contains("Manual"));
//								
//								String catTrans = null;
//								if(transmission.contains("Manual")){
//									catTrans = category.toLowerCase() + "m";
////									System.out.println("transmission works m: " + catTrans);
//								}else{
//									catTrans = category.toLowerCase() + "a";
////									System.out.println("transmission works a: " + catTrans);
//								}
//							
//								Element temp = e.getElementsByClass("ct-padding-top").first();//get supplier 	
//								String supplier = temp.getElementsByTag("img").attr("alt").toString();
//								String priceStr = e.getElementsByTag("h2").first().text();
//								String[] price = priceStr.replace(",","").split(" ");
//								try{
//									float ownPrice = Float.parseFloat(price[1]);// get price
//									Offer o = new Offer(ownPrice, supplier);
////									System.out.print(o.toString());
////									System.out.println(supplier + " " + ownPrice);
//									for(String s : Sites.sNorwegian){
//										// if null initialize
//										if(map.get(s) == null){
//											map.put(s, new ArrayList<Offer>());
//										}
//										
//										if(catTrans.toLowerCase().equals(s.toLowerCase())){
////											System.out.println(o.toString() + " added.");
//											map.get(s).add(o);
//										}else{
//											
//										}
//									}
//								}catch(NumberFormatException nfe){
//									System.out.println("-->Ilegal number format" + nfe.getMessage());
//								}
//							}
//				    	}
//					}
//				}
//				webClient.close();
			}catch (NoHttpResponseException ne){
				System.out.println("-->Not http response" + ne.getMessage());
			}
			
		} catch (IOException e) {
			System.out.println("-->Something went wrong" + e.getMessage()); 
			e.printStackTrace();
	    }
//		for (Entry<String, ArrayList<Offer>> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : "
//				+ entry.getValue().toString());
//		}
		return map;
	}
	
	public static void getScannerSilenium() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String site = ""
				+ "http://cars-scanner.com/en/?_c=fnd.default&clientID=334782&elID=0807212554181994514&pickupID=3224&returnID=3224&pickupName=Vilnius%20Airport&returnName=Vilnius%20Airport&pickupDateTime=2015-09-01T10:00:00&returnDateTime=2015-09-02T10:00:00&age=30&curr=EUR&carGroupID=0&residenceID=LT&CT=AJ&referrer=0:&utm_source=google&utm_medium=poisk&utm_content=price_compare&utm_campaign=global_words&_c=fnd.default&gclid=CJTS9ojnl8cCFfHJtAodF8EANg#book";
//		String showMoreStr = "/html/body/table/tbody/tr[1]/td/div[7]/div/div/div[1]/div[2]/div[2]/div/div/section/div[2]/div/div[2]/div[26]";
	    
		DesiredCapabilities caps = new DesiredCapabilities();
	    caps.setJavascriptEnabled(true); 
	    caps.firefox();
//	    String phantombinary = "C:\\bin\\phantomjs\\bin";
//	    caps.setCapability(
//	        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//	        phantombinary//"/home/p/phantomjs-1.9.2-linux-x86_64/bin/phantomjs"
//	    );
//		
		WebDriver driver = new PhantomJSDriver(caps);
//		WebDriverWait wait = new WebDriverWait(driver, 30);
		
        WebElement showMore = driver.findElement(By.linkText("Show more cars"));
    	showMore.click();
        
    	do{
	        try {
	        	synchronized (driver) {
	        	    driver.wait(2000);
	        	}
	        	if(driver.findElements(By.linkText("Show more cars")).size() > 0){
	        		showMore = driver.findElement(By.linkText("Show more cars"));
	        		System.out.println(showMore);
					showMore.click();
				}
	       
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
        }while(driver.findElements(By.linkText("Show more cars")).size() > 0);
    	
    	HtmlPage myPage =  ((WebClient) driver).getPage(site);
    	String elemsStr = "/html/body/table/tbody/tr[1]/td/div[7]/div/div/div[1]/div[2]/div[2]/div/div/section/div[2]/div/div[2]";
		List<HtmlElement> dmList= (List<HtmlElement>) myPage.getByXPath(elemsStr);//get all offer div element
//		System.out.println(dmList.size());

//		myPage = dmList.get(0).getHtmlPageOrNull();
//		Document tempDoc = Jsoup.parse(myPage.asXml());
		Document tempDoc = Jsoup.parse(myPage.asXml());
		Elements elems = tempDoc.select("div.ct-car.ct-box.ct-transparent");
		System.out.println(elems.size());
		if (elems.size() > 0){
			for(Element he : elems){
				System.out.println(he.select("li.ct-icon-vendor [title]").attr("title").toString());//supplier
//					System.out.println(he.select("h6.ct-car-type span").text());//category
				System.out.println(he.select("div.ct-car-rate-btn-wrapper button[data-cta-value]").attr("data-cta-value").toString());
				System.out.println(he.select("h2.ct-bdo-left.ct-text-right span").text());//price
			}
		}else{
			PrintWriter writer = new PrintWriter("output.html", "UTF-8");
			writer.println(tempDoc.body());
			writer.close();
			System.out.println("---->CHECK OUTPUT");
		}
//    	driver.quit();
	}
	
}
