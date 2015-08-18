package Pricing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;

public class PricingManager {
	
	public void fillPrices(String siteName){
		// info carsrent
//		File input = new File("E://Downloads//Mozila//FuseMetrix Enterprise Server - real time, all the time_failai//welcomeServ.htm");
//		org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		try {
			
			HtmlPage page = webClient.getPage("http://gmlithuania.fusemetrix.com/index.php");
			String priceOverrides = "/html/body/div[2]/table/tbody/tr[5]/td/a";
			
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		    webClient.waitForBackgroundJavaScript(3 * 1000);
//			String priceCell = "/html/body/form/table[2]/tbody/tr[3]/td[1]/input";
			List<?> elems = page.getByXPath(priceOverrides);
			String content = page.asXml();
			System.out.println(content + "/n" + elems.size());
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Elements prices = doc.select("div.dis-inner > p.now");
//		if(prices.size() == 0){
//			prices = doc.select("div.saving-included > p.now");
//		}
	}

}
