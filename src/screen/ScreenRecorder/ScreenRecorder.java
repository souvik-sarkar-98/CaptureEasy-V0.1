package screen.ScreenRecorder;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;

public class ScreenRecorder extends Frame implements ActionListener,DocumentListener{

	private static final long serialVersionUID = 1L;
	Frame frame;
	JTextField inputFileName;
	JButton button_1, button_2;
	JLabel label;
	XMLSlideShow ppt=null;
	XSLFSlide slide=null;
	String documentPath=createFolder(System.getProperty("user.home")+"\\PPTs");
	boolean runFlag = false;

	/**
	 * Create the dialog.
	 * @throws IOException 
	 * @throws AWTException 
	 * @throws HeadlessException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws HeadlessException, AWTException, IOException, InterruptedException
	{
		ScreenRecorder sr=new ScreenRecorder();
		sr.setVisible(true);
		sr.setDocumentDestinationFolder("C:\\Users\\USER\\Desktop\\PPT");
		sr.start();
		Thread.sleep(10000);
		sr.stop();
		sr.save("souvik");
	}
	/*
	public ScreenRecorder() {
    	frame= new Frame();
    	setResizable(true);
    	setLayout(null);  
    	setVisible(true);
    	setAlwaysOnTop(true);
    	setSize(400,400);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screensize.width/2,screensize.height/2+100);
    	/*frame.addWindowListener(new WindowAdapter(){  
            public void windowClosing(WindowEvent e) {  
            	frame.dispose();  
            }  
        }); 
    	  
    	label=new JLabel("j");
    	label.setVisible(true);
    	label.setBounds(10,10,400,100);
        label.setLocation(20,10);
        frame.add(label); 
        
        inputFileName = new JTextField();
		inputFileName.setVisible(false);
		inputFileName.setEnabled(false);
		inputFileName.setEditable(false);
		inputFileName.getDocument().addDocumentListener(this);
		inputFileName.setBounds(10,10,400,100);
		inputFileName.setLocation(20,40);
		frame.add(inputFileName);
    	
		button_1 = new JButton("START");
    	button_1.setBounds(10,10,70,20);   
    	button_1.setLocation(20,95);
    	button_1.addActionListener(this);
    	frame.add(button_1);
    	
    	button_2 = new JButton("CANCEL");
    	button_2.setBounds(10,10,70,20);   
    	button_2.setLocation(90,95);
    	button_2.addActionListener(this);
    	frame.add(button_2);         
    }  
		
	*/	

	/***
	 * All Methods are written here
	 */
	public void setDocumentDestinationFolder(String path)
	{
		documentPath=createFolder(path);
	}
	private String createFolder(String path)
  	{
		try{
			createFolder(new File(path).getParent());
	  		if (! new File(path).exists())
	  		{
	  			new File(path).mkdir();
	  		}
		}
		catch(NullPointerException e){}
  		return path;
  	}
	void Button_1() {
		
	}


	void start() throws HeadlessException, AWTException, IOException, InterruptedException {
		System.out.println("Strting");
		ppt = new XMLSlideShow();
		runFlag = true;
		//label.setText("RECORDING...");
		captureScreen();
	}

	void captureScreen() throws HeadlessException, AWTException, IOException, InterruptedException {
		System.out.println("capturing init");
		int c=0;
		
		while (runFlag && c<50) {
			System.out.println("capturing");
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			File imgFile = new File("temp.png");
			ImageIO.write(image, "png", imgFile);
			XSLFSlide slide = ppt.createSlide();
			byte[] picture = IOUtils.toByteArray(new FileInputStream(imgFile));
			slide.createPicture(ppt.addPicture(picture, XSLFPictureData.PictureType.PNG));
			imgFile.delete();
			Thread.sleep(500);
			
			c++;
		}
	}

	void stop() {
		System.out.println("stopped");
		runFlag = false;
	}

	void save(String name) throws IOException {
		System.out.println("saving");
		File file = new File(documentPath+"\\"+name+".pptx");
		FileOutputStream out = new FileOutputStream(file);
		ppt.write(out);
		//label.setText("PPT is ready");
		out.close();
		System.out.println("Done");
	}

	void textArea_Manager()
	{
		String Name=inputFileName.getText();
		if (Name.equals(""))
		{
			//label.setText("Please enter a FileName");
			inputFileName.setBackground(Color.pink);
			inputFileName.requestFocusInWindow();
			inputFileName.setToolTipText("Please enter a FileName");
			button_1.setEnabled(false);
		}
		else if(Name.contains(Character.toString('"')) || Name.contains("/") ||Name.contains("\\") || Name.contains(":") || Name.contains("*")||Name.contains("?")||Name.contains("<")||Name.contains(">")||Name.contains("|"))
		{
			//label.setText("Wrong filename format");
			inputFileName.setBackground(Color.pink);
			inputFileName.requestFocusInWindow();
			inputFileName.setToolTipText("A file name can not contain any of the following characters: \\ / : * ? "+Character.toString('"')+" < > | " );
			button_1.setEnabled(false);
		}
		else if(new File(documentPath+"\\"+Name+".pptx").exists())
		{
			//label.setText("File '"+Name+".pptx' already exists");
			inputFileName.setToolTipText("File '"+Name+".pptx' already exists");
			inputFileName.setBackground(Color.pink);
			inputFileName.requestFocusInWindow();
			button_1.setEnabled(false);
		}
		else
		{
			label.setText("");
			inputFileName.setBackground(Color.WHITE);
			inputFileName.requestFocusInWindow();
			button_1.setEnabled(true);
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(button_1.isSelected())
		{
			System.out.println("button1 is clicked");
		}
		else if (button_2.isSelected())
		{
			System.out.println("button2 is clicked");
		}
		
	}
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
