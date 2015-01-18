/*the CustomerAccount class holds information about
 * the customer and contains methods for the calculations
 * involved in processing sales and returns */

public class CustomerAccount {
	private int currentBalance;
	private String customerName;
	private static final double SERV_CHARGE = 0.8; // 20% service charge
	private static final double PENCE_PER_POUND = 100.0;

	/*CustomerAccount constructor. 
	 * n.b. Math.round() is needed here to account for possible rounding 
	 * errors due to the use of doubles (e.g. without this an initial 
	 * input balance of 17.49 would be displayed as 17.48) */
	public CustomerAccount(double bal, String name) {
		currentBalance = (int) Math.round(bal * 100);
		customerName = name.trim();
	}

	//accessor methods
	public double getBalance() {
		return currentBalance / PENCE_PER_POUND;
	}

	public String getName() {
		return customerName;
	}

	/*Called in LWMGUI when sale button is pressed.
	 * n.b. Math.round() needed here to account for possible rounding 
	 * errors due to the use of doubles. */
	public double processSale(int numBottles, double costBottle) {
		double transAmount = numBottles * costBottle;
		currentBalance += (int) Math.round(transAmount * 100); //updates current balance
		return transAmount;		// returns amount of transaction
	}

	/*Called in LWMGUI when return button is pressed.
	 * n.b. Math.round() needed here to account for possible rounding 
	 * errors due to the use of doubles (as well as from multiplying by 
	 * SERV_CHARGE - as this calculation may give a result with
	 * 3 decimal places). */
	public double processReturn(int numBottles, double costBottle) {
		double transAmount = numBottles * costBottle * SERV_CHARGE;
		currentBalance -= (int) Math.round(transAmount * 100); //updates current balance
		return transAmount;		//returns amount of transaction
	}
}
