package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes(Dialog arg0) {
		super(arg0);
		setTitle("Detective Notes");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// Create a layout with 2 rows and 4 columns
		setSize(900,500);
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

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JButton makeSug = new JButton("Make Suggestion");
		panel.add(makeSug);
		//panel.setPreferredSize(new Dimension(200,200));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "User input"));
		return panel;
	}

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
		panel.setPreferredSize(new Dimension(300,300));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room List"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		//panel.setPreferredSize(new Dimension(200,200));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "MyCards Panel"));
		return panel;
	}

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

	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.getPlayers().keySet().size(),1));
		JLabel nameLabel;
		JCheckBox box;
		for(String name : Board.getPlayers().keySet()) {
			box = new JCheckBox();
			panel.add(box);
			nameLabel = new JLabel(name);
			panel.add(nameLabel);
		}
		panel.setPreferredSize(new Dimension(300,300));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person List"));
		return panel;
	}

}
