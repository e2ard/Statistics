import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Element;


public class Main {
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Main started");
		String className = ".car-result";
		String tagName = "div";
		SourceReader sr = new SourceReader();
		
		ArrayList<Offer> temp = sr.getTags(className, tagName);
	
		
		for(Offer e:temp){
			
			System.out.println("\n" + e.toString() + '\n');
		}
		System.out.println("Main done");
	}
}
