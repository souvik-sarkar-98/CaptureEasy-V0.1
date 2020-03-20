package screen.screenCapture;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.JCheckBox;

public class Action extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926605251006631258L;
	public final JPanel contentPanel = new JPanel();
	public JTextField textField;
	public  JButton btnChooseFile,okButton,cancelButton;
	public  JRadioButton rdbtnNewRadioButton,rdbtnImportScreenshotsTo,rdbtnDeleteScreenshots;
	public  String existingfilepath="",newFileName="";
	public  JLabel lblNewLabel_1;
	public  JTextArea textArea_1;
	public  JTextArea textArea_3;
	public Color chooseFileButtonColor;
	Dimension screensize;
	public  JTextArea textArea_2;
	public  int DefaultRadioOption;
	public  JCheckBox chckbxNewCheckBox;
	private JRadioButton rdbtnViewScreenshots;

	/**
	 * Create the dialog.
	 */
	
	public Action(int DefaultRadioOption) {
		this.DefaultRadioOption=DefaultRadioOption;
		setTitle("  Action");
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 20));
		setAlwaysOnTop(true);
		screensize=Toolkit.getDefaultToolkit().getScreenSize();
		if(DefaultRadioOption==0)
			setBounds(100, 100, 530,315);
		else
			setBounds(100, 100, 515,315);
		setLocation(screensize.width/2-300,screensize.height/2-300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				
			}
		}
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			JLabel lblPleaseSelect = new JLabel("Please Select :");
			lblPleaseSelect.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblPleaseSelect);
		}
		{
			rdbtnImportScreenshotsTo = new JRadioButton();
			if(DefaultRadioOption==0)
				rdbtnImportScreenshotsTo.setText("Import Last Screenshots to New Microsoft Word Document ");
			else
				rdbtnImportScreenshotsTo.setText("Import Screenshots to New Microsoft Word Document ");
			rdbtnImportScreenshotsTo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnChooseFile.setEnabled(false);
					textField.setEnabled(true);
					textField.requestFocusInWindow();
					textArea_1_Manager();
					btnChooseFile.setBackground(chooseFileButtonColor);
					textArea_RemoveText(textArea_2);
					existingfilepath="";
					okButton.setText("Ok");
					cancelButton.setText("Cancel");
					textArea_RemoveText(textArea_3);
					chckbxNewCheckBox.setVisible(false);
					okButton.setEnabled(true);
					if(DefaultRadioOption==0 && cancelButton.getText().equalsIgnoreCase("Cancel"))
						cancelButton.setEnabled(false);
					else
						cancelButton.setEnabled(true);
				}
			});
			rdbtnImportScreenshotsTo.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(rdbtnImportScreenshotsTo);
		}
		{
			JLabel lblNewLabel = new JLabel("Filename : ");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setToolTipText("Enter filename");
			textField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent e) {
					textArea_1_Manager();
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					textArea_1_Manager();
				}
				@Override
				public void removeUpdate(DocumentEvent e) {
					textArea_1_Manager();
				}
				
				});
			
			textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(textField);
			textField.setColumns(25);
		}
		{
			rdbtnNewRadioButton = new JRadioButton();
			if(DefaultRadioOption==0)
				rdbtnNewRadioButton.setText("Add Last Screenshots to Existing Microsoft Word Document");
			else
				rdbtnNewRadioButton.setText("Add Screenshots to Existing Microsoft Word Document");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textField.setEnabled(false);
					btnChooseFile.setEnabled(true);
					textArea_RemoveText(textArea_1);
					textField.setBackground(Color.WHITE);
					okButton.setText("Ok");
					cancelButton.setText("Cancel");
					textArea_RemoveText(textArea_3);
					okButton.setEnabled(true);
					if(DefaultRadioOption==0 && cancelButton.getText().equalsIgnoreCase("Cancel"))
						cancelButton.setEnabled(false);
					else
						cancelButton.setEnabled(true);
				}
			});
			{
				textArea_1 = new JTextArea();
				//textArea_1.setRows(0);
				textArea_1.setColumns(0);
				textArea_1.setEditable(false);
				textArea_1.setForeground(Color.RED);
				textArea_1.setBackground(UIManager.getColor("CheckBox.background"));
				textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 16));
				contentPanel.add(textArea_1);
			}
			rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(rdbtnNewRadioButton);
		}
		{
			btnChooseFile = new JButton("Choose File");
			btnChooseFile.setToolTipText("Click here");
			btnChooseFile.setEnabled(false);
			btnChooseFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAlwaysOnTop(false);
					final FileDialog fileDialog = new FileDialog(new Frame(),"Choose File");
					fileDialog.setAlwaysOnTop(true);
					fileDialog.setFile("*.docx;*.doc");
					fileDialog.setVisible(true);
					existingfilepath=fileDialog.getDirectory()+fileDialog.getFile();
					setAlwaysOnTop(true);
					if ( existingfilepath.equalsIgnoreCase("nullnull") || existingfilepath.equals(""))
					{
						chckbxNewCheckBox.setVisible(false);
						btnChooseFile.setBackground(Color.pink);
						btnChooseFile.requestFocusInWindow();
						textArea_AddText(textArea_2,"Please choose a file!!",Color.RED);
					}
					else
					{
						File file=new File(existingfilepath);
						if (!file.renameTo(file))
						{
							chckbxNewCheckBox.setVisible(false);
							textArea_AddText(textArea_2,"Selected file '"+adjustName(file)+"' is open for editing. Please close the file to overwrite. Otherwise a copy will be created.  ",Color.RED);
						}
						else
						{
							chckbxNewCheckBox.setVisible(true);
							btnChooseFile.setBackground(chooseFileButtonColor);
							textArea_AddText(textArea_2,"Selected Path: "+file.getParent()+"\\"+adjustName(file),Color.BLUE);
						}
					}
				}

				public String adjustName(File file) {
					String str=file.getName().substring(0, file.getName().indexOf("."));
					if(str.length()>8)
					{
						str=str.substring(0, 8)+"... "+file.getName().substring(file.getName().indexOf("."),file.getName().length());
					}
					else
					{
						str=str+file.getName().substring(file.getName().indexOf("."));
					}
					return str;
				}
			});
			btnChooseFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
			chooseFileButtonColor=btnChooseFile.getBackground();
			contentPanel.add(btnChooseFile);
		}
		{
			chckbxNewCheckBox = new JCheckBox("Overwrite selected file");
			chckbxNewCheckBox.setVisible(false);
			chckbxNewCheckBox.setSelected(true);
			chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(chckbxNewCheckBox);
		}
		{
			textArea_2 = new JTextArea();
			//textArea_2.setRows(0);
			textArea_2.setColumns(0);
			textArea_2.setEditable(false);
			textArea_2.setForeground(Color.BLUE);
			textArea_2.setFont(new Font("Monospaced", Font.PLAIN, 16));
			textArea_2.setBackground(UIManager.getColor("CheckBox.background"));
			contentPanel.add(textArea_2);
		}
		{
			rdbtnDeleteScreenshots = new JRadioButton();
			if(DefaultRadioOption==0)
				rdbtnDeleteScreenshots.setText("Delete Last Screenshots                                                        ");
			else
				rdbtnDeleteScreenshots.setText("Delete Screenshots                                                        ");
			rdbtnDeleteScreenshots.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textField.setEnabled(false);
					btnChooseFile.setEnabled(false);
					textArea_RemoveText(textArea_1);
					textField.setBackground(Color.WHITE);
					btnChooseFile.setBackground(chooseFileButtonColor);
					textArea_RemoveText(textArea_2);
					existingfilepath="";
					textArea_AddText(textArea_3,"Are you sure that you want to permanently delete all captured screenshots ?",Color.BLUE);
					okButton.setText("Yes");
					cancelButton.setText("No");
					chckbxNewCheckBox.setVisible(false);
					okButton.setEnabled(true);
					if(DefaultRadioOption==0 && cancelButton.getText().equalsIgnoreCase("Cancel"))
						cancelButton.setEnabled(false);
					else
						cancelButton.setEnabled(true);
				}
			});
			{
				rdbtnViewScreenshots = new JRadioButton();
				if(DefaultRadioOption==0)
					rdbtnViewScreenshots.setText("View Last Screenshots                                                            ");
				else
					rdbtnViewScreenshots.setText("View Screenshots                                                            ");
				rdbtnViewScreenshots.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						textField.setEnabled(false);
						btnChooseFile.setEnabled(false);
						textArea_RemoveText(textArea_1);
						textField.setBackground(Color.WHITE);
						btnChooseFile.setBackground(chooseFileButtonColor);
						textArea_RemoveText(textArea_2);
						textArea_RemoveText(textArea_3);
						existingfilepath="";
						okButton.setText("Ok");
						cancelButton.setText("Cancel");
						chckbxNewCheckBox.setVisible(false);
						okButton.setEnabled(true);
						if(DefaultRadioOption==0 && cancelButton.getText().equalsIgnoreCase("Cancel"))
							cancelButton.setEnabled(false);
						else
							cancelButton.setEnabled(true);
							
					}
				});
				rdbtnViewScreenshots.setFont(new Font("Tahoma", Font.BOLD, 16));
				contentPanel.add(rdbtnViewScreenshots);
			}
			rdbtnDeleteScreenshots.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(rdbtnDeleteScreenshots);
		}
		
		
		ButtonGroup g =new ButtonGroup();
		g.add(rdbtnDeleteScreenshots);
		g.add(rdbtnImportScreenshotsTo);
		g.add(rdbtnNewRadioButton);
		g.add(rdbtnViewScreenshots);
		{
			textArea_3 = new JTextArea();
			//textArea_3.setRows(0);
			textArea_3.setColumns(0);
			textArea_3.setLineWrap(false);
			textArea_3.setForeground(Color.BLUE);
			textArea_3.setFont(new Font("Monospaced", Font.PLAIN, 16));
			textArea_3.setEditable(false);
			textArea_3.setBackground(UIManager.getColor("CheckBox.background"));
			contentPanel.add(textArea_3);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setSelected(true);
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (rdbtnImportScreenshotsTo.isSelected())
						{
							newFileName=textField.getText();
							if (newFileName.equals(""))
							{
								textField.setBackground(Color.pink);
								textField.requestFocusInWindow();
								textArea_AddText(textArea_1,"Please Enter a file name !!",Color.RED);
							}
							else if((newFileName.contains(Character.toString('"')) ||newFileName.contains("/") ||newFileName.contains("\\") || newFileName.contains(":") || newFileName.contains("*")||newFileName.contains("?")||newFileName.contains("<")||newFileName.contains(">")||newFileName.contains("|")))
							{
								textField.setBackground(Color.pink);
								textField.requestFocusInWindow();
								textArea_AddText(textArea_1,"A file name can not contain any of the following characters: \\ / : * ? "+Character.toString('"')+" < > |",Color.RED);
							}
							else if(new File(CommonVariablesAndMethods.subFolders(CommonVariablesAndMethods.DocumentPath)+"\\"+newFileName+".docx").exists())
							{
								textField.setBackground(Color.pink);
								textField.requestFocusInWindow();
								textArea_AddText(textArea_1,"File '"+newFileName+".docx' already exists at "
										+ new File(CommonVariablesAndMethods.subFolders(CommonVariablesAndMethods.DocumentPath)+"\\"+newFileName+".docx").getPath(),Color.RED);
							}
							else
							{
								try 
								{
									setTitle("   Processing, please wait..."+"          ");
									Thread.sleep(500);
									if (DefaultRadioOption==0)
									{
										CommonVariablesAndMethods.createNewWord(CommonVariablesAndMethods.DocumentPath,newFileName);
									}
									else
									{
										CommonVariablesAndMethods.createNewWordRunTime(CommonVariablesAndMethods.DocumentPath,newFileName);
									}
									CommonVariablesAndMethods.c=0;
									setTitle("   Completed, Now file is ready to use !!"+"        ");
									Thread.sleep(500);
									dispose();
									CommonVariablesAndMethods.resetClipboard();
									CommonVariablesAndMethods.Runflag=true;
									CommonVariablesAndMethods.f.setVisible(true);
								} 
								
								catch (IOException | InvalidFormatException | InterruptedException e1) { 
									e1.printStackTrace(); 
									setTitle(" EXCEPTION : Service Interrupted ");
								}
							}
						}
						else if (rdbtnNewRadioButton.isSelected())
						{
							if ( existingfilepath.equalsIgnoreCase("nullnull") || existingfilepath.equals(""))
							{
								btnChooseFile.setBackground(Color.pink);
								btnChooseFile.requestFocusInWindow();
								textArea_AddText(textArea_2,"Please choose a file!!",Color.RED);
							}
							else
							{
								btnChooseFile.setBackground(chooseFileButtonColor);
								textArea_AddText(textArea_2,existingfilepath,Color.BLUE);
								try{
									setTitle("   Processing, please wait..."+"          ");
									buttonPane.repaint();
									Thread.sleep(500);
									CommonVariablesAndMethods.addToExistingWord(new File(existingfilepath));
									CommonVariablesAndMethods.c=0;
									setTitle("    Completed, Now file is ready to use !!"+"        ");
									Thread.sleep(500);
									dispose();
									CommonVariablesAndMethods.resetClipboard();
									CommonVariablesAndMethods.Runflag=true;
									CommonVariablesAndMethods.f.setVisible(true);
								}
								catch(InterruptedException | FileNotFoundException e1){ e1.printStackTrace(); }
								
							}
						}
						else if (rdbtnDeleteScreenshots.isSelected())
						{
							try {
								setTitle("   Processing, please wait..."+"          ");
								Thread.sleep(100);
								CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);
								CommonVariablesAndMethods.c=0;
								if(DefaultRadioOption!=0)
								{
									CommonVariablesAndMethods.docx.close();
									CommonVariablesAndMethods.docx=null;
								}
								setTitle("   Successfully deleted !!"+"        ");
								Thread.sleep(500);
								dispose();
								CommonVariablesAndMethods.resetClipboard();
								CommonVariablesAndMethods.Runflag=true;
								CommonVariablesAndMethods.f.setVisible(true);
								
								
							} catch (InterruptedException | IOException e1) {
							}
						}
						else if (rdbtnViewScreenshots.isSelected())
						{
							try {
								if(!(DefaultRadioOption==0))
								{
									Desktop.getDesktop().open(new File(CommonVariablesAndMethods.TempImageFolderPath));
									dispose();
									CommonVariablesAndMethods.Runflag=true;
									CommonVariablesAndMethods.f.setVisible(true);
								}
								else
								{
									setLocation(screensize.width/2+300,screensize.height/4-250);
									Desktop.getDesktop().open(new File(CommonVariablesAndMethods.TempImageFolderPath));
									g.clearSelection();
									okButton.setEnabled(false);
									cancelButton.setEnabled(false);
								}
									
							} catch ( IOException e1) {
							}
							
						}
						
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(DefaultRadioOption!=0 && cancelButton.getText().equalsIgnoreCase("Cancel"))
						{
							
							dispose();
							CommonVariablesAndMethods.resetClipboard();
							CommonVariablesAndMethods.Runflag=true;
							CommonVariablesAndMethods.f.setVisible(true);
							
						}
						else if(cancelButton.getText().equalsIgnoreCase("No"))
						{
							textArea_RemoveText(textArea_3);
							g.clearSelection();
							okButton.setText("Ok");
							cancelButton.setText("Cancel");
							okButton.setEnabled(false);
							cancelButton.setEnabled(false);
							
						}
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		if(DefaultRadioOption==0)
		{
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			textField.setEnabled(false);
			rdbtnImportScreenshotsTo.setSelected(false);
			rdbtnNewRadioButton.setSelected(false);
			rdbtnDeleteScreenshots.setSelected(false);
			okButton.setEnabled(false);
			cancelButton.setEnabled(false);
			
		}
		else if (DefaultRadioOption==1)
		{
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			rdbtnImportScreenshotsTo.setSelected(true);
			rdbtnNewRadioButton.setSelected(false);
			rdbtnDeleteScreenshots.setSelected(false);
			CommonVariablesAndMethods.Runflag=false;
			CommonVariablesAndMethods.f.setVisible(false);
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
		}
		else if (DefaultRadioOption==2)
		{
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			rdbtnImportScreenshotsTo.setSelected(false);
			rdbtnNewRadioButton.setSelected(true);
			rdbtnDeleteScreenshots.setSelected(false);
			CommonVariablesAndMethods.Runflag=false;
			CommonVariablesAndMethods.f.setVisible(false);
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
		}
		else if (DefaultRadioOption==3)
		{
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			rdbtnImportScreenshotsTo.setSelected(false);
			rdbtnNewRadioButton.setSelected(false);
			rdbtnDeleteScreenshots.setSelected(true);
			CommonVariablesAndMethods.Runflag=false;
			CommonVariablesAndMethods.f.setVisible(false);
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
		}
	}
	

	/***
	 * All Methods are written here
	 */
	public void textArea_1_Manager()
	{
		newFileName=textField.getText();
		if(newFileName.contains(Character.toString('"')) || newFileName.contains("/") ||newFileName.contains("\\") || newFileName.contains(":") || newFileName.contains("*")||newFileName.contains("?")||newFileName.contains("<")||newFileName.contains(">")||newFileName.contains("|"))
		{
			textField.setBackground(Color.pink);
			textField.requestFocusInWindow();
			textArea_AddText(textArea_1,"A file name can not contain any of the following characters: \\ / : * ? "+Character.toString('"')+" < > | ",Color.RED);
		}
		else if(new File(CommonVariablesAndMethods.subFolders(CommonVariablesAndMethods.DocumentPath)+"\\"+newFileName+".docx").exists())
		{
			textField.setBackground(Color.pink);
			textField.requestFocusInWindow();
			textArea_AddText(textArea_1,"File '"+newFileName+".docx' already exists at "
					+ new File(CommonVariablesAndMethods.subFolders(CommonVariablesAndMethods.DocumentPath)+"\\"+newFileName+".docx").getParent(),Color.RED);
		}
		else
		{
			textField.setBackground(Color.WHITE);
			textField.requestFocusInWindow();
			textArea_RemoveText(textArea_1);
		}
	}
	public void textArea_AddText(JTextArea textArea,String text,Color color)
	{
		textArea.setForeground(color);
		textArea.setRows(2);
		textArea.setColumns(45);
		if(DefaultRadioOption==0)
			setSize(530, 315+textArea.getRows()*20);
		else
			setSize(515, 315+textArea.getRows()*20);
		textArea.setLineWrap(true);
		textArea.setText(text);
	}
	public void textArea_RemoveText(JTextArea textArea)
	{
		textArea.setRows(0);
		textArea.setColumns(0);
		if(DefaultRadioOption==0)
			setSize(530, 315+textArea.getRows()*20);
		else
			setSize(515, 315+textArea.getRows()*20);
		textArea.setLineWrap(false);
		textArea.setText("");
	}
}

