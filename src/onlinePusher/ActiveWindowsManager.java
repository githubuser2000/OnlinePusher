package onlinePusher;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ActiveWindowsManager {
	private static LinkedList<String> activeWindows = new LinkedList<String>();
	//private static int durchlauf = 0;
	private static LinkedList<String> interestingWindows;
	private static Semaphore actWin = new Semaphore(2);
 
	/*
	ActiveWindowsManager() {		
		clear();
		durchlauf = 0;
		interestingWindows = new LinkedList<String>();
		interestingWindows.add("lyx");		
		interestingWindows.add("firefox");
		interestingWindows.add("pidgin");
		interestingWindows.add("swrite");
		interestingWindows.add("java");
		interestingWindows.add("javaw");
		interestingWindows.add("putty");
		interestingWindows.add("skype");
		interestingWindows.add("vlc");
		// actWin=new Semaphore(2);
	}*/


	public static void setNextActiveWindow(String windowProcessName) {
		//if (durchlauf >= 11) {
		//	clear();
		//} else {
		try {
			actWin.acquire();
			activeWindows.add(windowProcessName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//activeWindows[durchlauf] = windowProcessName;
			
/*		
			if (interestingWindows.contains(windowProcessName))
				activeWindows[durchlauf] = windowProcessName;
			else
				activeWindows[durchlauf] = null;
*/
			actWin.release();
			//durchlauf++;
		//}
	}

	public static void clear() {
		//durchlauf = 0;
		try {
			actWin.acquire();
			activeWindows.clear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actWin.release();
	}

	public static LinkedList<String> getActiveWindows() {
		LinkedList<String> onceListedOrMore = new LinkedList<String>();
		LinkedList<String> twiceListedOrMore = new LinkedList<String>();
		//twiceListedOrMore.clear();
		//onceListedOrMore.clear();
		if (activeWindows != null)
			for (String windowname : activeWindows) {
				if (windowname != null)
					if (!windowname.equals("null")) {
						if (onceListedOrMore.contains(windowname)) {
							if (!twiceListedOrMore.contains(windowname)) {
								twiceListedOrMore.add(windowname);
							}
						} else {
							System.out.println("alxbla123: |" + windowname
									+ "|");
							onceListedOrMore.add(windowname);
						}
					}
			}
		System.out.println("activeWindows Size:" + activeWindows.size());
		clear();
		for (String tlom : twiceListedOrMore) {
			System.out.println("alxbla456: <" + tlom
					+ ">");
		}
		for (String windowname : activeWindows) {
			System.out.println("alxbla789:(" + windowname
					+ ")");
			
		}
		return twiceListedOrMore;
	}
}
