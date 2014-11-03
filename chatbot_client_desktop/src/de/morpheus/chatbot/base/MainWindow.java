package de.morpheus.chatbot.base;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import de.morpheus.chatbot.extension.AIMLExtensionHub;
import de.morpheus.chatbot.processing.abstracts.AsynchronousInformationRequester;
import de.morpheus.chatbot.processing.abstracts.InputProcessor;
import de.morpheus.chatbot.timer.ProcessorTimer;

public final class MainWindow 
extends AsynchronousInformationRequester<String, String> {

	private JFrame frame;
	private JTextArea responseLabel;
	private JTextPane inputField;
	private JScrollPane scrollPane;
	
	private final String defaultInputMessage = 
			"\u0000Type Text in here. Press Enter to submit.";
	
	private ProcessorTimer<String> timer = new ProcessorTimer.NullTimer<String>();

	public MainWindow(InputProcessor<String, String> nextProcessor) {
		super(nextProcessor);
		initialize();
	}

	private void initialize() {
		initializeMainFrame();
		initializeResponseLabel();
		initializeInputField();
	}
	
	//init Methods
	
	private void initializeMainFrame() {
		frame = new JFrame();
		frame.setTitle("Háblame-Chatprogramm");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		float sizeFactor = 0.75f;
		Rectangle rect = new Rectangle(
				(int) (dim.width * (1 - sizeFactor) / 2), 
				(int) (dim.height * (1 - sizeFactor) / 2), 
				(int) (dim.width * sizeFactor), 
				(int) (dim.height * sizeFactor));
		frame.setBounds(rect);
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new java.awt.GridLayout(2, 1, 0, 0));
	}
	
	private void initializeResponseLabel() {
		{
			responseLabel = new JTextArea();
			responseLabel.setEditable(false);
			responseLabel.setBackground(Color.GRAY);
			responseLabel.setForeground(Color.WHITE);
			responseLabel.setLineWrap(true);
			responseLabel.setWrapStyleWord(true);
			((DefaultCaret)responseLabel.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			
			scrollPane = new JScrollPane();
			frame.getContentPane().add(scrollPane);
			scrollPane.setViewportView(responseLabel);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		addToLog("System", "Here the answers will be displayed.");
	}
	
	private void initializeInputField() {
		inputField = new JTextPane();
		inputField.setText(defaultInputMessage);
		frame.getContentPane().add(inputField);
		
		StyledDocument doc = inputField.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		initInputFieldListeners();		
	}

	private void initInputFieldListeners() {
		inputField.addKeyListener(new java.awt.event.KeyAdapter() {			
			@Override
			public void keyTyped(KeyEvent e) {				
				switch(e.getKeyChar()) {
				case '\n':
					final String message = inputField.getText().trim();
					
					if(e.isShiftDown() || message.isEmpty()) break;
					
					addToLog("Client", message);
					inputField.setText("");
					requestInputProcessing(message);
					break;
				}
				
				timer.resetTimer();
			}
		});
		
		inputField.addFocusListener(new java.awt.event.FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(inputField.getText().equals(defaultInputMessage)) {
					inputField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(inputField.getText().equals("")) {
					inputField.setText(defaultInputMessage);
				}
			}
		});
		
	}
	
	//Message Processing
	
	@Override
	protected void onProcessFinished(final String result) {
		addToLog("Server", result);
	}
	
	public void addToLog(final String sender, final String str) {
		if(responseLabel == null) {
			throw new IllegalStateException("Chat log has not been initialized yet!");
		}
		responseLabel.append(String.format("%n%s:%n%s%n", sender, str));
	}
	
	//Launch App
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputProcessor<String, String> firstProcessor = 
//							null;
//							new de.morpheus.chatbot.processing.DummyProcessor();
//							new de.morpheus.chatbot.processing.TextLogger();
//							new de.morpheus.chatbot.processing.AndrésWebService();
							new Chatbot(AIMLExtensionHub.createFromPath(
									AIMLExtensionHub.DIRECTORY_PATH,
									AIMLExtensionHub.CLASS_FILE_NAME)
									);
					
					
					MainWindow window = new MainWindow(firstProcessor);
					window.frame.setVisible(true);
					
					//window.timer = new ProcessorTimer<String>(window, 7.f, "<silence/>");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
