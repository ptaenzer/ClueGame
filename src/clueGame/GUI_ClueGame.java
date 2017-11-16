/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the GUI class 
 */
package clueGame;

// imports
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_ClueGame extends JPanel {

	static Board board;
	
	// GUI constructor
	public GUI_ClueGame() {
		// Create a layout with 2 rows and 4 columns
		setLayout(new BorderLayout());
		// add panels
		JPanel panel = createBoardPanel();
		add(panel, BorderLayout.WEST);
		panel = createNamePanel();
		add(panel, BorderLayout.CENTER);
		panel = createWeaponPanel();
		add(panel, BorderLayout.EAST);
		panel = createCardPanel();
		add(panel, BorderLayout.NORTH);
		panel = createButtonPanel();
		add(panel, BorderLayout.SOUTH);
	}
	
	// creates user input panel
	JButton openNotes = new JButton("Detective Notes");
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(openNotes);
		ButtonListener note = new ButtonListener();
		openNotes.addActionListener(note);
		panel.setPreferredSize(new Dimension(746,200));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "User input"));
		return panel;
	}

	// create myCards panel for user character
	private JPanel createCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JButton box;
		for(Card c : Board.getPlayers().get("Greeny").getCards()) {
			box = new JButton(c.getCardName());
			panel.add(box);
		}
		panel.setPreferredSize(new Dimension(746,100));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "MyCards Panel"));
		return panel;
	}

	// create weapon list panel
	private JPanel createWeaponPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getWeapons().size(),1));
		JLabel nameLabel;
		for(String name : Board.getWeapons()) {
			nameLabel = new JLabel(name);
			panel.add(nameLabel);
		}
		panel.setPreferredSize(new Dimension(200,546));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon List"));
		return panel;
	}

	// create name list panel
	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getPlayers().keySet().size(),1));
		JLabel nameLabel;
		for(String name : Board.getPlayers().keySet()) {
			nameLabel = new JLabel(name + ": " + Board.getPlayers().get(name).getPlayerColor());
			panel.add(nameLabel);
		}
		panel.setPreferredSize(new Dimension(200,546));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Name List"));
		return panel;
	}

	// create board panel
	private JPanel createBoardPanel() {
		JPanel panel = board.createBoardPanel();
		repaint();
		return panel;
	}

	// Action Listener for button panel
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(openNotes.isEnabled()) {
				DetectiveNotes note = new DetectiveNotes(new JDialog());
			}
		}
	}
	
	// main for GUI
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Peter Taenzer and Jacob Gay's Clue Game");
		// Create the JPanels and add them to the JFrame
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLayoutLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		GUI_ClueGame gui = new GUI_ClueGame();
		frame.add(gui, BorderLayout.CENTER);
		frame.setSize(1146, 646);
		frame.pack();
		// tell player who they are
		JOptionPane.showMessageDialog(frame, "You are Greeny, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		// Now let's view it
		frame.setVisible(true);
	}
}
