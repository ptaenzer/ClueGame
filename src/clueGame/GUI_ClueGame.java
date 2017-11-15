/*
 * Authors: Peter Taenzer and Jacob Gay
 * This is the GUI class so far very basic
 */
package clueGame;

// imports
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JButton makeSug = new JButton("Detective Notes");
		JTextField sug = new JTextField(20);
		panel.add(makeSug);
		panel.add(sug);
		panel.setPreferredSize(new Dimension(1166,200));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "User input"));
		return panel;
	}

	// create myCards panel for user character
	private JPanel createCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		panel.setPreferredSize(new Dimension(1166,200));
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
		panel.setPreferredSize(new Dimension(200,566));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon List"));
		return panel;
	}

	// create name list panel
	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getPlayers().keySet().size(),1));
		JLabel nameLabel;
		for(String name : Board.getPlayers().keySet()) {
			nameLabel = new JLabel(name);
			panel.add(nameLabel);
		}
		//panel.setPreferredSize(new Dimension(200,566));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Name List"));
		return panel;
	}

	// create board panel
	private JPanel createBoardPanel() {
		JPanel panel = board.createBoardPanel();
		repaint();
		return panel;
	}

	// main for GUI
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI ClueGame");
		// Create the JPanels and add them to the JFrame
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLayoutLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		GUI_ClueGame gui = new GUI_ClueGame();
		frame.add(gui, BorderLayout.CENTER);
		frame.pack();
		// Now let's view it
		frame.setVisible(true);
		DetectiveNotes note = new DetectiveNotes(new JDialog());
	}
}
