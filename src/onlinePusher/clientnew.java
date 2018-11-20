package onlinePusher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class clientnew implements Runnable {

	public static hooks_KeyMouse HooksObj;
	public static Socket socket=null;

	public static void client() {

		// Verbindung zu Port 13000 auf localhost aufbauen:

		// Eine Zeile lesen:
		/*
		 * BufferedReader in = new BufferedReader(new
		 * InputStreamReader(socket.getInputStream())); String serverResponse =
		 * in.readLine(); System.out.println("Server-Antwort: " +
		 * serverResponse);
		 */
		HooksObj = new hooks_KeyMouse();
		HooksObj.addListeners();

		// ActiveWindowsManager.clear();
		while (true) {
			socket=null;
			System.out.println("vor system.gc()");
			System.gc();
			System.out.println("nach system.gc()");
			try {
				System.out.println("client aufbauen zu " + start.host + ":"
						+ start.port);
				boolean socketok;
				do {
					socketok=false;
					try {
						socket = new Socket(start.host, start.port);
						socketok=true;
					} catch (Exception ex) {						
						System.out.println("Socket Exception "+ex.getMessage());
					} catch (Error er) {
						System.out.println("Socket Error "+er.getMessage());						
					}
				} while (!socketok);
				System.out.println("client aufgebaut zu " + start.host + ":"
						+ start.port);
				
				System.out.println("Hooks: " + HooksObj.getKeysActive() + " "
						+ HooksObj.getMouseActive());
				String send1nachricht = start.ComputerAndoperatingSystem
						+ " an " + HooksObj.getKeysActive() + " "
						+ HooksObj.getMouseActive();
				// while (send1nachricht.length()<=200)
				// send1nachricht+=" ";
				schreibeNachricht(socket, send1nachricht);
				// System.out.println("1. Nachricht geschrieben");				
				HooksObj.setAllInactive();
				// System.out.println("Sem released und keys auf 0");
				
				if (leseNachricht(socket).compareTo("ack") == 0) {
					Thread.sleep(200);
					System.out.println("Bestaetigung erhalten vom Server.");
					String allActiveWindows = " ";
					for (String window : ActiveWindowsManager
							.getActiveWindows()) {
						allActiveWindows += window + " ";
					}
					System.out.println("allActiveWindows: " + allActiveWindows);
					schreibeNachricht(socket, allActiveWindows);
					System.out.println("2. Nachricht gesendet");
				}
				socket.close();
				ActiveWindowsManager.clear();
				System.out
				.println("Infos ueber letzte offenen Fenster geleert");
				// Thread.sleep(1000*60*5);
			} catch (Exception ex) {
				//ex.printStackTrace();
				System.out.println("Exception "+ex.getMessage());
			} catch (Error er) {
				System.out.println("Error "+er.getMessage());
				//er.printStackTrace();
			} finally {
				try {
					System.out.println("vor ClientSleep");
					Thread.sleep(start.clientSleep);
					System.out.println("nach ClientSleep");
					// Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("ClientSleep Interrupted");
					//e.printStackTrace();
				}
			}			
		}
	}

	static void schreibeNachricht(java.net.Socket socket, String nachricht)
			throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	static String leseNachricht(java.net.Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[400];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 400); // blockiert
																	// bis
																	// Nachricht
																	// empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("start client");
		TimerActiveWindow.startTimer();
		client();
	}

}
