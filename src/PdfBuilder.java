import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
  
  public void setSource(String site){
	org.jsoup.nodes.Document doc = null;
	try {
//		System.out.println(site);
		doc = Jsoup.connect(site).timeout(0).get();
	} catch (IOException e) {
      e.printStackTrace();
      System.out.println("-->Something went wrong");
	}
	Elements elems = doc.select("#header-main > div.container > div > a");
	if(elems.size() != 0){
		source = elems.get(0).attr("href").toString();
	}else if (doc.select("#header-main > div.container > a").size() != 0){
		source = doc.select("#header-main > div.container > a").get(0).attr("href").toString();
	}else{
		source = "www.norwegian.com";
	}
	
  }
  public String getSource(){
	  return source;
  }
	
  public void generatePdf(){
	  try {
	      document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
	      document.top(20);
	      if (source.contains(".")) {
	    	  PdfWriter.getInstance(document, new FileOutputStream(FILE + source.split("\\.")[1] + ".pdf"));
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
    
    c1 = new PdfPCell(new Phrase("EDMR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("EDAR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("CDMR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("CDAR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("IDMR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("IDAR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("SDMR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("SDAR"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("SUV"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("SUV A"));
     
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("PVMR"));
     
    table.addCell(c1);
    table.setHeaderRows(1);
    
  }
  public void addOffer(ArrayList<Offer> offers, int day){
	
	
	PdfPCell c1 = new PdfPCell(new Phrase());
	Chunk c = new Chunk("09-" + "1/" + day);
	c1.addElement(c);
	table.addCell(c1);
	
	for(int index = 0; index < offers.size(); index++){
		c1 = new PdfPCell(new Phrase());
		c = new Chunk(((offers != null) && (offers.size() > 0))?offers.get(index).getOffer():"Tuðèia");
		c.setAnchor(offers.get(index).getSite());
		c1.addElement(c);
		table.addCell(c1);
	}
//	
//	
//	
//	c1 = new PdfPCell(new Phrase());
//	c = new Chunk(((offers != null) && (offers.size() > 0))?offers.get(0).getOffer():"Tuðèia");
//	c.setAnchor(offers.get(0).getSite());
//	c1.addElement(c);
//	table.addCell(c1);
//
//	c1 = new PdfPCell(new Phrase());
//	c = new Chunk(offers.get(1).getOffer());
//	c.setAnchor(offers.get(1).getSite());
//	c1.addElement(c);
//	table.addCell(c1);
//    
//	c1 = new PdfPCell(new Phrase());
//	c = new Chunk(offers.get(2).getOffer());
//	c.setAnchor(offers.get(2).getSite());
//	c1.addElement(c);
//	table.addCell(c1);
//	
//    c1 = new PdfPCell(new Phrase(offers.get(3).getOffer()));
//    c = new Chunk(offers.get(3).getOffer());
//	c.setAnchor(offers.get(3).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(4).getOffer()));
//    c = new Chunk(offers.get(4).getOffer());
//	c.setAnchor(offers.get(4).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(5).getOffer()));
//    c = new Chunk(offers.get(5).getOffer());
//	c.setAnchor(offers.get(5).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(6).getOffer()));
//    c = new Chunk(offers.get(6).getOffer());
//	c.setAnchor(offers.get(6).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(7).getOffer()));
//    c = new Chunk(offers.get(7).getOffer());
//	c.setAnchor(offers.get(7).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(8).getOffer()));
//    c = new Chunk(offers.get(8).getOffer());
//	c.setAnchor(offers.get(8).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(9).getOffer()));
//    c = new Chunk(offers.get(9).getOffer());
//	c.setAnchor(offers.get(9).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
//    
//    c1 = new PdfPCell(new Phrase(offers.get(10).getOffer()));
//    c = new Chunk(offers.get(10).getOffer());
//	c.setAnchor(offers.get(10).getSite());
//	c1.addElement(c);
//    table.addCell(c1);
  }
  
}

