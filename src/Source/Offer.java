package Source;

public class Offer {
	private String supplier;
	private float price;
	private String GMsupplier;
	private float GMprice;
	private String site;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier1) {
		if(supplier1 != null)
			supplier = supplier1;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float minPrice) {
			price = minPrice;
	}
	
	public String getGMSupplier() {
		return GMsupplier;
	}
	public void setGMSupplier(String supplier) {
		GMsupplier = supplier;
	}
	
	public float getGMPrice() {
		return GMprice;
	}
	
	public void setGMPrice(float minPrice) {
		GMprice = minPrice;
	}
	
	public void setSite(String siteName) {
		site = siteName;
	}
	
	public String getSite() {
//		if(!(this.site == null))
		{
			return this.site;
//		}else{
//			return "--> getSite Nera";
		}
	}
	
	public Offer(float ownPrice, String supplier) {
		super();
		this.supplier = supplier;
		this.price = ownPrice;
	}
	public Offer(){};
	
	@Override
	public String toString() {
		if(!(supplier == null)){
			return supplier + " " + price + "\n";
		}else{
			return "--> toString Nera";
		}
	}
	public String getOffer(){
		if(price < GMprice){
			return validate(supplier, price) + "\n" + validate(GMsupplier, GMprice);
		}else{
			return validate(GMsupplier, GMprice) + "\n" + validate(supplier, price);
		}
	}
	private String validate(String s, float f){
		int strLen = 0;
		if(s != null){
			if(s.length() > 2)
				strLen = 3;
			else
				strLen = 2;
			return Math.round(f*100.0f)/100.0f + " " + s.substring(0, strLen);
		}else{
			return "";
		}
		
	}
	
	

}
