package Logging;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.NoHttpResponseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Source.Sites;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;


public class LogIn {
		   
    public void getCarHireHtml() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
        org.jsoup.nodes.Document doc = null;
        String site = "http://www.carhiremarket.com/";
        
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
   
            	
            	
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.setAjaxController(new NicelyResynchronizingAjaxController());
                webClient.waitForBackgroundJavaScript(3 * 1000);
                HtmlPage myPage =  webClient.getPage(site);
                String theContent = myPage.asText();
    //            System.out.println(theContent);
                
                
                    webClient.waitForBackgroundJavaScript(1000);
                    myPage = webClient.getPage(site);
            //click choose from list
                   
                   String dropDown = "/html/body/form/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/p[1]/span[1]";
                   System.out.println("1FOUND");
                   
                   
                   
                   
                   HtmlElement chooseFrom = myPage.getAnchorByText("choose from list");
                   chooseFrom.click();
                   
           //--choose from dropDown
                   HtmlSelect elem = (HtmlSelect) myPage.getElementById("modalCountry");
                   elem.click();
                   int num  =  elem.getOptionSize();
                   while(elem.getOptionSize() <= 1){
                	   System.out.println("while" + elem.getOptionSize());
                	   webClient.waitForBackgroundJavaScript(3000);
//                	   myPage = webClient.getPage(site);
                	   elem = (HtmlSelect) myPage.getElementById("modalCountry");
                	   
                	   
                	   elem.click();
                       synchronized (myPage) {
                           try {
                               myPage.wait(5000);
                           } catch (InterruptedException e) {
                        	   System.out.println("Exception");
                           }
                       }
//                	   System.out.println(theContent);
//                       System.out.println(elem.asXml());
//                       num = elem.getOptionSize();
                   }
                   System.out.println(elem.getOptionSize());
                   HtmlOption option = elem.getOptionByText("Lithuania");
                   elem.setSelectedAttribute(option, true);
                   
                //--choose dropDown Lithuania   
                   HtmlSelect selectReg = (HtmlSelect) myPage.getElementById("modalRegion");
                   selectReg.click();
                   while(selectReg.getOptionSize() <= 1){
                	   System.out.println("while" + selectReg.getOptionSize());
                	   webClient.waitForBackgroundJavaScript(3000);
//                	   myPage = webClient.getPage(site);
                	   selectReg = (HtmlSelect) myPage.getElementById("modalRegion");
                	   
                	   
                	  myPage =  selectReg.click();
                       synchronized (myPage) {
                           try {
                               myPage.wait(5000);
                           } catch (InterruptedException e) {
                        	   System.out.println("Exception");
                           }
                       }
//                	   System.out.println(selectReg.asXml());
                       num = elem.getOptionSize();
   					
                   }
                   System.out.println(num);
                   option = elem.getOptionByText("Lithuania");
                   elem.setSelectedAttribute(option, true);
                   
         //choose from dropDown Vilnius      
                   HtmlSelect selectLoc = (HtmlSelect) myPage.getElementById("modalLocation");
                   selectLoc.click();
                   while(selectLoc.getOptionSize() <= 1){
                	   System.out.println("while" + selectLoc.getOptionSize());
                	   webClient.waitForBackgroundJavaScript(3000);
//                	   myPage = webClient.getPage(site);
                	   selectLoc = (HtmlSelect) myPage.getElementById("modalLocation");
                	   
                	   
                	  myPage =  selectLoc.click();
                	  System.out.println(selectLoc.asXml());
                       synchronized (myPage) {
                           try {
                               myPage.wait(5000);
                           } catch (InterruptedException e) {
                        	   System.out.println("Exception");
                           }
                       }
                       num = selectLoc.getOptionSize();
                	
                   }
                   
                   
                   System.out.println(selectLoc.getOptionSize());
                   option = selectLoc.getOption(selectLoc.getOptionSize() - 1);
                   selectLoc.setSelectedAttribute(option, true);
                   System.out.println(selectLoc.getSelectedIndex());
                   
               //click select button    
                   
                   int tries = 0;
                   HtmlElement select = myPage.getAnchorByText("Select");
                   HtmlPage page = select.click();
                   webClient.waitForBackgroundJavaScript(10000);

                   System.out.println(myPage.asXml().equals(page.asXml()));
                   System.out.println(myPage.getTitleText());
                   
                   
                   
                   String searchStr = "/html/body/form/div[3]/div[2]/div[1]/div[1]/div[1]/div[2]/p[5]/a/span[1]";
                   HtmlElement search  = myPage.getFirstByXPath(searchStr);
                   page =  search.click();
                   System.out.println(myPage.getTitleText());
                   
                
                
                while(myPage.getFirstByXPath(searchStr) != null){
             	  webClient.waitForBackgroundJavaScript(10000);
             	  System.out.println(page.getTitleText());
             	  System.out.println("select is displayed " + select.isDisplayed());
                 
             	  System.out.println("search is displayed " + search.isDisplayed());
                 
             	
             	  search  = myPage.getFirstByXPath(searchStr);
             	  System.out.println("select is displayed " + search.asXml());
             	  page =  search.click();
             	   
                }	
                
              
    }
    public String carHire(){
    	
    	String connectionUrl = "http://www.carhiremarket.com/";
		Sites s = new Sites();
		String paramsStr = "params"; 
		
		
		String params = s.readFile(paramsStr);
	
	    URL obj;
		try {
			obj = new URL(connectionUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
		    con.setRequestProperty("User-Agent", "Mozilla/5.0");
		    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		    String urlParameters = params;

			    // Send post request
		    con.setDoOutput(true);

		    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.flush();
		    wr.close();
		    
		    //add reuqest header
		    
		    int responseCode = con.getResponseCode();
		    return con.getURL().toString();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
    	
    	return null;
    	
    }
};

