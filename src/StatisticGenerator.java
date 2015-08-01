import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.DocumentException;


public class StatisticGenerator {
	
	public static void genABalticRCars() throws DocumentException, IOException{
		
		//load site from file sites.txt
		Sites sites = new Sites();
		//create source reader for reading site source
		SourceReader sr = new SourceReader();
		for (int index = 0; index < 2; index++){
			//create pdf with site name
			PdfBuilder pdf = new PdfBuilder(sites.sites.get(index));
			//ini days to count for
			int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 17, 20, 21};
			//
			sites.setDate(2);
			System.out.println(sites.sites.size());
			for(int i = 0; i < days.length - 1; i++){
				sites.setDate(days[i+1]);
				sites.setCategories();
				sites.resetSiteName();
				
				ArrayList<String> sNames = sites.getSiteNames();// get created sites by categorie according to date
				ArrayList<Offer> offers = new ArrayList<Offer>();//ini array where all offers will be
				

				
				for (String site : sNames){//for each get get min offer
					Offer o = sr.getMinOffer(site);
					offers.add(o);//save offer to list
					System.out.println("Offer site:" + "/n" + o.getSite() + '\n');
				}
				pdf.addOffer(offers, days[i]);
			}
			
			pdf.finishGenerating();
			pdf.saveDocument();

		}
	}
}
