package com.deadmadness.algorithm;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.deadmadness.test.TestGUI;
import com.deadmadness.test.TestGUI.Browse;
import com.deadmadness.test.TestGUI.Message;
/*
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
*/
public class GUI extends JFrame{
	
	//RSA encryption classes
		static RSA encrypter = new RSA();
		static PrimeGen generate;
		
		//BigInteger variables
		static BigInteger[] encrypted = new BigInteger[3000];
		static BigInteger[] decrypted = new BigInteger[3000];
		static BigInteger[] keys = new BigInteger[3];
		static String outToInput = "";
		
		//first window for message encryption / decryption.
		
		//text fields
		static final JScrollPane inputBox = new JScrollPane();
		static final JTextPane inputField = new JTextPane();
		static final JScrollPane outputBox = new JScrollPane();
		static final JTextPane outputField = new JTextPane();
		static final JTextPane pubKeybox = new JTextPane();
		static final JTextPane pubExbox = new JTextPane();
		static final JTextPane privKeybox = new JTextPane();
		//labels
		static final JLabel lblOutput = new JLabel("Output");
		static final JLabel lblInput = new JLabel("Input");
		static final JLabel lblPublicKey = new JLabel("Public Key");
		static final JLabel lblPrivateKey = new JLabel("Private key");
		static final JLabel lblPublicExponent = new JLabel("Public Exponent");
		//buttons
		static final JButton btnEncrypt = new JButton("Encrypt text");
		static final JButton btnBrowse = new JButton("Encrypt File...");
		static final JButton btnDecrypt = new JButton("Decrypt text");
		static final JButton btnGenerateKeys = new JButton("Generate Keys");
		static final JButton btnCopyPaste = new JButton("Copy/Paste");

		
		
		
		//creates the window to encrypt messages
		public static class Message extends JPanel{
			static Message instance;
			//constructor
			public Message(){
				instance = this;
				btnGenerateKeys.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						generate = new PrimeGen();
						keys = generate.getKeys();
						pubKeybox.setText("" + keys[0]);
						pubExbox.setText("" + keys[1]);
						privKeybox.setText("" + keys[2]);
						//inputField.setText("Test String");
						
						//Message.instance.add(pubKeybox);
						//Message.instance.add(pubExbox);
						//Message.instance.add(privKeybox);
					}
				});
				
				btnEncrypt.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
						BigInteger pubEx = new BigInteger("" + pubExbox.getText());
						String ciphertext = "";
						
						//encrypting data
						encrypted = encrypter.encrypt(inputField.getText(), pubKey, pubEx);
						
						//Parsing and storing encrypted message as a String
						for(int i=0;i<encrypted.length;i++){
							ciphertext += encrypted[i];
							if(i != encrypted.length-1){
								ciphertext += "-";
							}
						}
						outputField.setText(ciphertext);
					}
				});
				btnCopyPaste.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						outToInput = outputField.getText();
						inputField.setText(outToInput);
					}
				});
				btnDecrypt.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String rawtext = inputField.getText();
						String[] parsedtext = rawtext.split("-");
						String decryptedString = "";
						
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
						BigInteger privKey = new BigInteger("" + privKeybox.getText());
						BigInteger[] encrypted = new BigInteger[parsedtext.length];
						
						
						
						for(int i=0;i<parsedtext.length;i++){
							encrypted[i] = new BigInteger("" +parsedtext[i]);
						}
						
						decrypted = encrypter.decrypt(encrypted, pubKey, privKey);
						for(int i=0;i<decrypted.length;i++){
							decryptedString += ((char)decrypted[i].intValue());
						}
						
						outputField.setText(decryptedString);
						
					}
				});
				
				this.add(lblPublicKey);
				this.add(pubKeybox);
				this.add(lblPublicExponent);
				this.add(pubExbox);
				this.add(lblPrivateKey);
				this.add(privKeybox);
				this.add(btnEncrypt);
				this.add(btnCopyPaste);
				this.add(btnDecrypt);
				this.add(btnGenerateKeys);
				this.add(btnBrowse);
				this.add(lblInput);
				//this.add(inputBox);
				this.add(inputField);
				//inputBox.setViewportView(inputField);
				this.add(lblOutput);
				//this.add(outputBox);
				this.add(outputField);
				//outputBox.setViewportView(outputField);
			}
			
			
			//set up window 
			@Override
			public void paintComponent(Graphics g){
				lblPublicKey.setBounds(50, 30, 100, 50);
				lblPublicKey.setSize(100, 15);
				
				pubKeybox.setBounds(50, 50, 500, 50);
				pubKeybox.setSize(500,20);
				
				lblPublicExponent.setBounds(50, 80, 100, 50);
				lblPublicExponent.setSize(200, 15);
				
				pubExbox.setBounds(50, 100, 500, 50);
				pubExbox.setSize(500,20);
				
				lblPrivateKey.setBounds(50, 130, 100, 50);
				lblPrivateKey.setSize(100, 15);
				
				privKeybox.setBounds(50, 150, 500, 50);
				privKeybox.setSize(500,20);
				
				btnGenerateKeys.setBounds(200, 175, 200, 50);
				btnGenerateKeys.setSize(200,20);
				
				btnBrowse.setBounds(200, 250, 200, 50);
				btnBrowse.setSize(200, 30);
				
				btnEncrypt.setBounds(450, 325, 200, 50);
				btnEncrypt.setSize(125,25);
				
				btnCopyPaste.setBounds(450, 362, 200, 50);
				btnCopyPaste.setSize(125,25);
				
				btnDecrypt.setBounds(450, 400, 200, 50);
				btnDecrypt.setSize(125,25);
				
				//prev 310 y
				lblInput.setBounds(50, 310, 50, 10);
				lblInput.setSize(70,15);
				
				inputField.setBounds(50, 325, 100, 250);
				inputField.setSize(400, 100);
				
				lblOutput.setBounds(50, 435, 50, 10);
				lblOutput.setSize(70,15);
				
				outputField.setBounds(50, 450, 100, 250);
				outputField.setSize(500, 100);
				
				
			}
			
		}
		
		
		//creates the window for browsing files 
		public static class Browse extends JPanel{
			static Browse instance;
			//constructor
			public Browse(){
				instance = this;

			}
		}

		
		public GUI(String window, String windowTitle){
			super(windowTitle);
			
			if(window == "messageBox"){
				
				setContentPane(new Message());
				setSize(600,600);
				//TODO set up message encryption window class 
			}
			else if(window == "fileBox"){
				setContentPane(new Browse());
				setSize(400,450);
				//TODO set up file browser window class
			}
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			setVisible(true);
		}
		public static void main(String[] args) {
			JFrame message = new GUI("messageBox" ,"RSA Encryption");
			//JFrame file = new TestGUI("fileBox" ,"Browse files");
			
			message.setSize(600, 600);
			//file.setSize(400, 500);
			

		}
	
	/*
	public JFrame frame;
	private final JScrollPane inputBox = new JScrollPane();
	public String message = "";


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


	public GUI() {
		
		initialize();
	}


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
		
		
		// Encryption button 
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
				}*//*
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
	}*/
}
