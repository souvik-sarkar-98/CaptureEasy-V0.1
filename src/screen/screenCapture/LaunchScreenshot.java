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
			Screenshot sc=new Screenshot();
			sc.setDocumentDestinationFolder(getProperty("DocumentPath"));
			sc.setFrameVisibilityInScreenshot(false);
			sc.Launch();
		}
		else
		{
			String path=JOptionPane.showInputDialog("Enter Document Destination File path");
			updateProperty("DocumentPath",path.replaceAll("\\","/" ),"");
			
			Perform();

		}
	}
	
	public static void updateProperty(String Field,String value,String comment)
	{
		Properties properties = new Properties();
		properties.setProperty(Field, value);
		File file = new File(CommonVariablesAndMethods.createFolder(CommonVariablesAndMethods.PropertyFilePath));
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file,true);
			properties.store(fileOut, comment);
			fileOut.close();
		} catch (IOException e) {
			//(e.getClass().getName()+"  "+CommonVariablesAndMethods.PropertyFilePath+" Not found");
			new PopUp("ERROR","error",e.getClass().getName()+"  "+CommonVariablesAndMethods.PropertyFilePath+" Not found");
		}
	}
	/** 
	 * @Type: File Processing Method
	 * @name= updateProperty(String Field,String value)
	 */
	public static String getProperty(String key) 
	{
		String value="";
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(CommonVariablesAndMethods.PropertyFilePath));
			value=properties.getProperty(key);
			if(value==null)
			{
				//("Error:  "+key+" Not found. Please update Path.properties file.");
				new PopUp("ERROR","error"," "+key+" Not found. Please update Path.properties file.");
			}
			else if(value.equalsIgnoreCase(""))
			{
				//("Error:  "+key+" is Empty. Please update Path.properties file.");
				new PopUp("ERROR","error"," "+key+" is Empty. Please update Path.properties file.");
			}
		} catch (IOException e) {
			new PopUp("ERROR","error",e.getClass().getName()+"  "+CommonVariablesAndMethods.PropertyFilePath+" Not found");
		}
		return value;
	}
}
