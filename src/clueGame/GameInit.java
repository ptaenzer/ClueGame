/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the class to create the detective notes dialog
 */
package clueGame;

// imports
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GameInit extends JDialog {

	// constructor
	public GameInit(Dialog arg0) {
		super(arg0);
		setTitle("Welcome to Clue");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Create a layout with 2 rows and 4 columns
		setSize(300,200);
		setLayout(new BorderLayout());
		// add panels
		JPanel panel = createNamePanel();
		add(panel, BorderLayout.CENTER);
		setVisible(true);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// constructor for name panel
	private JButton ok = new JButton("Lets get Started!");;
	private static JComboBox<String> people = new JComboBox<String>(Board.getPeopleNames());
	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		PickListener OK = new PickListener();
		panel.setLayout(new GridLayout(3,1));
		JLabel nameLabel = new JLabel("Pick which character you would like to be: ");
		panel.add(nameLabel);
		String[] names = new String[Board.getPlayers().keySet().size()];
		int i = 0;
		for(String name : Board.getPlayers().keySet()) {
			names[i] = Board.getLegend().get(name);
			i++;
		}
		panel.add(people);
		ok.addActionListener(OK);
		panel.add(ok);
		return panel;
	}
	
	// Action Listener for panel
	private class PickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(ok.isEnabled()) {
				Board.setHumanName(people.getSelectedItem().toString());
				Board.setSetHuman(true);
				dispose(); 
			}
		}
	}

}
