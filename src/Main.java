import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Element;


public class Main {
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("-->Main started");

		SourceReader sr = new SourceReader();
		Sites sites = new Sites();
		String siteName = sites.getSiteName();
		sr.getMinOffer(siteName);
		
		
		System.out.println("-->Main done");
	}
}
