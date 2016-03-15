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

/**
 * GUI class using JFrames to display a controller 
 * for the user to encrypt/decrypt messages and files.
 * 
 * 15/03/2016
 * @author deadmadness
 */

public class GUI extends JFrame{
	
	
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
		static final JButton btnBrowse = new JButton("Browse txt Files...");
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
				bitSet.setText("64");										//default bit set for encryption keys
				
				btnGenerateKeys.addActionListener(new ActionListener(){		//Generate keys button functionality
					public void actionPerformed(ActionEvent e){
						generate = new PrimeGen(bitSet.getText());			//instantiate Prime number Generator
						keys = generate.getKeys();							//Get Encryption keys
						pubKeybox.setText("" + keys[0]);					//set keys to fields in GUI
						pubExbox.setText("" + keys[1]);
						privKeybox.setText("" + keys[2]);
					}
				});
				
				btnEncrypt.addActionListener(new ActionListener(){							//Encrypt plaintext messages button
					public void actionPerformed(ActionEvent e){
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());		//public and private keys
						BigInteger pubEx = new BigInteger("" + pubExbox.getText());
						String ciphertext = "";												//String representation of encrypted file
						
						//encrypting data
						encrypted = encrypter.encrypt(inputField.getText(), pubKey, pubEx);
						
						//Parsing and storing encrypted message as a String
						for(int i=0;i<encrypted.length;i++){
							ciphertext += encrypted[i];
							if(i != encrypted.length-1){
								ciphertext += "-";
							}
						}
						outputField.setText(ciphertext);									//set output to field in GUI
					}
				});
				btnCopyPaste.addActionListener(new ActionListener(){						//Copy from output to input field in GUI button
					public void actionPerformed(ActionEvent e){
						outToInput = outputField.getText();									//gets text from output
						inputField.setText(outToInput);										//sets it to input field in GUI
					}
				});
					btnDecrypt.addActionListener(new ActionListener(){						//Decrypt text messages button
						public void actionPerformed(ActionEvent e){
						String rawtext = inputField.getText();								//get text from input field
						String[] parsedtext = rawtext.split("-");							//parse data, removing the dashes
						String decryptedString = "";										//String representation of decrypted message
						
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());		//set decryption keys
						BigInteger privKey = new BigInteger("" + privKeybox.getText());
						BigInteger[] encrypted = new BigInteger[parsedtext.length];			//initialise the encrypted variable storage
						
						
						
						for(int i=0;i<parsedtext.length;i++){								
							encrypted[i] = new BigInteger("" +parsedtext[i]);				//store parsed string into the encrypted BigInteger array
						}
						
						decrypted = encrypter.decrypt(encrypted, pubKey, privKey);			//decrypt data
						for(int i=0;i<decrypted.length;i++){
							decryptedString += ((char)decrypted[i].intValue());				//store decrypted data, converted to a string
						}
						
						outputField.setText(decryptedString);								//set output field to decrypted data 
						
					}
				});
				btnBrowse.addActionListener(new ActionListener(){							//Browse files button
					public void actionPerformed(ActionEvent e){
						JFileChooser fc = new JFileChooser();								//initialise JFileChooser
						//TODO Set for users own specific directory
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));		//set opening directory to Desktop
						fc.setDialogTitle("File Browser");									//set title of window
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);					//set files only filter
						fc.setFileFilter(new TxtFileFilter());								//set custom filter to show only txt files
						
						if(fc.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION){	//if browse files was clicked..
							filePath = fc.getSelectedFile().getAbsolutePath();				//grab file path user selected
						}
						System.out.println("File selected: " + filePath);
						try{
							FileReader inputFile = new FileReader(filePath);				//creates file reader to read selected file
							Scanner input = new Scanner(inputFile);							//creates the scanner to take input from file
							//while loop to end of file
							while(input.hasNext()){
								inputString += input.nextLine();							//adds each line of input to inputString
							}
							fileBox.setText(filePath);										//sets filepath to selected file
							
							//close filereader and scanner
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
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());		//public key 
						BigInteger pubEx = new BigInteger("" + pubExbox.getText());			//private key 
						JFileChooser fc = new JFileChooser();								//filechooser initialisation
						PrintWriter write;													//writes to a file
						String encryptedString = "";										//String representation of encrypted file
						
						System.out.println("inputString: " + inputString);
						
						//encrypting data
						encryptedFile = encrypter.encrypt(inputString, pubKey, pubEx);		
						
						for(int i=0;i<encryptedFile.length;i++){							//converting from BigInteger to String
							encryptedString += encryptedFile[i];
							encryptedString += "-";											//parsing each character with a '-'
						}
						
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));		//setup for JFileChooser
						fc.setDialogTitle("File Browser");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new TxtFileFilter());
						
						//If button clicked..
						if(fc.showSaveDialog(btnEncryptFile) == JFileChooser.APPROVE_OPTION){
							try{
								//write encrypted string to a new file
								write = new PrintWriter(fc.getSelectedFile()+".txt");
								write.write(encryptedString);
								write.flush();												//empty buffer and close filewriter
								write.close();
							} catch (IOException ex){
								ex.printStackTrace();
							}
						}
						
					}
				});
				
				btnDecryptFile.addActionListener(new ActionListener(){						//decrypt file button
					public void actionPerformed(ActionEvent e){
						BigInteger pubKey = new BigInteger("" + pubKeybox.getText());		//set public and private keys
						BigInteger privKey = new BigInteger("" + privKeybox.getText());
						BigInteger[] encryptedMessage;										//BigInteger to store input of encrypted file
						
						FileReader read;													//reading and writing variables
						PrintWriter write;
						
						String encryptedFileString = "";									//string representations of encrypted/decrypted files
						String decryptedFileString = "";
						String[] parsedMessage;												//parsed string variable i.e. after '-' has been removed
						
						JFileChooser fc = new JFileChooser();								//JFileChooser initialisation
						Scanner input;
						
						fc.setCurrentDirectory(new File("/home/deadmadness/Desktop"));		//JFileChooser setup
						fc.setDialogTitle("File Browser");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setFileFilter(new TxtFileFilter());
						try{
							//set file reader to path and read input into EncryptedString variable
							read = new FileReader(fileBox.getText());
							input = new Scanner(read);
							while(input.hasNext()){
								encryptedFileString += input.next();
							}
							read.close();													//close reader and scanner
							input.close();
						} catch(FileNotFoundException e1){
							e1.printStackTrace();
							System.exit(0);
						} catch(IOException e2){
							e2.printStackTrace();
						}
						
						parsedMessage = encryptedFileString.split("-");						//parsing String data
						encryptedMessage = new BigInteger[parsedMessage.length];			//initialising encryptedMessage with length of parsed string array
						
						for(int i=0;i<parsedMessage.length;i++){
							encryptedMessage[i] = new BigInteger("" + parsedMessage[i]);	// filling array with parsed data
						}
						
						//decryption
						decryptedFile = encrypter.decrypt(encryptedMessage, pubKey, privKey);	
						
						for(int i=0;i<decryptedFile.length;i++){
							decryptedFileString += ((char)decryptedFile[i].intValue());		// converting from BigInteger to String
						}
						
						if(fc.showSaveDialog(btnDecryptFile) == JFileChooser.APPROVE_OPTION){	//saving file after decryption
							try{
								//writing decrypted data to file
								write = new PrintWriter(fc.getSelectedFile()+".txt");
								write.write(decryptedFileString);
								write.flush();												//flushing buffers and closing writer
								write.close();
							} catch (IOException ex){
								ex.printStackTrace();
							}
						}
					}
				});
				
																								
				this.add(lblPublicKey);														//adding components to the JFrame
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
				lblPublicKey.setBounds(50, 30, 100, 50);										//drawing coordinates for each component
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


		//GUI constructor
		public GUI(String windowTitle){
			super(windowTitle);					//set panel title
				
			setContentPane(new Message());		//call constructor for JPanel and set it to the JFrame
			setSize(600,600);					//set the window size
			
			//default close operation
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// make visible
			setVisible(true);
		}
		//main method
		public static void main(String[] args) {
			JFrame message = new GUI("RSA Encryption");	//initialise JFrame
			message.setSize(600, 600);					//set window size
		}
}
