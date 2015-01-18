/*the Wine class holds information about the transaction that 
 * is input by the user in the GUI. Cool? */

public class Wine {
	private String wineName;
	private double winePrice;
	private int wineQuant;

	//constructor
	public Wine(String name, double price, int quant) {
		wineName = name.trim();
		winePrice = price;
		wineQuant = quant;
	}

	//accessor methods
	public String getWineName() {
		return wineName;
	}

	public double getWinePrice() {
		return winePrice;
	}

	public int getWineQuant() {
		return wineQuant;
	}
}
