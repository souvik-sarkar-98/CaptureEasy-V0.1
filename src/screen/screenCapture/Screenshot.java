package screen.screenCapture;


public class Screenshot {
	
    public void Launch() throws Exception 
	{  
    	/***
    	 	System.out.println("INFO:\n\n"
				+ " 'setDocumentDestinationFolder(String documentDestinationFolderPath): void - Screenshot'  set document destination folder\n"
				+ " 'setTemporaryScreenshotFolder(String temporaryScreenshotFolderPath): void - Screenshot' set temporary screenshot folder path\n"
				+ " 'setPictureType(String imageformat): void - Screenshot' set image format of screenshots\n"
				+ " 'getTemporaryScreenshotFolder(): String - Screenshot' return temporary screenshot folder path\n"
				+ " 'getDefaultTemporaryScreenshotFolder(): String - Screenshot' return default temporary screenshot folder path\n"
				+ " 'getDefaultPictureType(): String - Screenshot' return default image format\n\n\n");
		*/
    	if (!(CommonVariablesAndMethods.DocumentPath==null))
    	{
    		CommonVariablesAndMethods.resetClipboard();
            if (! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.createFolder(CommonVariablesAndMethods.TempImageFolderPath)))
    		{
            	CommonVariablesAndMethods.action= new Action(0);
            	CommonVariablesAndMethods.action.setVisible(true);
    		}
    		do{ }  while(! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.createFolder(CommonVariablesAndMethods.TempImageFolderPath)));
    		
    		new Thread(new ClipPrtSc()).start();
    		new PrtScScreenshot();
    	}
    	else
    		System.err.println("ERROR:\n"
    				+ " Please Set Document Destination Folder Before Launch\n"
    				+ " INFO: 'setDocumentDestinationFolder(String documentDestinationFolderPath): void - Screenshot'  set document destination folder\n");
		
	} 
    
    public void setDocumentDestinationFolder(String path)
    {
    	CommonVariablesAndMethods.DocumentPath=CommonVariablesAndMethods.createFolder(path);
    }
    public void setFrameVisibilityInScreenshot(boolean frameVisible)
    {
    	CommonVariablesAndMethods.frameVisible=frameVisible;
    }
    public void setTemporaryScreenshotFolder(String path)
    {
    	CommonVariablesAndMethods.TempImageFolderPath=CommonVariablesAndMethods.createFolder(path);
    }
    
    public String getTemporaryScreenshotFolder()
    {
    	return CommonVariablesAndMethods.TempImageFolderPath;
    }
    
    public String getDefaultPictureType()
    {
    	return "PNG";
    }

    public void setDoubleClickAction(boolean action)
    {
    	CommonVariablesAndMethods.doubleClickAction=action;
    }
   
    public void setPictureType(String imageformat)
    {
    	if (imageformat=="png"|| imageformat=="jpeg")
    		CommonVariablesAndMethods.Imageformat=imageformat.toLowerCase();
    }
}
