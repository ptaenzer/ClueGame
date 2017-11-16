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
import java.util.HashMap;
import java.util.Map;

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

public class DetectiveNotes extends JDialog {

	// constructor
	public DetectiveNotes(Dialog arg0) {
		super(arg0);
		setTitle("Detective Notes");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Create a layout with 2 rows and 4 columns
		setSize(800,600);
		setLayout(new BorderLayout());
		// add panels
		JPanel panel = createNamePanel();
		add(panel, BorderLayout.CENTER);
		panel = createWeaponPanel();
		add(panel, BorderLayout.EAST);
		panel = createGuessPanel();
		add(panel, BorderLayout.NORTH);
		panel = createRoomPanel();
		add(panel, BorderLayout.WEST);
		panel = createButtonPanel();
		add(panel, BorderLayout.SOUTH);
		setVisible(true);
	}

	// constructor for button panel
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JButton makeSug = new JButton("Make Suggestion");
		panel.add(makeSug);
		return panel;
	}

	// constructor for room panel
	private JPanel createRoomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getLegend().keySet().size(),1));
		JLabel nameLabel;
		JCheckBox box = new JCheckBox();
		for(char name : Board.getLegend().keySet()) {
			box = new JCheckBox();
			panel.add(box);
			nameLabel = new JLabel(Board.getLegend().get(name));
			panel.add(nameLabel);
		}
		panel.setPreferredSize(new Dimension(250,300));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room List"));
		return panel;
	}

	// constructor for guess panel
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		// adds room guess
		String[] names = new String[Board.getLegend().keySet().size()];
		int i = 0;
		for(char c : Board.getLegend().keySet()) {
			names[i] = Board.getLegend().get(c);
			i++;
		}
		JComboBox rooms = new JComboBox<String>(names);
		panel.add(rooms);
				
		// adds people guess
		names = new String[Board.getPlayers().keySet().size()];
		i = 0;
		for(String p : Board.getPlayers().keySet()) {
			names[i] = p;
			i++;
		}
		JComboBox people = new JComboBox<String>(names);
		panel.add(people);
		
		// adds weapon guess
		names = new String[Board.getWeapons().size()];
		i = 0;
		for(String w : Board.getWeapons()) {
			names[i] = w;
			i++;
		}
		JComboBox weapons = new JComboBox<String>(names);
		panel.add(weapons);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Suggestion Panel"));
		return panel;
	}

	// constructor for weapon panel
	private JPanel createWeaponPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getWeapons().size(),1));
		JLabel nameLabel;
		JCheckBox box;
		for(String name : Board.getWeapons()) {
			box = new JCheckBox();
			panel.add(box);
			nameLabel = new JLabel(name);
			panel.add(nameLabel);
		}
		panel.setPreferredSize(new Dimension(300,300));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon List"));
		return panel;
	}

	// constructor for name panel
	JCheckBox[] peopleBox = new JCheckBox[Board.getPlayers().keySet().size()];
	Map<JCheckBox, String> peopleNotes = new HashMap<JCheckBox, String>();
	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		CheckBoxListener clicker = new CheckBoxListener();
		panel.setLayout(new GridLayout(Board.getPlayers().keySet().size(),1));
		JLabel nameLabel;
		int i = 0;
		for(String name : Board.getPlayers().keySet()) {
			Card nameCard = new Card(name, CardType.PERSON);
			peopleBox[i] = new JCheckBox();
			peopleBox[i].addActionListener(clicker);
			peopleNotes.put(peopleBox[i], name);
			if(Board.getPlayers().get(Board.getHumanName()).getSeen().contains(nameCard)) {
				peopleBox[i].setEnabled(false);
			}
			panel.add(peopleBox[i]);
			nameLabel = new JLabel(name);
			panel.add(nameLabel);
			i++;
		}
		panel.setPreferredSize(new Dimension(250,300));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person List"));
		return panel;
	}

	// Action Listener for panel
		private class CheckBoxListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(peopleBox[0].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[1].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[2].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[3].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[4].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[5].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[6].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[7].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[8].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
				if(peopleBox[9].isSelected()) {
					Board.getPlayers().get(Board.getHumanName()).addSeenCard(new Card(peopleNotes.get(peopleBox[0]), CardType.PERSON)); 
				}
			}
		}
}
