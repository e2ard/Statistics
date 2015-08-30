package Source;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sites {
	private String siteName, pattern;
	private String fileName = "site";
	public static String[] sPdfClasses = {"MDMR", "EDMR", "CDMR", "CDAR", "IDMR", "IDAR", "SDMR", "SDAR", "CJMR", "CJAR","SUV", "SUV A", "PVMR"};
	public ArrayList<String> sNames = new ArrayList<String>();//generated site list
	public static String[] sClasses = {"mini", "economy", "compact", "intermediate", "standard", "estate", "suvs", "carriers_9"};
	public static String[] sNorwegian = {"Economym", "Economya", "Compactm", "Compacta", "Intermediatem", "Intermediatea", "Standardm", "Standarda", "SUVm", "SUVa", "9-seat minivanm"};
	public static String[] sAirbaltic = {"Minim", "Economym", "Compactm", "Compacta", "Intermediatem", "Intermediatea", "Standardm", "Standarda", "Estatem", "Estatea", "SUVm", "SUVa", "9-seat minivanm"};
	public static String[] sAirbalticLt = {"Ekonominism", "Ekonominisa", "Kompaktinism", "Kompaktinisa", "Vidutinism", "Vidutinisa", "Standartinism", "Standartinisa", "Visureigism", "Visureigisa", "Mikroautobusasm"};
	public static String[] sScanner = {"Economym", "Economya", "Compactm", "Compacta", "Standard / intermediatem", "Standard / intermediatea", "Standardm", "Standarda", "SUVm", "SUVa", "9 seat people carrierm"};
	public static int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
	public static String[] daysStr = {"1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", " 10", " 11", " 12", " 13", " 14", " 15", " 16", " 17", " 18", " 19", " 20", " 21", " 22", " 23", " 24", " 25", " 26", " 27", " 28", " 29", " 30"};
	private Calendar cal;
	
	public ArrayList<String> sites = new ArrayList<String>();//red from file sites
	
	public Sites() {
		super();
		cal = Calendar.getIntance();
//		readSiteNames();
//		setCategories();
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
				System.out.println("-->FILE READER NULL readSiteNames");
			}
	}
	
	public String readFile(String params) {
		String sCurrentLine = null;
		
		FileReader fr = getFileReader(params);
		if(fr != null){
			try{
				String param = "";
				BufferedReader br = new BufferedReader(fr);
				while((sCurrentLine = br.readLine()) != null){
					param += sCurrentLine;
				}
				
//				System.out.println(sCurrentLine);
				return param;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			System.out.println("-->FILE READER NULL readFile");
		}
		return sCurrentLine;
		
}
		
	public ArrayList<String> getSiteNames(){
//		System.out.println("--> Number of sites found " + sNames.size());
		return sNames;
	}
	
	private String addZero(int num){
		if(num > 9){
			return "" + num;
		}else{
			return "0" + num;
		}
		
	}
	
	public String newDate(int year, int month, int day){
//		int month = cal.getPuMonth();
//		int year = cal.getPuYear();
//		int puDay = cal.getPuDay() + day;
//		
//		if (puDay > 31){
//			month++;
//			puDay = puDay % 31;
//		}
		//Norvegian Scanner
		String dateToChange = "returnDateTime=201(\\d)-\\d\\d-\\d\\d";
		String newDate = "returnDateTime=" + year + "-" + addZero(month) + "-" + addZero(day);
		this.siteName = this.siteName.replaceAll(dateToChange, newDate);
		
		//Rental Baltic
		this.siteName = this.siteName.replaceAll("doDay=[\\d]*", "doDay=" + addZero(day));
		this.siteName = this.siteName.replaceAll("doMonth=[\\d]*", "doMonth=" + addZero(month));
		System.out.println("--> Date Changed from:" + "to  " + day + "\n" + "NOW SITE IS: " + "\n" + this.siteName);
//		System.out.println(year + " " + month + " " + day);
		return this.siteName;
	}
	
	public String initDate(int year, int month, int day){
		
		/*Norvegian Scanner*/
		String dateToChange = "pickupDateTime=201(\\d)-\\d\\d-\\d\\d";
		String newDate = "pickupDateTime=" + year + "-" + addZero(month) + "-" + addZero(day);
		this.siteName = this.siteName.replaceAll(dateToChange, newDate);
		String dateToChange1 = "returnDateTime=201(\\d)-\\d\\d-\\d\\d";
		String newDate2 = "returnDateTime=" + year + "-" + addZero(month) + "-" + addZero(day);
		this.siteName = this.siteName.replaceAll(dateToChange1, newDate2);
		/*Rental Baltic 
		 * */
		this.siteName = this.siteName.replaceAll("puDay=(\\d)*", "puDay=" + addZero(day));
		this.siteName = this.siteName.replaceAll("puMonth=(\\d)*", "puMonth=" + addZero(month));
		System.out.println("--> Date Changed from:" + "to  " + day + "\n" + "initDateNOW SITE IS: " + "\n" + this.siteName);
		this.siteName = this.siteName.replaceAll("doDay=(\\d)*", "doDay=" + addZero(day));
		this.siteName = this.siteName.replaceAll("doMonth=(\\d)*", "doMonth=" + addZero(month));
		
//		System.out.println(year + " " + month + " " + day);
		return this.siteName;
	}
	
	public String setRiga(String site){
		/*
		*/
		String country = "&country=[\\w]+&";
		String newCountry = "&country=Latvia&";
		
		String dropCountry = "&dropCountry=[\\w]+&";
		String newDropCountry = "&dropCountry=Latvia&";
		
		String doLocName = "&dropLocationName=[\\w\\+]+&";
		String newDoLocationName = "&dropLocationName=Riga+Airport&";
		
		String locationName = "&locationName=[\\w\\+]+&";
		String newLocationName = "&locationName=Riga+Airport&";
		
		String doLocationName = "&dropLocation=\\d*&";
		String newDoLocName = "&dropLocation=1373298&";
		
		String location = "&location=[\\d]*&";
		String newLocation = "&location=1373298&";
		
		String dropCity = "\\?dropCity=[\\w^&]+&";
		String newDropCity = "\\?dropCity=Riga&";
		
		String city = "&city=[\\w]+&";
		String newCity = "&city=Riga&";
		
		
		site = site.replaceAll(country, newCountry);
		site = site.replaceAll(dropCountry, newDropCountry);
		site = site.replaceAll(doLocName, newDoLocName);
		site = site.replaceAll(doLocName, newDoLocationName);
		site = site.replaceAll(locationName, newLocationName);
		site = site.replaceAll(location, newLocation);
		site = site.replaceAll(dropCity, newDropCity);
		site = site.replaceAll(city, newCity);

		return site;
	}
	
	public void setCategories(){
		sNames = new ArrayList<String>();
		addEcoClass();
		addTransm();
		for(String str : sClasses){
			String newSite = siteName.replace("economy", str);
			sNames.add(newSite);
			//escape PVAR
			if(!str.toLowerCase().equals("carriers_9")&& !str.toLowerCase().equals("mini") 
					&&!str.toLowerCase().equals("economy")){
				newSite = newSite.replace("Manual", "Automatic");
				sNames.add(newSite);
			}
		}
	}
	
	public void addEcoClass(){
		String ecoClass = "&filter_carclass=economy";
		if(!siteName.contains(ecoClass)){
			siteName = siteName.concat(ecoClass);
		}
	}
	
	public void addTransm(){
		String transm = "&filterTransmission=Manual";
		if(!siteName.contains(transm)){
			siteName = siteName.concat(transm);
		}
	}
	
	public void resetSiteName(){
		siteName = pattern;
	}
	
	private FileReader getFileReader(String fileName){
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) {
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
};
