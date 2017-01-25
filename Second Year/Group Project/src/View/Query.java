package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Font;

public class Query extends JFrame {

	private JPanel contentPane;
	private JTextField dayTxt;
	private JTextField monthTxt;
	private JTextField yearTxt;
	private JTextField hrsTxt;
	private JTextField minsTxt;
	private JTextArea descriptionTxt;
	private JLabel lblDescription;
	private JLabel lblDate;
	private JButton btnSubmit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Query frame = new Query();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Query() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1054, 736);
		getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 955, 415);
		contentPane.setBackground(Color.GRAY);
		getContentPane().add(contentPane);
		
		descriptionTxt = new JTextArea();
		descriptionTxt.setBounds(180, 160, 300, 200);
		descriptionTxt.setLineWrap(true);
		JScrollPane scrollDesc = new JScrollPane(descriptionTxt);
		scrollDesc.setBounds(180, 60, 645, 221);
		contentPane.add(scrollDesc);
		
		lblDescription = new JLabel("Diary Message:");
		lblDescription.setBounds(55, 60, 115, 26);
		contentPane.add(lblDescription);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(635, 331, 190, 29);
		contentPane.add(btnSubmit);
	}
}
