package GUI;


//Code Genarated by JGuiD
import java.awt.event.*;

import javax.swing.*;

import Source.Calendar;
import Source.Main;
import Source.Sites;
import Source.StatisticGenerator;

public class GuiDMain extends JFrame
	{
	private static final long serialVersionUID = -2082171821733818720L;
	
	private JComboBox<?> dayToList;
	private JComboBox<?> dayFromList;
	private JComboBox<?> monthFromList;
	private JComboBox<?> daysToCountList;
	private JComboBox<?> daysNumList;
	private JComboBox<?> cityList;
	JTextArea titleField;
	JTextArea city;
	JTextArea dayFrom;
	JTextArea dayTo;
	JTextArea monthFrom;
	JTextArea daysToCount;
	JTextArea dayNum;

	JButton run;
	JButton cancel;
	
	protected int dayFromNum;
	protected int dayToNum;
	protected int puMonthNum;
	protected int daysToCountNum;
	protected String cityName;
	protected int daysNum;
    
	String months[] = {"1"," 2"," 3"," 4"," 5"," 6"," 7"," 8"," 9"," 10"," 11"," 12"};
	String cityListStr[] = {"Vilnius", "Riga"};
	
   public GuiDMain()
   {
     getContentPane().setLayout(null);
     setupGUI();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   @SuppressWarnings({ "unchecked", "rawtypes" })
void setupGUI()
   {
 	

	titleField = new JTextArea();
	titleField.setLocation(11,7);
	titleField.setSize(525,76);
	titleField.setText("Rental car analytic system");
	titleField.setRows(5);
	titleField.setColumns(5);
	getContentPane().add(titleField);

	cityList = new JComboBox(cityListStr);
	cityList.setLocation(435,96);
	cityList.setSize(100,20);
	cityList.setEditable(false );
//	cityList.setEnabled(false);
	cityList.setSelectedItem(null);
	cityList.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	        System.out.println(cityList.getSelectedItem().toString());
	        cityName = cityList.getSelectedItem().toString();
	        setCityName(cityName);
	    }
	});
	getContentPane().add(cityList);

	dayFrom = new JTextArea();
	dayFrom.setLocation(9,96);
	dayFrom.setSize(65,20);
	dayFrom.setText(" Day from");
	dayFrom.setRows(5);
	dayFrom.setColumns(5);
	getContentPane().add(dayFrom);

	dayFromList = new JComboBox(Sites.daysStr);
	dayFromList.setLocation(85,96);
	dayFromList.setSize(50,20);
	dayFromList.setSelectedItem(Sites.daysStr[0]);
	dayFromList.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	dayFromNum = dayFromList.getSelectedIndex() + 1;
	    	System.out.println(dayFromNum);
	    	setDayFromNum(dayFromNum);
	    }
	});
	dayFromList.setEditable(false);
	getContentPane().add(dayFromList);

//	dayTo = new JTextArea();
//	dayTo.setLocation(9,134);
//	dayTo.setSize(65,20);
//	dayTo.setText(" Days to count");
//	dayTo.setRows(5);
//	dayTo.setColumns(5);
//	getContentPane().add(dayTo);

//	dayToList =new JComboBox(Sites.daysStr);
//	dayToList.setLocation(85,134);
//	dayToList.setSize(50,20);
//	dayToList.setEditable(false);
//	dayToList.setSelectedItem(Sites.daysStr[1]);
//	dayToList.addActionListener (new ActionListener () {
//	    public void actionPerformed(ActionEvent e) {
//	    	dayToNum = dayToList.getSelectedIndex() + 1;
//	    	System.out.println(dayToNum);
//	    	setDayToNum(dayToNum);
//	    }
//	});
//    getContentPane().add(dayToList);
//
	
	monthFromList = new JComboBox(months);
	monthFromList.setLocation(224,96);
	monthFromList.setSize(50,20);
	monthFromList.setEditable(false );
	monthFromList.setSelectedItem(months[8]);
	monthFromList.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	puMonthNum = monthFromList.getSelectedIndex() + 1;
	    	System.out.println(puMonthNum);
	    	setPuMonth(puMonthNum);
	    }
	});
    getContentPane().add(monthFromList);

	daysToCount = new JTextArea();
	daysToCount.setLocation(100,134);
	daysToCount.setSize(115,20);
	daysToCount.setText("Days to count");
	daysToCount.setRows(5);
	daysToCount.setColumns(5);
	getContentPane().add(daysToCount);
	
	monthFrom = new JTextArea();
	monthFrom.setLocation(144,96);
	monthFrom.setSize(70,20);
	monthFrom.setText("Month from");
	monthFrom.setRows(5);
	monthFrom.setColumns(5);
	getContentPane().add(monthFrom);

	daysToCountList = new JComboBox(Sites.daysStr);
	daysToCountList.setLocation(224,134);
	daysToCountList.setSize(50,20);
	daysToCountList.setEditable(false );
	daysToCountList.setSelectedItem(months[0]);
	daysToCountList.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	        System.out.println(daysToCountList.getSelectedIndex() + 1 );
	        daysToCountNum = daysToCountList.getSelectedIndex() + 1;
	        setDaysNum(daysToCountNum);
	    }
	});
	getContentPane().add(daysToCountList);

//	daysNumList = jComboBox;
//	daysNumList.setLocation(198,191);
//	daysNumList.setSize(50,20);
//	daysNumList.setEditable(false );
//	daysNumList.setSelectedItem(Sites.daysStr[5]);
//	
//	daysNumList.addActionListener (new ActionListener () {
//	    public void actionPerformed(ActionEvent e) {
//	       System.out.println(daysNumList.getSelectedIndex() + 1 );
//	       setDayNum(daysNumList.getSelectedIndex() + 1);
//	    }
//	});
//	getContentPane().add(daysNumList);

	city = new JTextArea();
	city.setLocation(347,96);
	city.setSize(79,20);
	city.setText("City ");
	city.setRows(5);
	city.setColumns(5);
	getContentPane().add(city);
	
//	dayNum = new JTextArea();
//	dayNum.setLocation(11,191);
//	dayNum.setSize(178,20);
//	dayNum.setText("Number of days per document");
//	dayNum.setRows(5);
//	dayNum.setColumns(5);
//	getContentPane().add(dayNum);

	run = new JButton();
	run.setLocation(300,417);
	run.setSize(100,30);
	run.setText("Run");
	run.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
	    	System.out.println("Run pressed");
	    	
	    	System.out.println("Values puDay " + dayFromNum + " doDay" + dayToNum + "puMonth" + puMonthNum + " " + daysToCountNum) ;
	    	System.out.println("daysNum" + daysNum);
	    	
			String rentalRiga = "http://www.rentalcars.com/SearchResults.do?dropCity=Ryga&doMinute=0&location=1373298&driversAge=25&exSuppliers=&doHour=10&filterName=CarCategorisationSupplierFilter&searchType=&locationName=Ryga+Oro+Uostas&doFiltering=&doMonthYear=8-2015&puSameAsDo=on&city=Ryga&puMonthYear=8-2015&chinese-license=on&tj_pe_exp=t%3D1440097272269.e%3D24031-B%40hash%401440230056249%2C24286-A%40hash%401440097276086&puHour=10&dropCountry=Latvija&puDay=03&filterTo=1000&dropLocation=1373298&doDay=04&dropLocationName=Ryga+Oro+Uostas&enabler=&country=Latvija&filter_carclass=economy&advSearch=&filterAdditionalInfo=&filterFrom=0&puMonth=10&puMinute=0&doMonth=10&doYear=2015&puYear=2015&filterTransmission=Automatic";
			String rentalVilnius = "http://www.rentalcars.com/SearchResults.do;jsessionid=DE2574FFEFE06FF009AF4FAAA2368A0E.node226a?&dropCity=Vilnius&doMinute=0&cor=fr&location=14159&driversAge=25&exSuppliers=&doHour=10&locationName=Vilnius+Airport&city=Vilnius&page=SearchResults.do&puHour=10&preflang=en&dropCountry=Lithuania&affiliateCode=airbaltic&puDay=12&dropLocation=14159&doDay=2&dropLocationName=Vilnius+Airport&country=Lithuania&filter_carclass=economy&filterAdditionalInfo=&advSearch=&puMonth=9&puMinute=0&doMonth=9&doYear=2015&puYear=2015&filterTransmission=Manual";
			String rental;
			if(cityName.contains("Vilnius")){
				rental = rentalVilnius;
			}else{
				rental = rentalRiga;
			}
			rental = "http://www.rentalcars.com/SearchResults.do;jsessionid=DE2574FFEFE06FF009AF4FAAA2368A0E.node226a?&dropCity=Vilnius&doMinute=0&cor=fr&location=14159&driversAge=25&exSuppliers=&doHour=10&locationName=Vilnius+Airport&city=Vilnius&page=SearchResults.do&puHour=10&preflang=en&dropCountry=Lithuania&affiliateCode=airbaltic&puDay=12&dropLocation=14159&doDay=2&dropLocationName=Vilnius+Airport&country=Lithuania&filter_carclass=economy&filterAdditionalInfo=&advSearch=&puMonth=9&puMinute=0&doMonth=9&doYear=2015&puYear=2015&filterTransmission=Manual";
			
			if(isZero(daysToCountNum) && isZero(dayFromNum) && isZero(puMonthNum) && cityName!=null){
				StatisticGenerator sr = new StatisticGenerator();
				
				Calendar cal = Calendar.getIntance();
				cal.setPuDay(dayFromNum);
				cal.setPuMonth(puMonthNum);
				cal.setPuYear(2015);
				
				JOptionPane.showMessageDialog(null, "Skaiciuojama, palaukite");
				
				sr.getPdfFast(rental, daysToCountNum);
				
			}else{
				JOptionPane.showMessageDialog(null, "Uzpildykite visas reiksmes");
				System.out.println("Uzpildykite reiksmes");
			}
			
		}
	});
	
	getContentPane().add(run);

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
	getContentPane().add(cancel);
	setTitle("Price comparison");
	setSize(556,502);
	setVisible(true);
	setResizable(true);
	
	
   }
	
//	void setupGUI()
//	{	
//		monthToList = new JComboBox(Sites.daysStr);
//		monthToList.setLocation(224,134);
//		monthToList.setSize(50,20);
//		monthToList.setEditable(false );
//		monthToList.addActionListener (new ActionListener () {
//			
//			
//			public void actionPerformed(ActionEvent e) {
//		        System.out.println(monthToList.getSelectedIndex() + 1 );
//		        monthTo = monthToList.getSelectedIndex() + 1;
//		    }
//		});
//		Main.puMonth = monthTo;
//   	
//		ComboBoxes cb = new ComboBoxes();
//		getContentPane().add(cb.dayFromList());
//		getContentPane().add(cb.dayToList());
//		getContentPane().add(cb.monthToList());
//		getContentPane().add(cb.monthFromList());
//		getContentPane().add(cb.daysNumList());
//		getContentPane().add(cb.cityList());
//		
//		TextAreas ta = new TextAreas();
//		getContentPane().add(ta.title());
//		getContentPane().add(ta.monthFrom());
//		getContentPane().add(ta.monthTo());
//		getContentPane().add(ta.dayNum());
//		getContentPane().add(ta.dayFrom());
//		getContentPane().add(ta.dayTo());
//		getContentPane().add(ta.dayTo());
//		getContentPane().add(ta.city());
//		
//		Buttons but = new Buttons();
//		getContentPane().add(but.run());
//		getContentPane().add(but.cancel());
//		
//		setTitle("Price Comparison");
//		setSize(556,502);
//		setVisible(true);
//		setResizable(true);
//		
//	}
//	public ComboBoxes getComboBoxes(){
//		return ;
//	}
   	public void setDayNum(int num){
   		daysNum = num;
   	}
	private void setDayFromNum(int dayFromNum) {
		this.dayFromNum = dayFromNum;
	}
	private void setDayToNum(int dayToNum) {
		this.dayToNum = dayToNum;
	}
	private void setPuMonth(int monthFromNum) {
		this.puMonthNum = monthFromNum;
	}
	private void setDaysNum(int monthToNum) {
		this.daysToCountNum = monthToNum;
	}
	private void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	private boolean isZero(int num){
		return num > 0?true:false;
	}
	public static void main( String args[] ) {
	  
	  new GuiDMain();
	}
	
};  
