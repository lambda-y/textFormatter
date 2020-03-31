package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
//import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import textFormatting.*;
//import javax.swing.filechooser.*;

public class Main {

	final static boolean BUGS = true;
	static JFormatter formatText;
	static String resultingText;

	// Gets the users' screen size
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// Size settings of elements and windows based on the users' screen size
	final static int SCREEN_WIDTH = (int) (screenSize.getWidth());
	final static int SCREEN_HEIGHT = (int) (screenSize.getHeight());
	final static int WIDTH = (int) (SCREEN_WIDTH / 2.5);
	final static int HEIGHT = SCREEN_HEIGHT / 2;

	final static int PREVIEW_WIDTH = (int) (SCREEN_WIDTH / 2.3);
	final static int PREVIEW_HEIGHT = (int) (SCREEN_HEIGHT / 1.1);

	final static int HEADER_TEXT_SIZE = (int) (SCREEN_HEIGHT / 25);
	final static int SUBHEADER_TEXT_SIZE = (int) (SCREEN_HEIGHT / 50);
	final static int PREVIEW_TEXT_SIZE = (int) (SCREEN_WIDTH / 120);

	final static int LARGE_INSET_SIZE = (int) (SCREEN_HEIGHT / 54);
	final static int MEDIUM_INSET_SIZE = (int) (SCREEN_HEIGHT / 73);
	final static int SMALL_INSET_SIZE = (int) (SCREEN_HEIGHT / 200);

	final static int BUTTON_X = (int) (SCREEN_WIDTH / 13);
	final static int BUTTON_Y = (int) (SCREEN_HEIGHT / 30);

	// All of the elements of the frames
	static JFrame frame;
	static JPanel panel;
	static JButton uploadButton;
	static JButton previewButton;
	static JButton saveButton;
	static JTextArea errorField;
	static JTextArea previewField;

	static GridBagConstraints gbc = new GridBagConstraints();

	public static void main(String[] args) {
		// Create the frame for the main menu
		frame = createNewFrame("PageChanger");

		// Create a panel with GridBagLayout
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		addTitleLabel(panel, "Page Changer", 2, 0);
		addLabel(panel, "Upload a txt file!", 2, 1);

		uploadButton = addButton(panel, "Upload", 0, 2);
		previewButton = addButton(panel, "Preview", 3, 2);

		// Adds functionality to the preview button
		previewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create the previewFrame, panel, and layout
				JFrame previewFrame = createNewFrame("Preview Page");
				JPanel panelPreview = new JPanel();
				panelPreview.setLayout(new GridBagLayout());
				JTextArea previewField = new JTextArea();

				// Adds the text field where the preview appears
				previewField = addTextField(panelPreview, 0, 2, "BLACK");
				previewField.setText(resultingText);

				// Add the save button to the top center of the frame
				saveButton = addButton(panelPreview, "Save", 0, 0);

				// Add functionality to the save button
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							writeFile(resultingText);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

				// Add panel to frame, set size, pack to fit components, and set to be visible
				previewFrame.add(panelPreview);
				previewFrame.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
				previewFrame.pack();
				previewFrame.setVisible(true);
			}

		});

		// Makes the preview button unable to be clicked initially
		previewButton.setEnabled(false);
		errorField = addTextField(panel, 0, 3, "RED");

		// Adds functionality to the upload button
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT File", "txt");

				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(chooser);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					// Text needs to output to error field for failed file

				} else {//

					try {
						String filepath = chooser.getSelectedFile().getAbsolutePath();

						String text = new String ( Files.readAllBytes( Paths.get(filepath) ) );
						text = text.replaceAll("\\p{javaSpaceChar}{2,}", " ");
						if (BUGS) {
							System.out.println();
							System.out.println(text);
						}

						formatText = new JFormatter(text);

						if (formatText.errors() == "") {
							// errorField.setForeground(Color.green);
							errorField.setText("All clear!");
							resultingText = formatText.format();
							previewButton.setEnabled(true);

							if (BUGS) {
								System.out.println(resultingText);
							}
						} else {
							errorField.setText(formatText.errors());

						}

					} catch (FileNotFoundException e1) {

						errorField.setText("File Not found");
						e1.printStackTrace();
					}
					catch (IOException e1) {
						errorField.setText("File Could Not Be Read");
						e1.printStackTrace();
					}

				}

			}

		});

		// Adds the panel to the frame
		frame.add(panel);

		// Debugging stuff TODO: Delete this when project is complete?
		/*
		 * System.out.println("Screen Width: "+WIDTH);
		 * System.out.println("Screen Height: "+HEIGHT);
		 * System.out.println("Header Size: "+HEADER_TEXT_SIZE);
		 * System.out.println("Subheader Size: "+SUBHEADER_TEXT_SIZE);
		 * System.out.println("Large Inset Size: "+LARGE_INSET_SIZE);
		 * System.out.println("Medium Inset Size: "+MEDIUM_INSET_SIZE);
		 * System.out.println("Small Inset Size: "+SMALL_INSET_SIZE);
		 * System.out.println("Button Width: "+BUTTON_X);
		 * System.out.println("Button Height: "+BUTTON_Y);
		 */

		// Set the size, pack the frame to fit the components, and make the frame
		// visible
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
		frame.setVisible(true);

	}

	/** previewWindow(): Creates the preview window */
	public static void previewWindow() {
		// Create the previewFrame, panel, and layout
		JFrame previewFrame = createNewFrame("Preview Page");
		JPanel panelPreview = new JPanel();
		panelPreview.setLayout(new GridBagLayout());
		JTextArea previewField = new JTextArea();

		// Adds the text field where the preview appears
		addTextField(panelPreview, 0, 2, "BLACK");

		// Add the save button to the top center of the frame
		saveButton = addButton(panelPreview, "Save", 0, 0);

		// Add functionality to the save button
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// writeFile(formatText.)
			}

		});

		// Add panel to frame, set size, pack to fit components, and set to be visible
		previewFrame.add(panelPreview);
		previewFrame.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
		previewFrame.pack();
		previewFrame.setVisible(true);
	}

	/**
	 * createNewFrame(): creates a frame
	 * 
	 * @param frameName
	 * @return frame
	 */
	public static JFrame createNewFrame(String frameName) {
		JFrame frame = new JFrame(frameName);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	/**
	 * addTitleLabel(): Creates the title text for the main menu
	 * 
	 * @param labelPanel:
	 *            the panel it is added to
	 * @param labelText:
	 *            the text the title contains
	 * @param x:
	 *            The x-axis grid location
	 * @param y:
	 *            The y-axis grid location
	 */
	public static void addTitleLabel(JPanel labelPanel, String labelText, int x, int y) {
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.0;

		// Padding and spacing
		gbc.insets = new Insets(MEDIUM_INSET_SIZE, MEDIUM_INSET_SIZE, 0, MEDIUM_INSET_SIZE);

		gbc.gridx = x;
		gbc.gridy = y;

		// Amount of x-axis grid boxes the label takes up
		gbc.gridwidth = 4;

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Skia", Font.BOLD, HEADER_TEXT_SIZE));
		labelPanel.add(label, gbc);
	}

	/**
	 * addLabel(): Creates the subtext for the main menu
	 * 
	 * @param labelPanel:
	 *            the panel it is added to
	 * @param labelText:
	 *            the text the label contains
	 * @param x:
	 *            The x-axis grid location
	 * @param y:
	 *            The y-axis grid location
	 */
	public static void addLabel(JPanel labelPanel, String labelText, int x, int y) {
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.0;

		gbc.gridx = x;
		gbc.gridy = y;

		// Amount of x-axis grid boxes the label takes up
		gbc.gridwidth = 4;
		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Skia", Font.PLAIN, SUBHEADER_TEXT_SIZE));
		labelPanel.add(label, gbc);
	}

	/**
	 * addButton(): Creates a button and adds it to a panel
	 * 
	 * @param panel:
	 *            the panel it is added to
	 * @param buttonText:
	 *            the text the button contains
	 * @param x:
	 *            The x-axis grid location
	 * @param y:
	 *            The y-axis grid location
	 * @return button
	 */
	public static JButton addButton(JPanel panel, String buttonText, int x, int y) {
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 0.0;
		gbc.weightx = 1.0;

		// Padding and spacing
		gbc.insets = new Insets(MEDIUM_INSET_SIZE, MEDIUM_INSET_SIZE, MEDIUM_INSET_SIZE, MEDIUM_INSET_SIZE);

		gbc.gridx = x;
		gbc.gridy = y;

		// X-Axis grid spaces the button takes up
		gbc.gridwidth = 3;

		// Dimensions for the button
		gbc.ipadx = BUTTON_X;
		gbc.ipady = BUTTON_Y;

		JButton button = new JButton(buttonText);
		button.setFont(new Font("Skia", Font.BOLD, SUBHEADER_TEXT_SIZE));
		panel.add(button, gbc);
		return button;
	}

	/**
	 * addTextField(): Creates a textField and adds it to a panel
	 * 
	 * @param panel:
	 *            the panel it is added to
	 * @param x:
	 *            The x-axis grid location
	 * @param y:
	 *            The y-axis grid location
	 * @param textColor
	 * @return frame
	 */
	public static JTextArea addTextField(JPanel panel, int x, int y, String textColor) {
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weighty = 1.0;

		// Padding and spacing
		gbc.insets = new Insets(SMALL_INSET_SIZE, SMALL_INSET_SIZE, SMALL_INSET_SIZE, SMALL_INSET_SIZE);

		gbc.gridx = x;
		gbc.gridy = y;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 6;

		gbc.ipady = 4000;

		JTextArea errorField = new JTextArea();
		errorField.setEditable(false);
		errorField.setLineWrap(true);
		errorField.setWrapStyleWord(true);

		if (textColor == "RED") {
			errorField.setFont(new Font("Skia", Font.PLAIN, SUBHEADER_TEXT_SIZE));
			errorField.setForeground(Color.red);
			errorField.setText("No Errors.");
		} else {
			errorField.setFont(new Font("Courier New", Font.PLAIN, PREVIEW_TEXT_SIZE));
			errorField.setForeground(Color.black);
		}

		JScrollPane errorPane = new JScrollPane(errorField);
		errorPane.getVerticalScrollBar().setPreferredSize(new Dimension(MEDIUM_INSET_SIZE, 0));
		panel.add(errorPane, gbc);
		return errorField;

	}

	public static void writeFile(String outList) throws IOException {
		File fout = new File("out.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (int i = 0; i < outList.length(); i++) {
			int counter = 0;

			bw.write(outList.charAt(i));
			if (counter >= 80) {
				bw.newLine();
			}

			counter++;
		}

		bw.close();
	}

}
// test 1
