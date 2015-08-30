package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Source.Main;
import Source.StatisticGenerator;

public class Buttons {
	JButton run;
	JButton cancel;
	public Buttons(){
		
	}
	
	public JButton run(){
		run = new JButton();
		run.setLocation(300,417);
		run.setSize(100,30);
		run.setText("Run");
		run.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent evt) {
		    	System.out.println("Run pressed");
//		    	System.out.println("Values puDay " + Main.puDay + " doDay" + Main.doDay + "puMonth" + Main.puMonth + " " + Main.doMonth) ;
//		    	System.out.println("city" + Main.cityName);
//		    	
	    	  	String site = 
	    			  "http://www.rentalcars.com/SearchResults.do?dropCity=Vilnius&doMinute=0&cor=lt&location=14159&driversAge=25&exSuppliers=&doHour=10&locationName=Vilnius+Airport&city=Vilnius&page=SearchResults.do&puHour=10&preflang=en&dropCountry=Lithuania&affiliateCode=airbaltic&puDay=26&dropLocation=14159&doDay=27&dropLocationName=Vilnius+Airport&country=Lithuania&filterTransmission=Automatic&filterAdditionalInfo=&advSearch=&puMonth=8&puMinute=0&doMonth=8&doYear=2015&puYear=2015&filter_carclass=economy";		
			
				for(int i = 2; i < 5; i++){
//					StatisticGenerator.getPdfFast(site, Main.daysPerDoc);
				}
		      }
		});
		return run;
	}
	
	public JButton cancel(){
		cancel = new JButton();
		cancel.setLocation(420,417);
		cancel.setSize(100,30);
		cancel.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent evt) {
		           System.exit(0);
		      }
		});
		cancel.setText("Cancel");
		return cancel;
	}
}
