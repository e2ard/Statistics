import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sites {
	private String siteName, pattern;
	private String fileName = "E:\\eclipse\\Users\\Edvard\\workspace\\Statistics\\src\\site";
	public ArrayList<String> sNames = new ArrayList<String>();
	private String[] sClasses = {"economy", "compact", "intermediate", "standard", "full_size", "carriers_9"};
	
	public Sites() {
		super();
		setSiteName();
		setDate(2);
		setCategories();
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName() {
			String sCurrentLine = null;
			
			FileReader fr = getFileReader(fileName);
			if(fr != null){
				try{
					
					BufferedReader br = new BufferedReader(fr);
					sCurrentLine = br.readLine();
//					System.out.println(sCurrentLine);
					pattern = this.siteName = sCurrentLine;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else{
				System.out.println("-->FILE READER NULL");
			}
	}
		
	public ArrayList<String> getSiteNames(){
		System.out.println("--> Number of sites found" + sNames.size());
		
		return sNames;
	}
	
	public void setDate(int addDay){
		String pdx = "1";//from
		String ddx = "2";//to
		String pmx = "8";//mounth from
		String dmx = "8";//mounth to
		
		siteName = siteName.replace("doDay=2", "doDay=" + Integer.toString(addDay));
		System.out.println("--> Changed from:2" + "to  " + addDay);
	}
	public void setCategories(){
		sNames = new ArrayList<String>();
		for(String str : sClasses){
			String newSite = siteName.replace("economy", str);
			sNames.add(newSite);
			newSite = newSite.replace("Manual", "Automatic");
			sNames.add(newSite);
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
