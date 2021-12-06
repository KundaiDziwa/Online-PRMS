package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Database.Landlord;
import Database.Property;

public class RegisterPropertyForm implements ActionListener {
	JFrame frame = new JFrame();
	private static JButton submitButton;
	private static JLabel addressLabel;
	private static JTextField addressText;
	private static JLabel houseTypeLabel;
	private static JComboBox houseTypeComboBox;
	private static JLabel bathLabel;
	private static JTextField bathText;
	private static JLabel bedLabel;
	private static JTextField bedText;
	private static JLabel furnishLabel;
	private static JComboBox furnishComboBox;
	private static JLabel quadrantLabel;
	private static JComboBox quadrantComboBox;
	private static JLabel priceLabel;
	private static JTextField priceText;

	private static Landlord landlord;

	RegisterPropertyForm(int id) throws SQLException {
		landlord = new Landlord(id);

		addressLabel = new JLabel("Address"); // label that goes beside textbox to tell user what to enter
		addressLabel.setBounds(30, 20, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(addressLabel);

		addressText = new JTextField(20); // creating box that lets user enter chars that takes in length argument
		addressText.setBounds(100, 20, 165, 25); // setting bounds (x,y,width,height)
		addressText.addActionListener(this);
		frame.add(addressText);

				houseTypeLabel = new JLabel("House Type"); // label that goes beside textbox to tell user what to enter
		houseTypeLabel.setBounds(30, 60, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(houseTypeLabel);

		String[] houseType = { "Apartment", "Bungalow" , "Condo" , "Duplex", "Townhouse"};
		houseTypeComboBox = new JComboBox(houseType);
		houseTypeComboBox.setBounds(120, 60, 200, 25);
		houseTypeComboBox.addActionListener(this);
		frame.add(houseTypeComboBox);



		bathLabel = new JLabel("Number of Bathroom(s)"); // label that goes beside textbox to tell user what to enter
		bathLabel.setBounds(30, 100, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(bathLabel);

		bathText = new JTextField(20); // creating box that lets user enter chars that takes in length argument
		bathText.setBounds(180, 100, 50, 25); // setting bounds (x,y,width,height)
		bathText.addActionListener(this);
		frame.add(bathText);

		bedLabel = new JLabel("Number of Bedroom(s)"); // label that goes beside textbox to tell user what to enter
		bedLabel.setBounds(30, 140, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(bedLabel);

		bedText = new JTextField(20); // creating box that lets user enter chars that takes in length argument
		bedText.setBounds(180, 140, 50, 25); // setting bounds (x,y,width,height)
		bedText.addActionListener(this);
		frame.add(bedText);

			furnishLabel = new JLabel("Furnished State"); // label that goes beside textbox to tell user what to enter
		furnishLabel.setBounds(30, 180, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(furnishLabel);

		String[] furnish = { "Furnished", "Unfurnised" };
		furnishComboBox = new JComboBox(furnish);
		furnishComboBox.setBounds(160, 180, 150, 25);
		furnishComboBox.addActionListener(this);
		frame.add(furnishComboBox);


		quadrantLabel = new JLabel("City Quadrant"); // label that goes beside textbox to tell user what to enter
		quadrantLabel.setBounds(30, 220, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(quadrantLabel);

		String[] quadrant = { "NE", "NW", "SE", "SW" };
		quadrantComboBox = new JComboBox(quadrant);
		quadrantComboBox.setBounds(160, 220, 80, 25);
		quadrantComboBox.addActionListener(this);
		frame.add(quadrantComboBox);

				priceLabel = new JLabel("Price (per month)"); // label that goes beside textbox to tell user what to enter
		priceLabel.setBounds(30, 260, 150, 20); // where the label will go on the panel (x,y,width,height)
		frame.add(priceLabel);

		priceText = new JTextField(20); // creating box that lets user enter chars that takes in length argument
		priceText.setBounds(150, 260, 165, 25); // setting bounds (x,y,width,height)
		priceText.addActionListener(this);
		frame.add(priceText);

		submitButton = new JButton("Submit");
		submitButton.setBounds(175, 300, 150, 30);
		submitButton.setFocusable(false);
		submitButton.addActionListener(this);
		frame.add(submitButton);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exiting window will close window
		frame.setSize(400, 400); // setting size of window
		frame.setLayout(null); // no layout
		frame.setTitle("Rental Property Management System");
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == submitButton) {
			String address = addressText.getText();
			String ht = houseTypeComboBox.getSelectedItem().toString();
			int numBath = Integer.parseInt(bathText.getText());
			int numRoom = Integer.parseInt(bedText.getText());
			String furnish = furnishComboBox.getSelectedItem().toString();
			String quadrant = quadrantComboBox.getSelectedItem().toString();
			Double price = Double.parseDouble(priceText.getText());
			
			Property newProp = new Property(address, ht, numBath, numRoom, furnish, quadrant, price);
//			int check = newProp.check();

			// if (check) {
				try {
					landlord.registerProperty(newProp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				int answer = JOptionPane.showOptionDialog(null, "Pay Property Fee for this New Property?", "Pay Property Fee",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
				if(answer == 0) {
					// yes answer changes listing to active
					newProp.setSOL("Active");
					System.out.println(answer);

				}else if(answer == 1){
					// no answer change listing to cancelled
					newProp.setSOL("Cancelled");
					System.out.println(answer);
				}
				
				landlord.changeSOL(newProp);
			} 

		// }

	}
}