package GetInfoMultiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Source.Offer;
import Source.PdfBuilder;
import Source.Sites;
import Source.SourceReader;

public class AirBaltic extends Thread{
	private Thread t;
	private String threadName;
    private HashMap<String, ArrayList<Offer>> map;
    private SourceReader sr;
	
    public AirBaltic(String name){
       threadName = name;
       sr = new SourceReader();
       map = new HashMap<String, ArrayList<Offer>>();
       //System.out.println("Creating " +  threadName );
	}	
	
	public void run(){
		//System.out.println("Thread " +  threadName + " interrupted.");
		SourceReader sr = new SourceReader();
		Sites sites = new Sites();
		int index = 0;
		PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
		try {
			map = sr.getAirbaltic(threadName);
//			for(String str : Sites.sAirbalticLt){
//				System.out.println("Key : "  + str +  " Value :  " + map.get(str) );
//						
//			}
			Thread.sleep(150);
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
	public HashMap<String, ArrayList<Offer>> getMap(){
		return map;
    }
};
