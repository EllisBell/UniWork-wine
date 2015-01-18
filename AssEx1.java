/*main method gets info from user through input
 * JOptionPanes then uses that data to create a
 * CustomerAccount object and subsequently a LWMGUI
 * object. */

import javax.swing.*;

public class AssEx1 {
	public static void main(String [] args) { 

		/* Get name from JOptionPane and check for null value (i.e. user 
		 * pressing cancel or close) or empty string - in these 
		 * cases exit program. */
		String custName = JOptionPane.showInputDialog(null, 
				"Please enter customer's name");
		if(custName == null || custName.isEmpty()) {
			System.exit(0);
		}

		/*Gets balance from JOptionPane. If non-numerical data is input, 
		 * number format exception is thrown and catch block displays error
		 * message. Put in infinite for loop so process is repeated until 
		 * user inputs valid data (or presses cancel or close).
		 * If data is OK creates CustomerAccount and LWMGUI objects */
		for(;;) {
			try {
				String balanceString = JOptionPane.showInputDialog(null, 
						"Please enter customer's current balance");
				if(balanceString == null) {
					System.exit(0); 		// program exits on cancel or close
				}
				//throws exception if input is non-numerical
				double curBalance = Double.parseDouble(balanceString);
				//if info from user is OK, create objects and break from loop
				CustomerAccount newCustAccount = new CustomerAccount(curBalance, 
						custName);
				LWMGUI newGui = new LWMGUI(newCustAccount);
				newGui.setVisible(true);
				break; 		
			}

			//catches exception if the user enters non-numerical data
			catch(NumberFormatException nfx) { 
				JOptionPane.showMessageDialog(null, 
						"Please enter customer's current balance."
								+ "\nHint: enter a number...", 
								"Come on now", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
