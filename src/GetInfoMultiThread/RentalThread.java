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
	private String threadName;
    private ArrayList<Offer> offers;
	
    public RentalThread(String name){
       threadName = name;
       
       //System.out.println("Creating " +  threadName );
	}	
	
	public void run(){
		//System.out.println("Thread " +  threadName + " interrupted.");
		SourceReader sr = new SourceReader();
		Sites sites = new Sites();
		int index = 0;
		PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
		try {
			offers = sr.getTags(threadName);
			System.out.println(offers.size());
			Thread.sleep(50);
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			//System.//System.out.println("Thread " +  threadName + " interrupted.");
			    
			e.printStackTrace();
		}
	}
	
	public void start (){
      //System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
	}
	public ArrayList<Offer> getArray(){
		
		return offers;
    }
};

