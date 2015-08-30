package GUI;

import javax.swing.JTextArea;

public class TextAreas {
	JTextArea titleField;
	JTextArea city;
	JTextArea dayFrom;
	JTextArea dayTo;
	JTextArea monthFrom;
	JTextArea monthTo;
	JTextArea dayNum;

	public TextAreas(){
		
	}
	public JTextArea title(){
		titleField = new JTextArea();
		titleField.setLocation(11,7);
		titleField.setSize(525,76);
		titleField.setText("Rental car price comparison system");
		titleField.setRows(5);
		titleField.setColumns(5);
		return titleField;
	}
	 
	public JTextArea city(){
		city = new JTextArea();
		city.setLocation(347,96);
		city.setSize(79,20);
		city.setText("City ");
		city.setRows(5);
		city.setColumns(5);
		return city;
	}
	
	public JTextArea dayTo(){
		dayTo = new JTextArea();
		dayTo.setLocation(9,134);
		dayTo.setSize(65,20);
		dayTo.setText(" Day to");
		dayTo.setRows(5);
		dayTo.setColumns(5);
		return dayTo;
	}
	
	public JTextArea dayFrom(){
		dayFrom = new JTextArea();
		dayFrom.setLocation(9,96);
		dayFrom.setSize(65,20);
		dayFrom.setText(" Day from");
		dayFrom.setRows(5);
		dayFrom.setColumns(5);
		return dayFrom;
	}
	
	public JTextArea monthFrom(){
		monthFrom = new JTextArea();
		monthFrom.setLocation(144,96);
		monthFrom.setSize(70,20);
		monthFrom.setText("Month from");
		monthFrom.setRows(5);
		monthFrom.setColumns(5);
		return monthFrom;
	}
	
	public JTextArea monthTo(){
		monthTo = new JTextArea();
		monthTo.setLocation(144,134);
		monthTo.setSize(70,20);
		monthTo.setText("Month from");
		monthTo.setRows(5);
		monthTo.setColumns(5);
		return monthTo;
	}
	
	public JTextArea dayNum(){
		dayNum = new JTextArea();
		dayNum.setLocation(11,191);
		dayNum.setSize(178,20);
		dayNum.setText("Number of days per document");
		dayNum.setRows(5);
		dayNum.setColumns(5);
		return dayNum;
	}
	
	
	
}
