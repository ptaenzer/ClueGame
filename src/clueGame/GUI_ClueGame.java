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
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_ClueGame extends JPanel {

	static Board board;
	static boolean picked = false;

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

	// creates variables for user input panel
	JButton openNotes = new JButton("Detective Notes");
	JButton nextPlayer = new JButton("Next Player");
	JButton accusation = new JButton("Make an Accusation!");
	static JTextField turn = new JTextField();
	static JTextField roll = new JTextField();
	static JTextArea guess = new JTextArea();
	static JTextField guessR = new JTextField();
	
	// creates user input panel
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,4));
		turn.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		roll.setBorder(new TitledBorder (new EtchedBorder(), "Die Roll"));
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guess.setRows(3);
		guessR.setBorder(new TitledBorder (new EtchedBorder(), "Guess Response"));
		panel.add(turn);
		panel.add(roll);
		panel.add(guess);
		panel.add(guessR);
		panel.add(nextPlayer);
		panel.add(openNotes);
		panel.add(accusation);
		ButtonListener note = new ButtonListener();
		openNotes.addActionListener(note);
		nextPlayer.addActionListener(note);
		accusation.addActionListener(note);
		panel.setPreferredSize(new Dimension(746,200));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "User input"));
		return panel;
	}

	// updates input panel for new player and roll
	public static void updateButtonPanel(int r, String currentName) {
		roll.setText(Integer.toString(r));
		turn.setText(currentName);
		guess.setText("");
		guessR.setText("");
	}

	//  updates input panel for suggestion
	public static void updateButtonPanelSug(ArrayList<Card> sug, String dissprove) {
		guess.setText("Person: " + sug.get(0).getCardName() + "\nWeapon: " + sug.get(1).getCardName() + "\nRoom: " +sug.get(2).getCardName());
		guessR.setText(dissprove);
	}

	// create myCards panel for user character
	private JPanel createCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JLabel box;
		int i = 0;
		for(Card c : Board.getPlayers().get(Board.getHumanName()).getCards()) {
			box = new JLabel(c.getCardName());
			box.setBorder(new TitledBorder (new EtchedBorder(), "Card" + i));
			panel.add(box);
			i++;
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
	JButton submit = new JButton("Submit");
	JButton cancel = new JButton("Cancel");
	JComboBox<String> rooms;
	JComboBox<String> people;
	JComboBox<String> weapons;
	JDialog f;
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// When open notes is pressed, creates new detective notes
			if(arg0.getSource() == openNotes) {
				DetectiveNotes note = new DetectiveNotes(new JDialog());
			}
			//When next player is pressed, checks conditions and moves on if conditions are correct
			if(arg0.getSource() == nextPlayer) {
				if(Board.getHumanSug() && Board.getHumanMove() && !Board.getHumanAcc()) {
					Board.nextPlayer();
					repaint();
				}
				else if(!Board.getHumanSug()) {
					JOptionPane.showMessageDialog(frame, "You must make a suggestion!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else if(!Board.getHumanMove() && Board.getHumanAcc()) {
					JOptionPane.showMessageDialog(frame, "You must make a move or make a accusation!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			// When make accusation is pressed, checks conditions and then opens accusation menu
			if(arg0.getSource() == accusation) {
				if(Board.getHumanAcc()) {
					f = new JDialog(frame, "Make an Accusation", true);
					f.setLayout(new GridLayout(4,2));

					// adds room guess
					String[] names = new String[Board.getLegend().keySet().size()];
					int i = 0;
					for(char w : Board.getLegend().keySet()) {
						names[i] = Board.getLegend().get(w);
						i++;
					}
					rooms = new JComboBox<String>(names);
					JLabel roomLabel = new JLabel("Room");
					f.add(roomLabel);
					f.add(rooms);

					// adds people guess
					names = new String[Board.getPlayers().keySet().size()];
					i = 0;
					for(String w : Board.getPlayers().keySet()) {
						names[i] = w;
						i++;
					}
					people = new JComboBox<String>(names);
					JLabel peopleLabel = new JLabel("Killer");
					f.add(peopleLabel);
					f.add(people);

					// adds weapon guess
					names = new String[Board.getWeapons().size()];
					i = 0;
					for(String w : Board.getWeapons()) {
						names[i] = w;
						i++;
					}
					weapons = new JComboBox<String>(names);
					JLabel weaponLabel = new JLabel("Weapon");
					f.add(weaponLabel);
					f.add(weapons);

					// Action listener for the submit and cancel buttons for accusation
					AccusationListener note = new AccusationListener();
					submit.addActionListener(note);
					cancel.addActionListener(note);
					f.add(submit);
					f.add(cancel);

					f.pack();
					f.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(frame, "You can only make an accusation at the start of your turn!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	// Action Listener for make accusation panel
	private class AccusationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// When submit is pressed and conditions, then tests accusation
			if(arg0.getSource() == submit) {
				System.out.println(people.getSelectedItem() + "\n" + weapons.getSelectedItem() + "\n" + rooms.getSelectedItem());
				if(Solution.testAccusation(new Card(people.getSelectedItem().toString(), CardType.PERSON), new Card(weapons.getSelectedItem().toString(), CardType.WEAPON), new Card(rooms.getSelectedItem().toString(), CardType.ROOM))) {
					frame.setVisible(false);
					JOptionPane.showMessageDialog(frame, "You won!", "Congradulations!", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					frame.setVisible(false);
					JOptionPane.showMessageDialog(frame, "You don f***ed up! And the killer gets away!", "You all lose!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			//When cancel is pressed, exits the menu
			if(arg0.getSource() == cancel) {
				f.dispose();
			}
		}
	}

	// disprove getter for the human player called from disprove loop
	static JButton card1;
	static JButton card2;
	static JButton card3;
	static JButton none;
	static Dialog disprove;
	static Card dis;
	public static Card getHumanDissprove() {
		disprove = new Dialog(frame, "Pick a card to show if in the suggestion", Dialog.ModalityType.DOCUMENT_MODAL);
		disprove.setLayout(new GridLayout(4,1));
		disprove.setModal(true);
		card1 = new JButton(Board.getPlayers().get(Board.getHumanName()).getCards().get(0).getCardName());
		card2 = new JButton(Board.getPlayers().get(Board.getHumanName()).getCards().get(1).getCardName());
		card3 = new JButton(Board.getPlayers().get(Board.getHumanName()).getCards().get(2).getCardName());
		none = new JButton("No cards match!");
		DisproveListener d = new DisproveListener();
		card1.addActionListener(d);
		card2.addActionListener(d);
		card3.addActionListener(d);
		none.addActionListener(d);
		disprove.add(card1);
		disprove.add(card2);
		disprove.add(card3);
		disprove.add(none);
		disprove.setSize(300, 200);
		updateButtonPanelSug(Board.getPlayers().get(Board.getCurrentName()).getSuggestion(), "");
		disprove.setVisible(true);
		return dis;
	}
	
	// Action Listener for make disprove dialog
	private static class DisproveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == card1) {
				dis = Board.getPlayers().get(Board.getHumanName()).getCards().get(0);
				disprove.dispose();
			}
			if(arg0.getSource() == card2) {
				dis = Board.getPlayers().get(Board.getHumanName()).getCards().get(1);
				disprove.dispose();
			}
			if(arg0.getSource() == card3) {
				dis = Board.getPlayers().get(Board.getHumanName()).getCards().get(2);
				disprove.dispose();
			}
			if(arg0.getSource() == none) {
				dis = null;
				disprove.dispose();
			}
		}
	}

	// main for GUI
	private static JFrame frame = new JFrame();
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Peter Taenzer and Jacob Gay's Clue Game");
		// Create the JPanels and add them to the JFrame
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLayoutLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		// select player in ten seconds
		while(!picked) {
			GameInit game = new GameInit(new JDialog());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// start game
		GUI_ClueGame gui = new GUI_ClueGame();
		frame.add(gui, BorderLayout.CENTER);
		frame.setSize(1146, 646);
		frame.pack();
		// tell player who they are
		// JOptionPane.showMessageDialog(frame, "You are Greeny, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		// Now let's view it
		System.out.println(Solution.getPerson().getCardName() + "\n" + Solution.getWeapon().getCardName() + "\n" + Solution.getRoom().getCardName());
		frame.setVisible(true);
	}

	// Sets picked to true when player picks the character name
	public static void setPickTrue() {
		picked = true;
	}

	// Error for when invalid target is chosen by player
	public static void errorClicked() {
		JOptionPane.showMessageDialog(frame, "That is not a target!", "", JOptionPane.INFORMATION_MESSAGE);
	}

	// Error for trying to make a suggestion when they can't
	public static void errorMakeSug() {
		JOptionPane.showMessageDialog(frame, "You must enter a room first!", "", JOptionPane.INFORMATION_MESSAGE);
	}

	// Message for when a Computer Player wins
	public static void compWin(String name) {
		frame.setVisible(false);
		JOptionPane.showMessageDialog(frame, name + " figured out who don it!", "You lose!", JOptionPane.INFORMATION_MESSAGE);
	}

	// Message for when a Computer Player loses
	public static void compLose(String name) {
		frame.setVisible(false);
		JOptionPane.showMessageDialog(frame, name + " don f***ed up! The killer got away!", "You all lose!", JOptionPane.INFORMATION_MESSAGE);
	}
}
