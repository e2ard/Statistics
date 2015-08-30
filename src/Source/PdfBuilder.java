package Source;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfBuilder {
  private PdfPTable table;
  private Document document;
  private String documentTitle, city;
  private String FILE = "Pdf\\";
  private String puMonth, puDay;
  private Font font = FontFactory.getFont("Arial", 9, Font.NORMAL);
  private static int dayNum;
  private Calendar cal ;
private String suppliersStr;
  
  public PdfBuilder(String site){
	  dayNum = 1;
	  cal = Calendar.getIntance();
	  setPuMonthDay(site);
	  
	  setDocTitle(site);
	  
	  if(site.contains("Vilnius")){
		  	city = "Vilnius";
	  }else{
		  city = "Riga";
	  }
	  generatePdf(city);
  }
  
  public void setPuMonthDay(String site){
		String puDayStr = "puDay=(\\d)*";
		Pattern pattern = Pattern.compile(puDayStr);
		
		Matcher puDayMatcher = pattern.matcher(site);
		String puDay = null;
		//extract day
		if (puDayMatcher.find())
		{
			puDay = puDayMatcher.group(0).substring(6, puDayMatcher.group(0).length());
		}
		//extract month
		String puMonthStr = "puMonth=(\\d)*";
		pattern = Pattern.compile(puMonthStr);
		Matcher puMonthMatcher = pattern.matcher(site);
		String puMonth;
		if (puMonthMatcher.find())
		{
			puMonth = puMonthMatcher.group(0).substring(8, puMonthMatcher.group(0).length());
			
			this.puDay = puDay;
			this.puMonth = puMonth;
		}
	
  }
  
  public String setDocTitle(String site){
		String str = ".*(\\.).{3}\\/";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(site);
		if (matcher.find())
		{
			documentTitle = matcher.group(0);
			documentTitle = documentTitle.substring(0, documentTitle.length()-1);
			if (documentTitle.contains("rental")){
				documentTitle = "airbaltic ";
			}
			return documentTitle ;
		}
		return "unknown"; 
  }
  
  public String getSource(){
	  return documentTitle;
  }
	
  public void generatePdf(String city){
     try {
	      document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
	      document.top(20);
	      
	      PdfWriter.getInstance(document, new FileOutputStream(FILE + documentTitle + puMonth + "-" + puDay + city + ".pdf"));
	      document.open();
	      createTable();
     } catch (Exception e) {
	      e.printStackTrace();
     }
  }
  
  
  public void finishGenerating(){
	  try {
		document.add(table);
		Paragraph p = new Paragraph();
		p.add(new Phrase(suppliersStr));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public void saveDocument(){
	  document.close();
  }
  
  private void createTable() throws BadElementException {
    
	table = new PdfPTable(14);
    
	PdfPCell c1 = new PdfPCell(new Phrase( documentTitle  + puMonth + "-" + puDay + " "+ city));
    c1.setColspan(14);
    
    table.setPaddingTop(10);
    table.setWidthPercentage(97);
    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
    table.getDefaultCell().setColspan(14);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase());
    Chunk c = new Chunk("Data", font);
    c1.addElement(c);
    table.addCell(c1);
    
    for(String str : Sites.sPdfClasses){
    	c1 = new PdfPCell(new Phrase());
    	c = new Chunk(str, font);
    	c1.addElement(c);
    	table.addCell(c1);
	}
    table.setHeaderRows(2);
  }
  
  public void addOffersRow(ArrayList<Offer> offers, int day){
	  if (offers == null){
		  System.out.println("OFFERs NULL");
		  return;
	  }
	  
	  PdfPCell c1 = new PdfPCell(new Phrase());
	  
	  Chunk c = new Chunk(cal.getPuMonth() + "-" + cal.getPuDay() + "/" + cal.getDoDay() + "\n" + dayNum++);
	  c1.addElement(c);
	  table.addCell(c1);
	
	  for(int index = 0; index < offers.size(); index++){
			c1 = new PdfPCell(new Phrase());
			
			String offer = offers.get(index).getOffer();
			c = new Chunk(offer, font);
			c.setAnchor(offers.get(index).getSite());
			
			c1.addElement(c);
			table.addCell(c1);
	  }
  }
  public void setSuppliers(String suppliers){
	  this.suppliersStr = suppliers;
  }
};

