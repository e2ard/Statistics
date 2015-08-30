package GetInfoMultiThread;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;

import com.itextpdf.text.DocumentException;

import Source.Offer;
import Source.PdfBuilder;
import Source.Sites;
import Source.SourceReader;

public class RentalThread extends Thread{
	private Thread t;
	private String siteName;
    private ArrayList<Offer> offers;
	
    public RentalThread(String name){
       siteName = name;
       
       //System.out.println("Creating " +  threadName );
	}	
	
	public void run(){
		//System.out.println("Thread " +  threadName + " interrupted.");
		SourceReader sr = new SourceReader();
		
		try {
			offers = sr.getTags(siteName);
			System.out.println(offers.size());
			Thread.sleep(150);
		
		} catch (InterruptedException e) {
			//System.//System.out.println("Thread " +  threadName + " interrupted.");
			    
			e.printStackTrace();
		}
	}
	
	public void start (){
      //System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, siteName);
         t.start ();
      }
	}
	public ArrayList<Offer> getArray(){
		
		return offers;
    }
	public String getSiteName(){
		return siteName;
	}
};

