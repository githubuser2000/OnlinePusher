package onlinePusher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActiveWindow extends TimerTask {

	//private static String lastWindowName = "";
	private static String lastProcessName = "";
	private static boolean windows = false;
	private static boolean linux = false;
	private static EnumerateWindows ew = null;

	/**
	 * Am Ende mit ActiveWindowsManager.setNextActiveWindow(lastProcessName);
	 * wird der Prozessname des aktiven Fensters in dem Moment unter
	 * Windows oder Linux der statischen Methode siehe oben �bergeben
	 */
	@Override
	public void run() {
		if (linux) {
			
			/*
			String cmd = "ls -l"; // this is the command to execute in the Unix shell
			// create a process for the shell
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			pb.redirectErrorStream(true); // use this to capture messages sent to stderr
			Process shell = pb.start();
			InputStream shellIn = shell.getInputStream(); // this captures the output from the command
			int shellExitStatus = shell.waitFor(); // wait for the shell to finish and get the return code
			// at this point you can process the output issued by the command
			// for instance, this reads the output and writes it to System.out:
			int c;
			while ((c = shellIn.read()) != -1) {System.out.write(c);}
			// close the stream
			try {shellIn.close();} catch (IOException ignoreMe) {}
			*/
			
			
			
			
			//System.out.println("test");
			//Runtime shell = Runtime.getRuntime();
			//Process p = null;
			//String cmd = "/home/alex/workspace/onlinePoller/src/onlinePoller/linuxWinProces.sh";
            //String cmd = "echo bla17";
			//String cmd = "./linuxWinProces.sh";
			System.out.println("Windows 1");
			//String cmd = "ps -e | grep $(xdotool getwindowpid $(xdotool getwindowfocus)) | grep -v grep | awk '{print $4}'";
			//http://superuser.com/questions/382616/detecting-currently-active-window
			String cmd = "xprop -id $(xprop -root _NET_ACTIVE_WINDOW | cut -d \' \' -f 5) WM_NAME | awk -F \'\"\' \'{print $2}\'";
			System.out.println("Windows 2");
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			System.out.println("Windows 3");
			Process shell=null;
			try {
				shell = pb.start();
				System.out.println("Windows 4");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Windows 15");
				e.printStackTrace();
			}
			try {
				System.out.println("Windows 5");
				int shellExitStatus = shell.waitFor();
				System.out.println("Windows 6");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Windows 13");
				e.printStackTrace();
			}
			System.out.println("Windows 7");
			int c;
			InputStream shellIn = shell.getInputStream();
			System.out.println("Windows 8");
			try {
				lastProcessName=null;
				lastProcessName="";
				System.out.println("Windows 9");
				while ((c = shellIn.read()) != -1) {
					byte[] b={(byte)c};
					System.out.println("Windows 10");
					lastProcessName+=new String(b, "UTF-8");
					System.out.println("Windows 11 "+lastProcessName);						
					//System.out.write(c);					
				}
				System.out.println("Windows 12");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Windows 14");
				e.printStackTrace();
			}			
			//System.out.print(lastProcessName);
			// close the stream
			System.out.println("Windows 16");
			try {shellIn.close();System.out.println("Windows 17");} catch (IOException ignoreMe) {System.out.println("Windows 18");}
			lastProcessName = lastProcessName.replaceAll("\\s", "");
			System.out.println("Windows 19 "+lastProcessName);
			System.gc();
			/*
			try {
				p = shell.exec(cmd);

			} catch (IOException ioe) {

			}
			System.out.println("test2");
			try {
				p.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("test3");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			StringBuffer output = new StringBuffer();
			try {
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(output.toString());
			*/
			/*
			 * Getting window title:
			 * 
			 * xwininfo -root -children | grep $(printf '%x\n' $(xdotool
			 * getwindowfocus)) | grep -oEi '"[^"]+"' | head -1
			 * 
			 * Getting process name:
			 * 
			 * ps -e | grep $(xdotool getwindowpid $(xdotool getwindowfocus)) |
			 * grep -v grep | awk '{print $4}'
			 * 
			 * or:
			 * 
			 * cat /proc/$(xdotool getwindowpid $(xdotool getwindowfocus))/comm
			 */
			 System.out.println("Windows 20");
		}
		if (windows) {
			EnumerateWindows.bufferAndWindowname baw = ew.WindowName();
			//lastWindowName = baw.WindowName;
			//System.out.println(lastWindowName);
			lastProcessName = ew.WindowProcess(baw.buffer);
			if (lastProcessName.length()>4) {
				if (lastProcessName.charAt(lastProcessName.length()-4)=='.')
					lastProcessName=lastProcessName.substring(0, lastProcessName.length()-4);
			}
			//System.out.println(lastProcessName);			
		}
		ActiveWindowsManager.setNextActiveWindow(lastProcessName);
	}
	//public static ActiveWindowsManager awm=new ActiveWindowsManager();
	
	/**
	 * Entscheidung und Zuweisung welches Betriebssystem
	 * Start eines Timers dieser Klasse hier mit der Wiederholungszeit
	 * aus der Startklasse
	 * gestartet wird immer die Runmethode dieser Klasse hier
	 */
	public static void startTimer() {
		Timer timer = new Timer();
		String os = "os.name";
		Properties prop = System.getProperties();
		if (prop.getProperty(os).contains("Windows")) {
			windows = true;
			ew = new EnumerateWindows();
		} else if (prop.getProperty(os).contains("Linux")) {
			linux = true;
			ew = null;
		}
		timer.schedule(new TimerActiveWindow(), 0, start.activeWindowTimer);
	}
/*
	public static void main(String[] args) {
		startTimer();
	}
	*/
}
