package com.deadmadness.test;

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

import com.deadmadness.algorithm.*;

public class TestGUI extends JFrame{
	
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

	
	public TestGUI(String panel){
		if(panel == "messageBox"){
			setContentPane(new Message());
			//TODO set up message encryption window class 
		}
		else if(panel == "fileBox"){
			setContentPane(new Browse());
			//TODO set up file browser window class
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,600);
		setVisible(true);
	}
	public static void main(String[] args) {
		JFrame message = new TestGUI("messageBox");
		//JFrame file = new TestGUI("fileBox");
		
		message.setSize(600, 600);
		//file.setSize(400, 500);
		

	}

}
