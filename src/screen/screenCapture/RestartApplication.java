package screen.screenCapture;

import java.io.FileNotFoundException;

public class RestartApplication implements Runnable{

	@Override
	public void run() {
		try {
			CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Application Restarted");
		new Thread(new ClipPrtSc()).start();
		new PrtScScreenshot();
	}
	

}
