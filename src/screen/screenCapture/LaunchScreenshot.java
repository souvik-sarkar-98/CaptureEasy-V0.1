package screen.screenCapture;

import java.util.*;

import javax.swing.JOptionPane;

import java.io.*; 



public class LaunchScreenshot {
	public static void main(String args[]) throws Exception
	{
		/*Screenshot sc=new Screenshot();
		sc.setTemporaryScreenshotFolder("\\\\omsmds001\\isd\\TCS Testing Team\\Souvik Sarkar\\temp");
		sc.setDocumentDestinationFolder("\\\\omsmds001\\isd\\TCS Testing Team\\Souvik Sarkar");

		sc.setFrameVisibilityInScreenshot(false);
		sc.Launch();*/
		
		Perform();
	}
	public static void Perform() throws Exception
	{
		String Propertiesfilepath=CommonVariablesAndMethods.createFolder(System.getProperty("user.home")+"/CaptureEasy/Config")+"/SystemPath.properties";
		if(new File(Propertiesfilepath).exists())
		{
			FileReader reader=new FileReader(Propertiesfilepath); 
			Properties p=new Properties();  
			p.load(reader);  
			System.out.println(p.getProperty("db.filepath"));
			if(!(p.getProperty("db.filepath").equals("")))
			{

				Screenshot sc=new Screenshot();

				//sc.setTemporaryScreenshotFolder(p.getProperty("TempPath"));
				sc.setDocumentDestinationFolder(p.getProperty("db.filepath"));

				sc.setFrameVisibilityInScreenshot(false);
				sc.Launch();
			}
			else
			{
				System.out.println("Error");
			}
		}
		else
		{
			try {
				Properties properties = new Properties();
				properties.setProperty("db.filepath", JOptionPane.showInputDialog("Enter Document Destination File path"));
				

				File file = new File(Propertiesfilepath);
				FileOutputStream fileOut = new FileOutputStream(file);
				properties.store(fileOut, "SystemPaths");
				fileOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Perform();

		}
	}
}
