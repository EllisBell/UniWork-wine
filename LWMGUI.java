//the LMWGUI class creates the GUI and handles user input

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LWMGUI extends JFrame implements ActionListener {

	private JPanel northPanel, southPanel, middlePanel, middleTop, 
	middleBottom;
	private JButton saleButton, returnButton;
	private JLabel wineNameLabel, quantLabel, priceLabel, winePurchasedLabel,
	transAmountLabel, balanceLabel;
	private JTextField wineNameField, quantField, priceField, 
	transAmountField, balanceField; 

	private CustomerAccount custAccount;

	//THIS IS CONSTRUCTOR AND LAYOUT STUFF:

	public LWMGUI(CustomerAccount cAccount) {
		custAccount = cAccount;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 175);
		setLocation(300, 300);
		//Get name from customer account object
		setTitle("Lilybank Wine Merchants: " + custAccount.getName()); 

		//Call methods to add and layout components on the content pane
		this.layoutNorth();
		this.layoutSouth();
		this.layoutMiddle();
	}

	private void layoutNorth() {
		//create north JPanel
		northPanel = new JPanel();
		northPanel.setBackground(Color.white);

		//create and add components to north JPanel
		wineNameLabel = new JLabel("Name:");
		northPanel.add(wineNameLabel);

		wineNameField = new JTextField(20);
		northPanel.add(wineNameField);

		quantLabel = new JLabel("Quantity:");
		northPanel.add(quantLabel);

		quantField = new JTextField(10);
		northPanel.add(quantField);

		priceLabel = new JLabel("Price: £");
		northPanel.add(priceLabel);

		priceField = new JTextField(10);
		northPanel.add(priceField);

		//now add the north panel to the content pane
		this.add(northPanel, BorderLayout.NORTH);
	}

	private void layoutSouth() {
		//create south JPanel
		southPanel = new JPanel();
		southPanel.setBackground(Color.white);

		//create and add components to south panel
		transAmountLabel = new JLabel("Amount of Transaction:");
		southPanel.add(transAmountLabel);

		transAmountField = new JTextField(10);
		southPanel.add(transAmountField);
		transAmountField.setEditable(false);

		balanceLabel = new JLabel("Current balance:");
		southPanel.add(balanceLabel);

		balanceField = new JTextField(10);
		southPanel.add(balanceField);
		balanceField.setEditable(false);
		// call method to display initial balance before any transaction
		displayBalance(); 				

		//add south panel to content pane
		this.add(southPanel, BorderLayout.SOUTH);
	}

	private void layoutMiddle() {
		//create middle JPanel
		middlePanel = new JPanel();
		middlePanel.setBackground(Color.white);
		BorderLayout b1 = new BorderLayout();
		middlePanel.setLayout(b1);

		//top section of middle JPanel
		middleTop = new JPanel();
		middleTop.setBackground(Color.white);

		saleButton = new JButton("Process Sale");
		middleTop.add(saleButton);
		//sale button needs to listen for events
		saleButton.addActionListener(this);

		returnButton = new JButton("Process Return");
		middleTop.add(returnButton);
		//return button needs to listen for events
		returnButton.addActionListener(this);

		middlePanel.add(middleTop, BorderLayout.NORTH);

		//bottom section of middle JPanel
		middleBottom = new JPanel();
		middleBottom.setBackground(Color.white);
		BorderLayout b2 = new BorderLayout();
		middleBottom.setLayout(b2);

		winePurchasedLabel = new JLabel("Wine purchased:");
		middleBottom.add(winePurchasedLabel, BorderLayout.SOUTH);

		middlePanel.add(middleBottom);		

		//add center panel to content pane
		this.add(middlePanel, BorderLayout.CENTER);		
	}


	//THIS IS EVENT HANDLER STUFF:	

	/* actionPerformed method first assigns to wineObj whatever is 
	 * returned by the makeWine method.
	 * makeWine() returns a Wine object if user has input valid data for a
	 * transaction. Otherwise, it returns null. 
	 * If wineObj is not null it is passed to the wineSale or wineReturn 
	 * methods as appropriate. Otherwise an error message is displayed. */

	public void actionPerformed(ActionEvent e) {
		// call makeWine method to create Wine object using input data
		Wine wineObj = makeWine(); 
		// check for existing wine object (which would mean valid data was input)		
		if(wineObj != null) {			
			//if data OK, check which button was pressed and call relevant method
			if(e.getSource() == saleButton) { 
				this.wineSale(wineObj);
			}
			else if(e.getSource() == returnButton) {
				this.wineReturn(wineObj);
			}
			displayBalance(); //called outside of if-else statement as this is common to both transactions
		}		
		/* if wineObj is null, it means input data was not valid; display 
		 * error message to user */
		else {
			JOptionPane.showMessageDialog(this, "Please enter valid data."
					+ "\nMake sure to enter the name of"
					+ " the wine,\na whole number for the quantity and a "
					+ "number for the price.", 
					"Come on now", JOptionPane.ERROR_MESSAGE);
		}
		clearText();  // clears the input text fields; called here as this should happen in every case
	}

	/*makeWine method retrieves input from GUI textfields and checks that 
	 * the data is valid.
	 * If data is valid method returns a Wine object passing such data as 
	 * parameters to the constructor. Otherwise, method returns null. */
	private Wine makeWine() {
		String n = wineNameField.getText();
		String priceString = priceField.getText();
		String quantString = quantField.getText();

		if(n.equals("")) { //check for empty string in wine name field
			return null; // if no name has been entered method returns null
		}

		/* then check for valid data (i.e. positive numerical values) from
		 *  quantity and price fields. */		
		try { //try-catch block used for possible number format exceptions thrown from parse		
			double p = Double.parseDouble(priceString);
			int q = Integer.parseInt(quantString);
			// check for negative values in price and quantity fields	
			if(p<0 || q<0) { 
				return null; // if a negative value has been entered method returns null
			}
			//if data OK return new Wine object passing data to constructor
			else {
				return new Wine(n, p, q); 
			}
		}
		//catches possible number format exceptions from parsing
		catch(NumberFormatException nfx) { 
			return null; //if NumberFormatException thrown method returns null
		}
	}

	/* wineSale method called if sale button is pressed. gets data from 
	 * Wine object and passes it to processSale method in CustomerAccount 
	 * used to update balance and return transaction amount. */
	private void wineSale(Wine wineObject) {
		int number = wineObject.getWineQuant();
		double cost = wineObject.getWinePrice();
		transAmountField.setText(String.format("£%.2f", 
				custAccount.processSale(number, cost))); //processSale() returns transaction amount
		winePurchasedLabel.setText("Wine purchased: " + wineObject.getWineName());
	}

	/*wineReturn method called if return button is pressed. Does the same 
	 * as wineSale but passes the data to processReturn method in
	 * CustomerAccount instead. */
	private void wineReturn(Wine wineObject) {
		int number = wineObject.getWineQuant();
		double cost = wineObject.getWinePrice();
		transAmountField.setText(String.format("£%.2f", 
				custAccount.processReturn(number, cost)));
		winePurchasedLabel.setText("Wine returned: " + wineObject.getWineName());
	}

	/* Displays balance stored in CustomerAccount, with the letters "CR" 
	 * if balance is negative.*/
	private void displayBalance() {
		double newBalance = custAccount.getBalance();
		if(newBalance<0) {
			balanceField.setText(String.format("£%.2f CR",  Math.abs(newBalance)));
		}
		else {
			balanceField.setText(String.format("£%.2f", newBalance));
		}
	}

	//clears the name, quantity and price textfields of the GUI
	private void clearText() {
		wineNameField.setText("");
		quantField.setText("");
		priceField.setText("");
	}
}