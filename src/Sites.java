import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sites {
	private String siteName, pattern;
	private String fileName = "site";
	public ArrayList<String> sNames = new ArrayList<String>();//generated site list
	public static String[] sClasses = {"economy", "compact", "intermediate", "standard", "suvs", "carriers_9"};
	public static String[] sNorwegian = {"Economym", "Economya", "Compactm", "Compacta", "Intermediatem", "Intermediatea", "Standardm", "Standarda", "SUVm", "SUVa", "9-seat minivanm"};
	public ArrayList<String> sites = new ArrayList<String>();//red from file sites
	
	public Sites() {
		super();
		readSiteNames();
		setCategories();
	}

	public String getSiteName() {
		return this.siteName;
	}
	
	public void setSiteName(String str){
		this.siteName = str;
		pattern = str;
	}
	
	public void readSiteNames() {
			String sCurrentLine = null;
			
			FileReader fr = getFileReader(fileName);
			if(fr != null){
				try{
					
					BufferedReader br = new BufferedReader(fr);
					while((sCurrentLine = br.readLine()) != null){
						sites.add(sCurrentLine);
					}
//					System.out.println(sCurrentLine);
					setSiteName(sites.get(0));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else{
				System.out.println("-->FILE READER NULL");
			}
	}
		
	public ArrayList<String> getSiteNames(){
		System.out.println("--> Number of sites found " + sNames.size());
		
		return sNames;
	}
	
	private String addZero(int num){
		if(num > 9){
			return "" + num;
		}else{
			return "0" + num;
		}
		
	}
	
	public String setDate(int year, int month, int day){

		System.out.println(year + " " + month + " " + day);
		//Norvegian
		String dateToChange = "returnDateTime=201(\\d)-\\d\\d-\\d\\d";
		String newDate = "returnDateTime=" + year + "-" + addZero(month) + "-" + addZero(day);
		this.siteName = this.siteName.replaceAll(dateToChange, newDate);
		//Rental Baltic
		this.siteName = this.siteName.replaceAll("doDay=(\\d)*", "doDay=" + addZero(day));
		this.siteName = this.siteName.replaceAll("doMonth=(\\d)*", "doMonth=" + addZero(month));
//		System.out.println("--> Date Changed from:2" + "to  " + doDay + "\n" + "NOW SITE IS: " + "\n" + this.siteName);
		
		return this.siteName;
	}
	
	public void setCategories(){
		sNames = new ArrayList<String>();
		for(String str : sClasses){
			String newSite = siteName.replace("economy", str);
			sNames.add(newSite);
			//escape PVAR
			if(!str.toLowerCase().equals("carriers_9")){
				newSite = newSite.replace("Manual", "Automatic");
				sNames.add(newSite);
			}
		}
		
	}
	
	public void resetSiteName(){
		siteName = pattern;
	}
	
	private FileReader getFileReader(String fileName){
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) {
//			System.out.println("File Found"); 
			try {
				return new FileReader(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("File not found");
		}
		return null;
	}
}
