package com.deadmadness.algorithm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;

public class GUI {

	public JFrame frame;
	private final JScrollPane inputBox = new JScrollPane();
	public String message = "";
	//int msgLen;
	/*
	public PrimeGen generator;
	public RSA encrypter = new RSA();
	public BigInteger[] keys = new BigInteger[3];*/

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
		frame.setBounds(100, 100, 593, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		inputBox.setBounds(22, 193, 332, 128);
		frame.getContentPane().add(inputBox);
		
		JTextPane inputField = new JTextPane();
		inputBox.setViewportView(inputField);
		
		JScrollPane outputBox = new JScrollPane();
		outputBox.setBounds(22, 349, 332, 78);
		frame.getContentPane().add(outputBox);
		
		JTextPane outputField = new JTextPane();
		outputBox.setViewportView(outputField);
		
		JTextPane pubKeybox = new JTextPane();
		pubKeybox.setBounds(22, 32, 499, 21);
		frame.getContentPane().add(pubKeybox);
		
		JTextPane pubExbox = new JTextPane();
		pubExbox.setBounds(22, 86, 499, 21);
		frame.getContentPane().add(pubExbox);
		
		JTextPane privKeybox = new JTextPane();
		privKeybox.setBounds(22, 131, 499, 21);
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
		lblPrivateKey.setBounds(22, 116, 97, 15);
		frame.getContentPane().add(lblPrivateKey);
		
		
		/* Encryption button */
		JButton btnEnter = new JButton("Encrypt");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String encrypted = "";
				RSA encrypter = new RSA();
				BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
				BigInteger pubEx = new BigInteger("" + pubExbox.getText());
				BigInteger[] ciphertext;
				message = inputField.getText();
				//msgLen = message.length();
				
				System.out.println("inputField: " + inputField.getText());
				System.out.println("Message: " + message);
				System.out.println("Public Key: " + pubKey);
				System.out.println("Public Exponent: " + pubEx);
				
				ciphertext = encrypter.encrypt(message, pubKey, pubEx);
				
				System.out.println("Message Encrypted\nEncrypted Message: ");
				for(int i=0;i<ciphertext.length;i++){
					System.out.print(ciphertext[i]);
					encrypted += ciphertext[i];
					if(i != ciphertext.length-1){
						encrypted += "-";
					}
					
				}
				System.out.println("\nCipher Text: " + encrypted);
				outputField.setText("Ciphertext: " + encrypted);
			}
		});
		btnEnter.setBounds(366, 256, 117, 25);
		frame.getContentPane().add(btnEnter);
		
		JButton btnBrowse = new JButton("Browse..");
		btnBrowse.setBounds(366, 193, 117, 25);
		frame.getContentPane().add(btnBrowse);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSA encrypter = new RSA();
				String rawtext = inputField.getText();
				String[] parsedText = rawtext.split("-");
				String decryptedMessage = "";
				BigInteger[] encrypted = new BigInteger[parsedText.length];
				BigInteger[] decrypted;
				BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
				BigInteger privKey = new BigInteger("" + privKeybox.getText());
				//int[] cipher = new int[plaintext.length()];
				
				System.out.println("\nplaintext: \n" + rawtext);
				System.out.println("Public Key: " + pubKey);
				System.out.println("Private Key: " + privKey);
				/*
				System.out.println("cipher: ");
				for(int i=0;i<plaintext.length();i++){
					cipher[i] = ((int)plaintext.charAt(i));
					System.out.print(cipher[i]);
				}*/
				System.out.println("plaintext length: " + rawtext.length());
				System.out.println("\nEncrypted: ");
				for(int i=0;i<parsedText.length;i++){

					encrypted[i] = new BigInteger("" + parsedText[i]);
					System.out.print("" + encrypted[i]);
				}


				
				
				decrypted = encrypter.decrypt(encrypted, pubKey, privKey);
				System.out.println("\nDecrypted Message outside decrypt(): ");
				for(int i=0;i<decrypted.length;i++){
					System.out.print(decrypted[i]);
					decryptedMessage += ((char)decrypted[i].intValue());
				}
				System.out.println("\nDecrypted message: " + decryptedMessage);

				
				outputField.setText("" + decryptedMessage);
			}
		});
		btnDecrypt.setBounds(366, 292, 117, 25);
		frame.getContentPane().add(btnDecrypt);
		
		JButton btnGenerateKeys = new JButton("Generate Keys");
		
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String privKey;
				String pubEx;
				String pubKey;
				PrimeGen generator = new PrimeGen();
				
				BigInteger[] keys = new BigInteger[3];
						
				keys = generator.getKeys();
				
				pubKey = "" + keys[0];
				pubKeybox.setText(pubKey);
				
				pubEx = "" + keys[1];
				pubExbox.setText(pubEx);
				
				privKey = "" + keys[2];
				privKeybox.setText(privKey);
			}
		});
		btnGenerateKeys.setBounds(181, 152, 173, 25);
		frame.getContentPane().add(btnGenerateKeys);
		
		JLabel lblPublicExponent = new JLabel("Public Exponent");
		lblPublicExponent.setBounds(22, 65, 134, 15);
		frame.getContentPane().add(lblPublicExponent);
	}
}
