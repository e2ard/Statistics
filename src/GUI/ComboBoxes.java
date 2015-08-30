package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import Source.Sites;
import Source.Main;

public class ComboBoxes {
	private JComboBox dayToList;
	private JComboBox dayFromList;
	private JComboBox monthFromList;
	private JComboBox monthToList;
	private JComboBox daysNumList;
	private JComboBox cityList;
	
	
	
	
	protected int dayFrom;
	protected int dayTo;
	protected int monthFrom;
	protected int monthTo;
	protected String city;
	protected int daysNum;
	
	public ComboBoxes(){
		
	}
	
	public JComboBox dayFromList(){
		dayFromList = new JComboBox(Sites.daysStr);
		dayFromList.setLocation(85,96);
		dayFromList.setSize(50,20);
		dayFromList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(dayFromList.getSelectedIndex() + 1 );
		        dayFrom = dayFromList.getSelectedIndex() + 1;
		    }
		});
		dayFromList.setEditable(false);
		return dayFromList;
	}
	
	public JComboBox dayToList(){
		dayToList = new JComboBox(Sites.daysStr);
		dayToList.setLocation(85,134);
		dayToList.setSize(50,20);
		dayToList.setEditable(false);
		dayToList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(dayToList.getSelectedIndex() + 1 );
		        dayTo = dayToList.getSelectedIndex() + 1;
		    }
		});
	   return dayToList;
	}
	
	public JComboBox monthFromList(){
//		monthFromList = new JComboBox(months);
		monthFromList.setLocation(224,96);
		monthFromList.setSize(50,20);
		monthFromList.setEditable(false );
		monthFromList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(monthFromList.getSelectedIndex() + 1 );
		        monthFrom = monthFromList.getSelectedIndex() + 1;
		    }
		});
	    return monthFromList;
	}
	
	public JComboBox monthToList(){
//		monthToList = new JComboBox(months);
		monthToList.setLocation(224,134);
		monthToList.setSize(50,20);
		monthToList.setEditable(false );
		monthToList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(monthToList.getSelectedIndex() + 1 );
		        monthTo = monthToList.getSelectedIndex() + 1;
		    }
		});
		return monthToList;
	}
	public JComboBox cityList(){
//		cityList = new JComboBox(cityListStr);
		cityList.setLocation(435,96);
		cityList.setSize(100,20);
		cityList.setEditable(false );
//		cityList.setEnabled(false);
		cityList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(cityList.getSelectedItem().toString());
		        city = cityList.getSelectedItem().toString();
		    }
		});
		return cityList;
	}
	public JComboBox daysNumList(){
		daysNumList = new JComboBox(Sites.daysStr);
		daysNumList.setLocation(198,191);
		daysNumList.setSize(50,20);
		daysNumList.setEditable(false );
		daysNumList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(daysNumList.getSelectedIndex() + 1 );
		        daysNum = daysNumList.getSelectedIndex() + 1;
		    }
		});
		return daysNumList;
	}
	
};
