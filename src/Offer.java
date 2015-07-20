
public class Offer {
	private String supplier;
	private float price;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float minPrice) {
		this.price = minPrice;
	}
	
	public Offer(float ownPrice, String supplier) {
		super();
		this.supplier = supplier;
		this.price = ownPrice;
	}
	@Override
	public String toString() {
		return supplier + " " + price;
	}
	
	

}
