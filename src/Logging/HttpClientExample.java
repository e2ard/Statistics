package Logging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.jetty.http.HttpFields.Entry;

import Source.Sites;

public class HttpClientExample {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		
		Sites s = new Sites();
		String paramsStr = "params"; 
		System.out.println(s.readFile(paramsStr));
		
		
		
		HttpClientExample http = new HttpClientExample();

		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet();

		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendJson();

	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = "http://www.carhiremarket.com/";
		Sites s = new Sites();
		String paramsStr = "params"; 
		
		
		String params = s.readFile(paramsStr);
	
	    URL obj = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	    //add reuqest header
	    con.setRequestMethod("POST");
	    con.setRequestProperty("User-Agent", USER_AGENT);
	    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	    String urlParameters = params;

		    // Send post request
	    con.setDoOutput(true);

	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    wr.writeBytes(urlParameters);
	    wr.flush();
	    wr.close();

	    int responseCode = con.getResponseCode();
//	    System.out.println("Response Code : " + responseCode);
	    
	    System.out.println(con.getURL());
	    
	}
	private void sendPost() throws Exception {

		String url = "http://www.carhiremarket.com/";
		Sites s = new Sites();
		String paramsStr = "params"; 
		
		
		String params = s.readFile(paramsStr);
	
	    URL obj = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	    //add reuqest header
	    con.setRequestMethod("POST");
	    con.setRequestProperty("User-Agent", USER_AGENT);
	    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	    String urlParameters = params;

		    // Send post request
	    con.setDoOutput(true);

	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    InputStream in = con.getInputStream();  
	    Object inputLine;
	    
//		while ((inputLine = in.read()) != null) {
//			Scanner s1 = new Scanner(con.getInputStream(), "UTF-8").useDelimiter("\\A");
//            System.out.print());
//		}
		String myString = IOUtils.toString(in, "UTF-8");
		System.out.print(myString);
        in.close();
	    
	    
	    wr.writeBytes(urlParameters);
	    wr.flush();
	    wr.close();

	    int responseCode = con.getResponseCode();
//	    System.out.println("Response Code : " + responseCode);
	    
	    System.out.println(con.getURL());
	    
	}	
	// HTTP POST request
	public static String getPostData(HttpServletRequest req) {
	    StringBuilder sb = new StringBuilder();
	    try {
	        BufferedReader reader = req.getReader();
	        reader.mark(10000);

	        String line;
	        do {
	            line = reader.readLine();
	            sb.append(line).append("\n");
	        } while (line != null);
	        reader.reset();
	        // do NOT close the reader here, or you won't be able to get the post data twice
	    } catch(IOException e) {
	        System.out.println("getPostData couldn't.. get the post data");  // This has happened if the request's reader is closed    
	    }

	    return sb.toString();
	}
	
	// HTTP POST request
	private void sendJson() throws Exception {

		String url = "http://www.carhiremarket.com/";
		Sites s = new Sites();
		String paramsStr = "JsonReq"; 
		
		
		String params = s.readFile(paramsStr);
	
	    URL obj = new URL(url);
	    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	    //add reuqest header
	    con.setRequestMethod("POST");
	    con.setRequestProperty("User-Agent", USER_AGENT);
	    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	    con.setRequestProperty("Accept", "application/json");
	    con.setRequestProperty("Host", "www.carhiremarket.com");
	    con.setRequestProperty("Referer", "http://www.carhiremarket.com/liveoffers.aspx?Search_ID=1934075214");
	    con.setRequestProperty("X-Requested-With","XMLHttpRequest");
	    
	    String urlParameters = params;

		    // Send post request
	    con.setDoOutput(true);

	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    InputStream in = con.getInputStream();  
	    Object inputLine;
	    
//		while ((inputLine = in.read()) != null) {
//			Scanner s1 = new Scanner(con.getInputStream(), "UTF-8").useDelimiter("\\A");
//            System.out.print());
//		}
		String myString = IOUtils.toString(in, "UTF-8");
		System.out.print(myString);
        in.close();
	    
	    
	    wr.writeBytes(urlParameters);
	    wr.flush();
	    wr.close();

	    int responseCode = con.getResponseCode();
//	    System.out.println("Response Code : " + responseCode);
	    
	    System.out.println(con.getURL());
	    
	}	
		
		

}


