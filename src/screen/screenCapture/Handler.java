package screen.screenCapture;

import java.lang.Thread.UncaughtExceptionHandler;

public class Handler implements UncaughtExceptionHandler
{
	   public void uncaughtException(Thread t, Throwable e)
	   {
		    System.out.println("UncaughtExceptionHandler Launched "+(++ClipPrtSc.n)+" Times");
		    CommonVariablesAndMethods.ClipboardStatus="Blocked";
	        new Thread(new ClipPrtSc()).start();
	   }
}


