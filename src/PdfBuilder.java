import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfBuilder {
  private PdfPTable table;
  Document document;
  String source;
	
  private static String FILE = "";
  
  public PdfBuilder(String site){
	  setSource(site);
	  generatePdf();
  }
  
//  public void setSource(String site){
//	org.jsoup.nodes.Document doc = null;
//	try {
////		System.out.println(site);
//		doc = Jsoup.connect(site).timeout(0).get();
//	} catch (IOException e) {
//      e.printStackTrace();
//      System.out.println("-->Something went wrong");
//	}
//	Elements elems = doc.select("#header-main > div.container > div > a");
//	if(elems.size() != 0){
//		source = elems.get(0).attr("href").toString();
//	}else if (doc.select("#header-main > div.container > a").size() != 0){
//		source = doc.select("#header-main > div.container > a").get(0).attr("href").toString();
//	}else{
//		source = "www.norwegian.com";
//	}
//	
//  }
  public String setSource(String site){
		String str = ".*(\\.).{3}\\/";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(site);
		if (matcher.find())
		{
			source = matcher.group(0);
			return source = source.substring(0, source.length()-1);
		}
		
		return "unknown"; 
  }
  public String getSource(){
	  return source;
  }
	
  public void generatePdf(){
	  try {
	      document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
	      document.top(20);
	      if (source.contains(".")) {
	    	  PdfWriter.getInstance(document, new FileOutputStream(FILE + source.split("/.")[1] + ".pdf"));
		      document.open();
		      
		      createTable();
    	  } else {
	    	    throw new IllegalArgumentException("String " + source + " does not contain -");
    	  }
	      
	      
//	      System.out.println(sourceName.length + "\n" + source + "\n" + FILE + source + ".pdf");
	      
	      
	    } catch (Exception e) {
	      e.printStackTrace();
      }
  }
  
  public void finishGenerating() throws DocumentException{
	  document.add(table);
  }
  public void saveDocument(){
	  document.close();
  }
  private void createTable()
	 throws BadElementException {
    table = new PdfPTable(12);

    PdfPCell c1 = new PdfPCell(new Phrase(source));
    c1.setColspan(12);
    
    table.setWidthPercentage(95);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    table.getDefaultCell().setColspan(12);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Data"));
     
    table.addCell(c1);
    
    for(String str : Sites.sPdfClasses){
    	c1 = new PdfPCell(new Phrase());
    	Chunk c = new Chunk(str);
		c1.addElement(c);
		table.addCell(c1);
	}
    table.setHeaderRows(1);
    
  }
  public void addOffersRow(ArrayList<Offer> offers, int day){
	PdfPCell c1 = new PdfPCell(new Phrase());
	Chunk c = new Chunk(Main.month + "-" + Main.pickupDay + "/" + day);
	
	c1.addElement(c);
	table.addCell(c1);
	
	for(int index = 0; index < offers.size(); index++){
		c1 = new PdfPCell(new Phrase());
		c = new Chunk(((offers != null) && (offers.size() > 0))?offers.get(index).getOffer():"Tu��ia");
		c.setAnchor(offers.get(index).getSite());
		c1.addElement(c);
		table.addCell(c1);
	}
  }
  
}

