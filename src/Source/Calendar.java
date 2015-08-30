package Source;

public class Calendar {
	private Integer puDay, doDay;
	private Integer puMonth, doMonth;
	private Integer puYear, doYear;
	private static Calendar instance = null;
	
	protected Calendar() {
		super();
		
	}
	
	public static Calendar getIntance(){
		
		if(instance == null) {
	         instance = new Calendar();
	      }
	      return instance;
	}
	
	public Integer getPuDay() {
		return puDay;
	}
	
	public void setPuDay(Integer nowDay) {
		this.puDay = nowDay;
		this.doDay = nowDay;
	}
	
	public Integer getPuMonth() {
		return puMonth;
	}
	
	public void setPuMonth(Integer nowMonth) {
		this.puMonth = nowMonth;
		this.doMonth = nowMonth;
	}
	
	public Integer getPuYear() {
		return puYear;
	}
	
	public void setPuYear(Integer nowYear) {
		this.puYear = nowYear;
		this.doYear = nowYear;
	}
	
	public Integer getDoDay() {
		return doDay;
	}

	public void setDoDay(Integer doDay) {
		this.doDay = doDay;
	}

	public Integer getDoMonth() {
		return doMonth;
	}

	public void setDoMonth(Integer doMonth) {
		this.doMonth = doMonth;
	}

	public Integer getDoYear() {
		return doYear;
	}

	public void setDoYear(Integer doYear) {
		this.doYear = doYear;
	}
	
	public void incDoDay(){
		
		if(doDay < 28){
			doDay++;
		}else{
			if((doDay == 30 && (doMonth == 4 || doMonth == 6 || doMonth == 9 || doMonth == 11))||(doDay == 28 && doMonth == 2)){
				doDay = 1;
				doMonth++;
			}else if(doDay == 31 && (doMonth == 1 || doMonth == 3 || doMonth == 5 || doMonth == 7 || doMonth == 8 || doMonth == 10 || doMonth == 12)){
				doDay = 1;
				doMonth++;
			}else {
				doDay++;
			}
		}
		if(doMonth > 12){
			doMonth = 1;
			doYear++;
		}
	}
	
public void incPuDay(){
		
		if(puDay < 28){
			puDay++;
		}else{
			if((puDay == 30 && (puMonth == 4 || puMonth == 6 || puMonth == 9 || puMonth == 11))||(puDay == 28 && puMonth == 2)){
				puDay = 1;
				puMonth++;
			}else if(puDay == 31 && (puMonth == 1 || puMonth == 3 || puMonth == 5 || puMonth == 7 || puMonth == 8 || puMonth == 10 || puMonth == 12)){
				puDay = 1;
				puMonth++;
			}else {
				puDay++;
			}
		}
		if(puMonth > 12){
			puMonth = 1;
			puYear++;
		}
	}
	
	public void reset(){
		doDay = puDay;
		doMonth = puMonth;
		doYear = puYear;
	}
};
