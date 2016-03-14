package com.deadmadness.algorithm;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class GUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1454019719794475451L;
	//RSA encryption classes
		static RSA encrypter = new RSA();
		static PrimeGen generate;
		
		//BigInteger variables
		static BigInteger[] encrypted = new BigInteger[3000];
		static BigInteger[] encryptedFile = new BigInteger[30000];
		static BigInteger[] decrypted = new BigInteger[3000];
		static BigInteger[] decryptedFile = new BigInteger[30000];
		static BigInteger[] keys = new BigInteger[3];
		static String outToInput = "";
		static String filePath = "";
		static String inputString = "";
		
		//first window for message encryption / decryption.
		
		//text fields
		static final JScrollPane inputBox = new JScrollPane();
		static final JTextPane inputField = new JTextPane();
		static final JScrollPane outputBox = new JScrollPane();
		static final JTextPane outputField = new JTextPane();
		static final JTextPane pubKeybox = new JTextPane();
		static final JTextPane pubExbox = new JTextPane();
		static final JTextPane privKeybox = new JTextPane();
		static final JTextPane fileBox = new JTextPane();
		static final JTextPane bitSet = new JTextPane();
		

		//labels
		static final JLabel lblOutput = new JLabel("Output");
		static final JLabel lblInput = new JLabel("Input");
		static final JLabel lblPublicKey = new JLabel("Public Key");
		static final JLabel lblPrivateKey = new JLabel("Private key");
		static final JLabel lblPublicExponent = new JLabel("Public Exponent");
		static final JLabel lblSetBits = new JLabel("Bits:");
		//buttons
		static final JButton btnEncrypt = new JButton("Encrypt text");
		static final JButton btnEncryptFile = new JButton("Encrypt file");
		static final JButton btnBrowse = new JButton("Browse Files...");
		static final JButton btnDecrypt = new JButton("Decrypt text");
		static final JButton btnDecryptFile = new JButton("Decrypt file");
		static final JButton btnGenerateKeys = new JButton("Generate Keys");
		static final JButton btnCopyPaste = new JButton("Copy/Paste");
		
		
		//creates the window to encrypt messages
		public static class Message extends JPanel{
			/**
			 * 
			 */
			private static final long serialVersionUID = 7504088245074894851L;
			static Message instance;
			//constructor
			public Message(){
				instance = this;
				bitSet.setText("64");
				btnGenerateKeys.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						generate = new PrimeGen(bitSet.getText());
						keys = generate.getKeys();
						pubKeybox.setText("" + keys[0]);
						pubExbox.setText("" + keys[1]);
						privKeybox.setText("" + keys[2]);
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
				btnBrowse.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JFileChooser fc = new JFileChooser();
						//TODO Set for users own specific directory
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));
						fc.setDialogTitle("File Browser");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new TxtFileFilter());
						
						if(fc.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION){
							filePath = fc.getSelectedFile().getAbsolutePath();
						}
						System.out.println("File selected: " + filePath);
						try{
							FileReader inputFile = new FileReader(filePath);
							Scanner input = new Scanner(inputFile);
							while(input.hasNext()){
								inputString += input.nextLine();
							}
							fileBox.setText(filePath);
							
							inputFile.close();
							input.close();
						} catch(FileNotFoundException e1){
							e1.printStackTrace();
							System.exit(0);
						} catch(IOException e2){
							e2.printStackTrace();
						}
					}
				});
				
				btnEncryptFile.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
						BigInteger pubEx = new BigInteger("" + pubExbox.getText());
						JFileChooser fc = new JFileChooser();
						PrintWriter write;
						String encryptedString = "";
						
						System.out.println("inputString: " + inputString);
						
						encryptedFile = encrypter.encrypt(inputString, pubKey, pubEx);
						
						for(int i=0;i<encryptedFile.length;i++){
							encryptedString += encryptedFile[i];
							encryptedString += "-";
						}
						
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));
						fc.setDialogTitle("File Browser");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new TxtFileFilter());
						
						if(fc.showSaveDialog(btnEncryptFile) == JFileChooser.APPROVE_OPTION){
							try{
								write = new PrintWriter(fc.getSelectedFile()+".txt");
								write.write(encryptedString);
								write.flush();
								write.close();
							} catch (IOException ex){
								ex.printStackTrace();
							}
						}
						
					}
				});
				
				btnDecryptFile.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());
						BigInteger privKey = new BigInteger("" + privKeybox.getText());
						BigInteger[] encryptedMessage;
						
						FileReader read;
						PrintWriter write;
						
						String encryptedFileString = "";
						String decryptedFileString = "";
						String[] parsedMessage;
						
						JFileChooser fc = new JFileChooser();
						Scanner input;
						
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));
						fc.setDialogTitle("File Browser");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new TxtFileFilter());
						try{
							read = new FileReader(fileBox.getText());
							input = new Scanner(read);
							while(input.hasNext()){
								encryptedFileString += input.next();
							}
							read.close();
							input.close();
						} catch(FileNotFoundException e1){
							e1.printStackTrace();
							System.exit(0);
						} catch(IOException e2){
							e2.printStackTrace();
						}
						
						parsedMessage = encryptedFileString.split("-");
						encryptedMessage = new BigInteger[parsedMessage.length];
						
						for(int i=0;i<parsedMessage.length;i++){
							encryptedMessage[i] = new BigInteger("" + parsedMessage[i]);
						}
						
						decryptedFile = encrypter.decrypt(encryptedMessage, pubKey, privKey);
						
						for(int i=0;i<decryptedFile.length;i++){
							decryptedFileString += ((char)decryptedFile[i].intValue());
						}
						
						if(fc.showSaveDialog(btnDecryptFile) == JFileChooser.APPROVE_OPTION){
							try{
								write = new PrintWriter(fc.getSelectedFile()+".txt");
								write.write(decryptedFileString);
								write.flush();
								write.close();
							} catch (IOException ex){
								ex.printStackTrace();
							}
						}
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
				this.add(lblSetBits);
				this.add(bitSet);
				this.add(btnBrowse);
				this.add(btnEncryptFile);
				this.add(btnDecryptFile);
				this.add(fileBox);
				this.add(lblInput);
				this.add(inputField);
				this.add(lblOutput);
				this.add(outputField);
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
				
				lblSetBits.setBounds(400, 10 , 30, 30);
				lblSetBits.setSize(50, 30);
				
				bitSet.setBounds(435, 15, 30, 30);
				bitSet.setSize(30, 25);
				
				btnBrowse.setBounds(200, 250, 200, 50);
				btnBrowse.setSize(200, 30);
				
				btnEncryptFile.setBounds(50, 250, 200, 50);
				btnEncryptFile.setSize(150, 30);
				
				btnDecryptFile.setBounds(400, 250, 200, 50);
				btnDecryptFile.setSize(150, 30);
				
				fileBox.setBounds(100, 215, 200, 50);
				fileBox.setSize(400, 30);
				
				btnEncrypt.setBounds(450, 325, 200, 50);
				btnEncrypt.setSize(125,25);
				
				btnCopyPaste.setBounds(450, 362, 200, 50);
				btnCopyPaste.setSize(125,25);
				
				btnDecrypt.setBounds(450, 400, 200, 50);
				btnDecrypt.setSize(125,25);
				
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


		
		public GUI(String window, String windowTitle){
			super(windowTitle);
			
			if(window == "messageBox"){
				
				setContentPane(new Message());
				setSize(600,600);
			}

			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			setVisible(true);
		}
		public static void main(String[] args) {
			JFrame message = new GUI("messageBox" ,"RSA Encryption");
			
			message.setSize(600, 600);
			

		}
}
