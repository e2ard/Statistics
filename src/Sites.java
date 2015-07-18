import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Sites {
	private String siteName;
	private String fileName = "E:\\eclipse\\Users\\Edvard\\workspace\\Statistics\\src\\site";
	
	public Sites() {
		super();
		setSiteName();
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
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.siteName = sCurrentLine;
			}else{
				System.out.println("-->FILE READER NULL");
			}
		
		
	}
	private FileReader getFileReader(String fileName){
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) {
			System.out.println("File Found"); 
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
