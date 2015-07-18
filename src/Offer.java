
public class Offer {
	private String supplier;
	private String ownSupplier;
	private float minPrice;
	private float ownPrice;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getOwnSupplier() {
		return ownSupplier;
	}
	public void setOwnSupplier(String ownSupplier) {
		this.ownSupplier = ownSupplier;
	}
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	public float getOwnPrice() {
		return ownPrice;
	}
	public void setOwnPrice(float ownPrice) {
		this.ownPrice = ownPrice;
	}
	public Offer(String supplier, String ownSupplier, float minPrice,
			float ownPrice) {
		super();
		this.supplier = supplier;
		this.ownSupplier = ownSupplier;
		this.minPrice = minPrice;
		this.ownPrice = ownPrice;
	}
	
	public Offer(float ownPrice, String supplier) {
		super();
		this.supplier = supplier;
		this.ownSupplier = "";
		this.minPrice = 0;
		this.ownPrice = ownPrice;
	}
	@Override
	public String toString() {
		return "supplier " + supplier + " " + ownPrice;
	}
	
	

}
