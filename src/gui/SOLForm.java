package gui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Database.Landlord;
import Database.Manager;
import Database.Property;

public class SOLForm implements ActionListener {
	JFrame frame = new JFrame();

	// title panel
	private static JPanel titlePanel;
	private static JLabel titleLabel;

	// button panel
	private static JPanel buttonPanel;
	private static JLabel idLabel;
	private static JTextField idText;
	private static JComboBox stateComboBox;
	private String[] states = { "Rented", "Cancelled", "Suspended" };
	private static JButton applyButton;
	private static JButton applyButton2;

	// display panel
	private static JPanel displayPanel;
	private static JTextArea display;
	private static JScrollPane scroll;

	ArrayList<Property> activeProperty;
	private int landlordID;



	// for manager
	SOLForm() {
		
		Manager myManager = new Manager();
		activeProperty = myManager.searchProperties("Active");	// has all of active properties
		// ********* TITLE ************
		titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 800, 50);

		// title label
		titleLabel = new JLabel("Change State of Lisitng");
		titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
		titlePanel.add(titleLabel);

		// ********* BUTTON PANEL ************
		buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 50, 800, 75);

		// label for property id
		idLabel = new JLabel("Property ID: ");
		idLabel.setFont(new Font("Courier", Font.PLAIN, 17));
		buttonPanel.add(idLabel);

		// user input text id 
		idText = new JTextField(10);
		buttonPanel.add(idText);
		buttonPanel.add(Box.createHorizontalStrut(60));

		// combobox for different states of listings
		stateComboBox = new JComboBox(states);
		buttonPanel.add(stateComboBox);

		// button for applying change
		applyButton = new JButton("Apply");
		applyButton.setBounds(new Rectangle(300, 500));
		applyButton.addActionListener(this);
		buttonPanel.add(applyButton);

		// ******** ACTIVE LISTINGS *******
		displayPanel = new JPanel();
		displayPanel.setBorder(new TitledBorder(new EtchedBorder(), "Active Listings"));
		displayPanel.setBounds(50, 150, 700, 400);
		
		display = new JTextArea(22, 52);
		display.setEditable(false); // set textArea non-editable
		scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayPanel.add(scroll);

		// must display listings here
		display.append("ID\t\t");
		display.append("Address\n\n");

		for (int i = 0; i<activeProperty.size(); i++){
			String propID = Integer.toString(activeProperty.get(i).getID());
			String address = activeProperty.get(i).getAddress();

			display.append(propID + "\t\t");
			display.append(address + "\n\n");

		}

		// ******** FRAME ********
		frame.add(titlePanel);
		frame.add(buttonPanel);
		frame.add(displayPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exiting window will close window
		frame.setSize(800, 600); // setting size of window
		frame.setLayout(null); // no layout
		frame.setTitle("Rental Property Management System");

		frame.setVisible(true);
	}

	SOLForm(int id) {
	
	 Landlord myLandlord = new Landlord(id);
	 landlordID = id;
		activeProperty = myLandlord.searchProperties("Active");
		// ********* TITLE ************
		titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 800, 50);

		titleLabel = new JLabel("Change State of Lisitng");
		titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
		titlePanel.add(titleLabel);

		// ********* BUTTON PANEL ************
		buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 50, 800, 75);

		// label for Property ID
		idLabel = new JLabel("Property ID: ");
		idLabel.setFont(new Font("Courier", Font.PLAIN, 17));
		buttonPanel.add(idLabel);

		// user input the property ID
		idText = new JTextField(10);
		buttonPanel.add(idText);
		buttonPanel.add(Box.createHorizontalStrut(60));

		// 
		stateComboBox = new JComboBox(states);
		buttonPanel.add(stateComboBox);

		applyButton2 = new JButton("Apply");
		applyButton2.setBounds(new Rectangle(300, 500));
		applyButton2.addActionListener(this);
		buttonPanel.add(applyButton2);

		// ******** ACTIVE LISTINGS *******
		displayPanel = new JPanel();
		displayPanel.setBorder(new TitledBorder(new EtchedBorder(), "Active Listings"));
		displayPanel.setBounds(50, 150, 700, 400);

		display = new JTextArea(22, 52);
		display.setEditable(false); // set textArea non-editable
		scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayPanel.add(scroll);

		// must display listings here
		display.append("ID\t\t");
		display.append("Address\n\n");

		for (int i = 0; i<activeProperty.size(); i++){
			String propID = Integer.toString(activeProperty.get(i).getID());
			String address = activeProperty.get(i).getAddress();

			display.append(propID + "\t\t");
			display.append(address + "\n\n");

		}

		// ******** FRAME ********
		frame.add(titlePanel);
		frame.add(buttonPanel);
		frame.add(displayPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exiting window will close window
		frame.setSize(800, 600); // setting size of window
		frame.setLayout(null); // no layout
		frame.setTitle("Rental Property Management System");

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int propID = Integer.parseInt(idText.getText());
		String myState = stateComboBox.getSelectedItem().toString();
		
		 


		// for manager
		if (e.getSource() == applyButton) {
			Manager myManager;
			myManager = new Manager();
			myManager.changeSOL(propID, myState);
			frame.dispose();
			SOLForm updated = new SOLForm();
			// have to call a check to make sure that id entered is within the list
			// if true, change the state
		}

		else if(e.getSource() == applyButton2){
			Landlord myLandlord;

			myLandlord= new Landlord(landlordID);

			// have to call a check to make sure that id entered is within the list
			// if true, change the state
			
			myLandlord.changeSOL(propID, myState);
			//display success messge 

		}

	}

}
