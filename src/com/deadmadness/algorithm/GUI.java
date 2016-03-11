package com.deadmadness.algorithm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;

public class GUI {

	public JFrame frame;
	private final JScrollPane inputBox = new JScrollPane();
	private JTextField inputField;
	public PrimeGen generator = new PrimeGen();
	public RSA encrypter = new RSA();
	public String message = "";
	public String decryptedMessage = "";
	public BigInteger[] ciphertext;
	public BigInteger[] keys = new BigInteger[3];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 566, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		inputBox.setBounds(22, 193, 332, 128);
		frame.getContentPane().add(inputBox);
		
		inputField = new JTextField();
		inputBox.setViewportView(inputField);
		inputField.setColumns(10);
		
		JScrollPane outputBox = new JScrollPane();
		outputBox.setBounds(22, 349, 332, 78);
		frame.getContentPane().add(outputBox);
		
		JTextPane outputField = new JTextPane();
		outputBox.setViewportView(outputField);
		
		JTextPane pubKeybox = new JTextPane();
		pubKeybox.setBounds(22, 33, 499, 21);
		frame.getContentPane().add(pubKeybox);
		
		JTextPane privKeybox = new JTextPane();
		privKeybox.setBounds(22, 80, 499, 21);
		frame.getContentPane().add(privKeybox);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(22, 333, 70, 15);
		frame.getContentPane().add(lblOutput);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(22, 172, 70, 15);
		frame.getContentPane().add(lblInput);
		
		JLabel lblPublicKey = new JLabel("Public Key");
		lblPublicKey.setBounds(22, 12, 97, 15);
		frame.getContentPane().add(lblPublicKey);
		
		JLabel lblPrivateKey = new JLabel("Private key");
		lblPrivateKey.setBounds(22, 66, 97, 15);
		frame.getContentPane().add(lblPrivateKey);
		
		JButton btnEnter = new JButton("Encrypt");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ciphertext = encrypter.encrypt(message, keys[1], keys[0]);
				
			}
		});
		btnEnter.setBounds(366, 256, 117, 25);
		frame.getContentPane().add(btnEnter);
		
		JButton btnBrowse = new JButton("Browse..");
		btnBrowse.setBounds(366, 193, 117, 25);
		frame.getContentPane().add(btnBrowse);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(366, 292, 117, 25);
		frame.getContentPane().add(btnDecrypt);
		
		JButton btnGenerateKeys = new JButton("Generate Keys");
		/*
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String privKey = "";
				String pubKey = "";
				
				keys = generator.getKeys();
				
				pubKey = 
				
				pubKeybox.setText();
			}
		});*/
		btnGenerateKeys.setBounds(180, 113, 173, 25);
		frame.getContentPane().add(btnGenerateKeys);
	}
}
