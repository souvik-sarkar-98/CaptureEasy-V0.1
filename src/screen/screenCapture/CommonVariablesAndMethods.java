package screen.screenCapture;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;


public class CommonVariablesAndMethods
{
	public static String TempImageFolderPath=createFolder(System.getProperty("user.home")+"/CapturEasy/Temp");
	public static String Imageformat="png",ClipboardStatus="Useable",ErrorMessage="",Temp,DocumentPath;
	public static JLabel l1,l2,lError;  
	public static Frame f;
	public static JButton b1;
	public static boolean Runflag=true;
    public static int c=0,p=0;
	public static FileOutputStream out;
	public static XWPFDocument docx=null;
	public static XWPFRun run=null;
	public static Action action=null;
	public static JButton b2;
	public static boolean doubleClickAction=false;
	public static BufferedImage buffimage;
	public static boolean frameVisible=true;
	public static boolean stopThread=false; 
	
	/***
	 * 
	 * Primary Methods
	 * @return 
	 * 
	 ***/
	public static boolean captureScreen() throws InvalidFormatException
	{
		try
		{
			if (!frameVisible==true)
			{
				CommonVariablesAndMethods.f.setVisible(false);
				do{Thread.sleep(200);}while(f.isVisible());
			}
	        String screenshot_name =++c+ "."+Imageformat;
	        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        File file = new File(createFolder(TempImageFolderPath)+"\\" + screenshot_name);
	        ImageIO.write(image, Imageformat, file);
	        loadImagesRunTime(file);
		}
		catch(Exception e)	{	System.err.println("Exception at captureScreen method");	}
		CommonVariablesAndMethods.f.setVisible(true);
		return true;
	}
	
	public static void createNewWord(String DocumentPath,String testName) throws IOException, InvalidFormatException
	{
		try
		{
			XWPFDocument createNewWordDocument =new XWPFDocument();
			XWPFRun createNewWordRun=createNewWordDocument.createParagraph().createRun();
			LoadImages(TempImageFolderPath,createNewWordRun);
			FileOutputStream createNewWordOut=new FileOutputStream(subFolders(DocumentPath)+"\\"+testName+".docx");
			createNewWordDocument.write(createNewWordOut);
			createNewWordOut.flush();
			createNewWordOut.close();
			createNewWordDocument.close();
			createNewWordDocument=null;
			if(!(action.DefaultRadioOption==0))
			{
		        docx.close();
		        docx=null;
			}
	        DeleteScreenshots(TempImageFolderPath);	
		}
		catch(Exception e)	{	System.err.println("Exception at captureScreen method"); e.printStackTrace();	}
	}
	
	public static void addToExistingWord(File file) throws FileNotFoundException, InterruptedException 
	{
		FileOutputStream addToExistingWordOut;
		try
		{
			XWPFDocument addToExistingWordDocument = new XWPFDocument(new FileInputStream(file.getPath()));
			XWPFRun addToExistingWordRun = addToExistingWordDocument.getLastParagraph().createRun();
			LoadImages(TempImageFolderPath,addToExistingWordRun);
			if(action.chckbxNewCheckBox.isSelected())
			{
				addToExistingWordOut = new FileOutputStream(file.getPath());
			}
			else
			{
				addToExistingWordOut = new FileOutputStream(modifyFilePath(file));
			}
			
			addToExistingWordDocument.write(addToExistingWordOut);
			addToExistingWordOut.flush();
			addToExistingWordOut.close();
	        addToExistingWordDocument.close();
	        addToExistingWordDocument=null;
	        if(!(action.DefaultRadioOption==0))
			{
		        docx.close();
		        docx=null;
			}
	        DeleteScreenshots(TempImageFolderPath);
		}
        catch(Exception e){
        	action.chckbxNewCheckBox.setSelected(false);
        	Thread.sleep(2000);
        	addToExistingWord(file);
        }
	}
	
	public static void DeleteScreenshots(String path) throws FileNotFoundException  { 
		try
    	{
			do{
			    for (File file: new File(path).listFiles()) 
			    {
			    	file.delete();
			    }
			}while(!IsEmpty(TempImageFolderPath));
	    }
		catch(Exception e){	System.err.println("Exception while delete");}
	}

	public static void LoadImages(String path,XWPFRun run) throws InvalidFormatException, IOException
	{
		File[] files = new File(path).listFiles();
		Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int i = 0;
                try {
                    int e = name.lastIndexOf('.');
                    String number = name.substring(0, e);
                    i = Integer.parseInt(number);
                } catch(Exception e) {
                    i = 0; 
                }
                return i;
            }
        });
		for(File file:files)
		{
			InputStream pic = new FileInputStream(file.getPath());
			run.addBreak();
	        run.addPicture(pic, pictureType(), file.getName(), Units.toEMU(475), Units.toEMU(300));
	        pic.close();
		}
	}

	/***
	 * 
	 * Supporting Primary Methods 
	 * 
	 ***/
	
	public static void createNewWordRunTime(String Path,String testName) throws IOException, InvalidFormatException
	{
        out = new FileOutputStream(subFolders(Path)+"\\"+testName+".docx");
        docx.write(out);
        out.flush();
        out.close();
        docx.close(); 
        docx=null;
        DeleteScreenshots(TempImageFolderPath);
	}

	public static void loadImagesRunTime(File screenshot)
	{
		try{
        	InputStream pic = new FileInputStream(screenshot.getPath());
        	run.addBreak();
			run.addPicture(pic, pictureType(), screenshot.getName(), Units.toEMU(500), Units.toEMU(300));
            pic.close();
        } 
		catch(NullPointerException e)
        {
        	docx =new XWPFDocument();
        	run=docx.createParagraph().createRun();
        	loadImagesRunTime(screenshot);
        }
		catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int pictureType()
	{
		int type=XWPFDocument.PICTURE_TYPE_PNG;;
		if(Imageformat=="jpeg") 
			type= XWPFDocument.PICTURE_TYPE_JPEG;
		return type;
	}
	/***
	 * 
	 * Secondary Methods
	 * 
	 ***/
	
	public static String modifyFilePath(File file)
	{
		int newVersion=0;
		String modifiedFilewPath;
		do{
			newVersion++;
			modifiedFilewPath=file.getParent()+"\\"+file.getName().substring(0, file.getName().indexOf("."))+" ["+(newVersion)+"].docx";
		}
		while(new File(modifiedFilewPath).exists());
		return modifiedFilewPath;
	}
	
	public static void resetClipboard()
	{
		StringSelection stringSelection = new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection, null);
	}
	//Check whether a folder is empty or not
  	public static boolean IsEmpty(String Path) 
  	{    
  		int count=0;
  		boolean var=false;
  	      File directory = new File(Path);
  	      String[] a=directory.list(); 
  	      for(int i=0; i<a.length; i++)
  	      {
  	    	  if (a[i].contains(".jpeg") || a[i].contains(".jpg") || a[i].contains(".png") ||a[i].contains(".bmp"))
  	    	  {
  	    		  count++;
  	    	  }
  	      }
  	      if (count==0)
  	    	  var= true;
  	      else
  	    	  var=false;
  		  return var ;
  	}
	
	// Minimizing and Restoring Frame
	public static void Minimize()
	{
		f.setState ( Frame.ICONIFIED );
	}
	
	public static void Restore()
	{
		f.setState ( Frame.NORMAL );
	}
	
  	// Method Overloading
  	public static void updateLabel(Color color,String l2Text){
  		l1.setForeground(Color.BLACK);
  		l1.setText("("+c+")");
  		l2.setForeground(color);
		l2.setText(l2Text);
  	}
  	
  	public static void updateLabel(String l1Text,String l2Text){
  		l1.setForeground(Color.BLACK);
		l1.setText(l1Text);
		l2.setText(l2Text);
  	}
  	
  	public static void updateLabel(int l1Number,String ErrorMessage){
  		lError.setText(ErrorMessage);
  	}
  	
  	public static void updateLabel(String l1Text,String l2Text,String ErrorMessage){
  		l1.setForeground(Color.BLACK);
  		l1.setText(l1Text);
		l2.setText(l2Text);
		lError.setText(ErrorMessage);
  	}

  	
  	
  	public static File lastFileModified(String dir) {
  	    File fl = new File(dir);
  	    File[] files = fl.listFiles(new FileFilter() {          
  	        public boolean accept(File file) {
  	            return file.isFile();
  	        }
  	    });
  	    long lastMod = Long.MIN_VALUE;
  	    File choice = null;
  	    for (File file : files) {
  	        if (file.lastModified() > lastMod) {
  	            choice = file;
  	            lastMod = file.lastModified();
  	        }
  	    }
  	    return choice;
  	}
  	
  	public static String subFolders(String basepath)
  	{
  		String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
        return createFolder(createFolder(basepath+"\\"+month+" "+cal.get(Calendar.YEAR))+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH));
  	}
  	
  	public static String createFolder(String path)
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
  	
  	/***
  	 * 
  	 * 
  	 * 
  	 ***/
  	
  	public static final String SUN_JAVA_COMMAND = "sun.java.command";
  	
  	public static void restartApplication(Runnable runBeforeRestart) throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// execute some custom code before restarting
			if (runBeforeRestart!= null) {
				runBeforeRestart.run();
			}
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}
  	
}