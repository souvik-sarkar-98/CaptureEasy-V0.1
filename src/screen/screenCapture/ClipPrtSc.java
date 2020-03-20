package screen.screenCapture;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ClipPrtSc implements Runnable
{
	public static int n=0;
	// Runnable Invoked Method
	@Override
	public void run() 
	{
		Thread.currentThread().setUncaughtExceptionHandler(new Handler());
		try {
			MakeImage();
		} catch (HeadlessException | AWTException e) { e.printStackTrace();}
	}

	//Function to convert Clipboard data to image (Loop) 
	public void MakeImage() throws HeadlessException, AWTException  
	{   
		while(! CommonVariablesAndMethods.stopThread)
		{
			while(CommonVariablesAndMethods.Runflag && ! CommonVariablesAndMethods.stopThread)
			{
				if (CommonVariablesAndMethods.c==0)
				{
					try{CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);}
					catch(Exception e1){}
				}

				//if (CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) || CommonVariablesAndMethods.c > 0)
				{   

					Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null); // main exception point
					try {
						CommonVariablesAndMethods.Temp=(String) content.getTransferData(DataFlavor.stringFlavor);
					} catch (UnsupportedFlavorException | IOException e1) {}
					if (content.isDataFlavorSupported(DataFlavor.imageFlavor) && !content.isDataFlavorSupported(DataFlavor.stringFlavor) )
					{
						try {
							CommonVariablesAndMethods.b2.setEnabled(false);
							CommonVariablesAndMethods.updateLabel(Color.RED,"BUSY");
							CommonVariablesAndMethods.captureScreen();
							CommonVariablesAndMethods.ClipboardStatus="Useable";
							CommonVariablesAndMethods.updateLabel(CommonVariablesAndMethods.c,"");
						} catch (InvalidFormatException e) {
							e.printStackTrace();

						}
						CommonVariablesAndMethods.resetClipboard();
						if (CommonVariablesAndMethods.ClipboardStatus !="Blocked")
						{
							StringSelection stringSelection1 = new StringSelection(CommonVariablesAndMethods.Temp);
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection1, null);
						}
					}


					try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
					CommonVariablesAndMethods.b2.setEnabled(true);
					CommonVariablesAndMethods.updateLabel(Color.GREEN,"READY");
					if (CommonVariablesAndMethods.ClipboardStatus=="Useable" )
					{ 
						CommonVariablesAndMethods.updateLabel(CommonVariablesAndMethods.c,"");
						if (CommonVariablesAndMethods.c!=0){
							CommonVariablesAndMethods.updateLabel(Color.GREEN,"READY");
						}
					}
				}

				if (CommonVariablesAndMethods.ClipboardStatus=="Blocked" )
				{
					Transferable Data=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null );
					ImageSelection trans = new ImageSelection(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) trans,null );
					CommonVariablesAndMethods.updateLabel(CommonVariablesAndMethods.c,"Clipboard\n Unavailable");
					CommonVariablesAndMethods.Restore();
					Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
					if (content.isDataFlavorSupported(DataFlavor.imageFlavor) && !content.isDataFlavorSupported(DataFlavor.stringFlavor) )
					{
						CommonVariablesAndMethods.ClipboardStatus="Useable";
						CommonVariablesAndMethods.updateLabel(CommonVariablesAndMethods.c,"");
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(Data, null);
					}
				}


				if(CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath))
				{
					CommonVariablesAndMethods.b2.setEnabled(false);
				}
				else
				{
					CommonVariablesAndMethods.b2.setEnabled(true);
				}

				if (!CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c > 0)
				{
					CommonVariablesAndMethods.updateLabel(Color.GREEN,"READY");
				}
			}
			if (CommonVariablesAndMethods.b1.getText().equalsIgnoreCase("RESUME"))
			{
				CommonVariablesAndMethods.updateLabel(Color.RED,"PAUSED");
				CommonVariablesAndMethods.b2.setEnabled(false);
			}
			else
			{
				CommonVariablesAndMethods.b2.setEnabled(true);
				CommonVariablesAndMethods.updateLabel(Color.RED,"BUSY");
			}
			try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

}

