package screen.screenCapture;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class PrtScScreenshot extends Frame implements MouseListener,ActionListener
{  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Constructor 
	public  PrtScScreenshot()
	{
		setResizable(false);
		setAlwaysOnTop(true);
		CommonVariablesAndMethods.f= new Frame();
		CommonVariablesAndMethods.f.setResizable(false);
		CommonVariablesAndMethods.f.setLayout(null);  
		CommonVariablesAndMethods.f.setVisible(true);
		CommonVariablesAndMethods.f.setAlwaysOnTop(true);
		CommonVariablesAndMethods.f.setSize(190,130);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		CommonVariablesAndMethods.f.setLocation(screensize.width-160,screensize.height/2+100);
		CommonVariablesAndMethods.f.addWindowListener(new WindowAdapter(){  
			public void windowClosing(WindowEvent e) {  
				if (CommonVariablesAndMethods.c==0 && CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath))
				{
					CommonVariablesAndMethods.stopThread=true;
					CommonVariablesAndMethods.f.dispose();
				}
				else
				{	
					CommonVariablesAndMethods.l2.setText("");
					CommonVariablesAndMethods.l1.setForeground(Color.RED);
					CommonVariablesAndMethods.l1.setText("PLEASE SAVE YOUR ");
					CommonVariablesAndMethods.lError.setText("WORK BEFORE CLOSE");
				}
			}  
		}); 
		CommonVariablesAndMethods.f.addMouseListener(this); 

		CommonVariablesAndMethods.l1=new JLabel();
		CommonVariablesAndMethods.l1.setBounds(10,10,300,100);
		CommonVariablesAndMethods.l1.setLocation(20,10);
		CommonVariablesAndMethods.f.add(CommonVariablesAndMethods.l1);

		CommonVariablesAndMethods.l2=new JLabel();
		CommonVariablesAndMethods.l2.setBounds(10,10,100,100);
		CommonVariablesAndMethods.l2.setLocation(60, 10);
		CommonVariablesAndMethods.f.add(CommonVariablesAndMethods.l2);

		CommonVariablesAndMethods.lError=new JLabel(); 
		CommonVariablesAndMethods.lError.setBounds(10,10,300,100);
		CommonVariablesAndMethods.lError.setLocation(20,30);
		CommonVariablesAndMethods.lError.setForeground(Color.RED);
		CommonVariablesAndMethods.f.add(CommonVariablesAndMethods.lError);

		CommonVariablesAndMethods.b2 = new JButton("ACTION");
		CommonVariablesAndMethods.b2.setFont(new Font("Calibri",Font.BOLD,13));
		CommonVariablesAndMethods.b2.setBounds(2,2,80,20);   
		CommonVariablesAndMethods.b2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( CommonVariablesAndMethods.c!=0)
				{
					try {
						Thread.sleep(100);
						CommonVariablesAndMethods.updateLabel("ACTION PERFORMED","");
						CommonVariablesAndMethods.action=new Action(1);
						CommonVariablesAndMethods.action.setVisible(true);
						CommonVariablesAndMethods.action.textField.requestFocusInWindow();
					} catch (InterruptedException e) {		}

				}
				else
				{
					try{CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);}
					catch(FileNotFoundException e1){}
				}

			}

		});
		CommonVariablesAndMethods.f.add(CommonVariablesAndMethods.b2);
		CommonVariablesAndMethods.b2.setLocation(10,95);

		CommonVariablesAndMethods.b1 = new JButton("PAUSE");
		CommonVariablesAndMethods.b1.setFont(new Font("Calibri",Font.BOLD,13));
		CommonVariablesAndMethods.b1.setBounds(2,2,90,20);   
		CommonVariablesAndMethods.b1.addActionListener( this);
		CommonVariablesAndMethods.f.add(CommonVariablesAndMethods.b1);
		CommonVariablesAndMethods.b1.setLocation(90,95);

		CommonVariablesAndMethods.updateLabel(Color.GREEN,"READY");
		try {  Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		// CommonVariablesAndMethods.Minimize();

	}  


	// MouseListener Invoked Method
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) || CommonVariablesAndMethods.c > 0) 
		{
			CommonVariablesAndMethods.p=e.getClickCount();
			if (CommonVariablesAndMethods.p==1)
			{
				try {
					if (!CommonVariablesAndMethods.frameVisible==true)
					{
						Thread.sleep(300);
					}
					CommonVariablesAndMethods.captureScreen();
				} 
				catch (InvalidFormatException | InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}
			//this part gives bug on double click at c=0; a redundant file generated which was locked by system. implementing Action button instead of this

			if (CommonVariablesAndMethods.p==2 && CommonVariablesAndMethods.doubleClickAction)
			{ 
				File f=CommonVariablesAndMethods.lastFileModified(CommonVariablesAndMethods.TempImageFolderPath);
				do{
					f.delete();
				}
				while(f.exists());
				CommonVariablesAndMethods.c=CommonVariablesAndMethods.c-1;
				//System.out.println(CommonVariablesAndMethods.c);
				if( CommonVariablesAndMethods.c!=0)
				{
					CommonVariablesAndMethods.updateLabel("ACTION PERFORMED","");
					CommonVariablesAndMethods.action=new Action(1);
					CommonVariablesAndMethods.action.setVisible(true);
					CommonVariablesAndMethods.action.textField.requestFocusInWindow();
				}
				else
				{
					try{CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);}
					catch(FileNotFoundException e1){}
				}
			}	
		}
	}

	// MouseListener Invoked Method
	@Override
	public void mouseEntered(MouseEvent e) 
	{	
		if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) 
		{
			try {
				CommonVariablesAndMethods.restartApplication(new RestartApplication());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	} 

	// MouseListener Invoked Method
	@Override
	public void mouseExited(MouseEvent e) 
	{
		if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
			try {
				CommonVariablesAndMethods.restartApplication(new RestartApplication());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}  

	// MouseListener Invoked Method
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
			try {
				CommonVariablesAndMethods.restartApplication(new RestartApplication());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	} 

	// MouseListener Invoked Method
	@Override
	public void mouseReleased(MouseEvent e) {
		if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
			try {
				CommonVariablesAndMethods.restartApplication(new RestartApplication());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	} 

	// Method to take screenshot 

	@Override
	public void actionPerformed(ActionEvent e) {
		if (CommonVariablesAndMethods.Runflag==true)
		{
			CommonVariablesAndMethods.Runflag=false;
			CommonVariablesAndMethods.updateLabel(Color.RED, "PAUSED");
			CommonVariablesAndMethods.b1.setText("RESUME");
		}
		else if (CommonVariablesAndMethods.Runflag==false)
		{
			CommonVariablesAndMethods.Runflag=true;
			StringSelection stringSelection = new StringSelection(CommonVariablesAndMethods.Temp);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection, null);
			CommonVariablesAndMethods.b1.setText("PAUSE");
			CommonVariablesAndMethods.updateLabel(Color.GREEN, "PAUSED");
		}
	}
}	

