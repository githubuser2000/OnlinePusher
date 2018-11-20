package onlinePusher;

import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.*;
//Argumente:
//client localhost ubuntu 8887
// server bla.txt 8887

public class start extends JFrame implements ActionListener {

	// JButton button;
	public static int clientSleep = 1000 * 60 * 5 - 100;// 5000; //

	public static int activeWindowTimer = 30 * 1000; // 500 //

	static int port;

	static String host = "localhost";

	static String[] arguments = null;

	static String ComputerAndoperatingSystem;

	MenuItem exitItem, exitItem2;

	SystemTray tray = null;

	TrayIcon trayIcon = null;

	static start start2;

	static String pfadfile;

	static boolean exit2 = false;

	start() {
		super("blablub");
		JPanel panel = new JPanel();
		// button=new JButton("Ende");
		// button.addActionListener(this);
		// panel.add(button);
		// button.setVisible(true);
		BufferedImage img = null;
		/*
		 * String pfad = System.getProperty("java.class.path"); String pfad2 =
		 * ""; if (pfad.length() > 1) { if (pfad.charAt(1) == ':') { for (int i
		 * = 0; i < pfad.length(); i++) { if (pfad.charAt(i) == '\\') pfad2 +=
		 * '/'; else pfad2 += pfad.charAt(i); } } else pfad2 = pfad; } else
		 * pfad2 = pfad;
		 * 
		 * try { pfad2 += "/"; pfad2 =
		 * pfad2.substring(0,pfad2.lastIndexOf("/")); pfad2 =
		 * pfad2.substring(0,pfad2.lastIndexOf("/")); } catch (Exception ex) {
		 * pfad2="~"; } System.out.println("traysource: "+pfad2);
		 */
		// java.net.URL imgURL = getClass().getResource(path);
		try {
			// img = ImageIO.read(new File(pfad2 + "/ajax-indicator.gif"));
			img = ImageIO.read(getClass().getResource("onlinepoller.gif"));
			System.out.println(img.toString());
		} catch (IOException e) {
			System.out.println("img error");
		}
		final PopupMenu popup = new PopupMenu();

		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		} else {
			trayIcon = new TrayIcon(img);
			trayIcon.setImageAutoSize(false);			
			tray = SystemTray.getSystemTray();
			// Menu displayMenu = new Menu("Display");
			exitItem = new MenuItem("Exit");
			exitItem2 = new MenuItem("Exit2b");
			exitItem.addActionListener(this);
			exitItem2.addActionListener(this);
			// Add components to pop-up menu
			// popup.addSeparator();
			// popup.add(displayMenu);
			popup.add(exitItem);
			popup.add(exitItem2);
			trayIcon.setPopupMenu(popup);
			try {
				tray.add(trayIcon);				
			} catch (AWTException e) {
				System.out.println("TrayIcon could not be added.");
			}
			setState(start.ICONIFIED);
		}

	}

	static Semaphore hookObjSem = new Semaphore(2);
	static client clientObj = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("args 0");
		if (args.length == 0) {
			System.out.println("keine Argumente angegeben");
			System.out
					.println("Betriebsart, Datei bei Server / Serveradresse bei Client, Server-Port");
			System.out
					.println("Beispiel fuer Argumente1: client localhost ubuntu 8887");
			System.out.println("Beispiel fuer Argumente2: server bla.txt 8887");
			System.out
					.println("Beispiel fuer Argumente3: client localhost ubuntu 8887 test");
			System.out.println("fuer deutlich kuerzere Polling-Abstaende");
			System.exit(1);
		}
		System.out.println("args 1");
		// System.out.println("args[4]: "+args[4]);
		if (args.length > 4) {
			if (args[4].compareTo("test") == 0) {
				System.out.println("Programmargument test");
				clientSleep = 5000;
				activeWindowTimer = 500;
			}
		}
		System.out.println("args 2");
		arguments = args;
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println("ShutdownHook laeuft");
				if (arguments.length > 2)
					if (arguments[0].compareTo("server") == 0) {
						WriteTextFile
								.write(start.ComputerAndoperatingSystem
										+ " aus " + "false false"
										+ server.clientAdress);

						if (server.serverSocket != null)
							if (!server.serverSocket.isClosed()) {
								System.out.println("ServerSocket close");
								try {
									server.serverSocket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
					}

				if (arguments.length > 3)
					if (arguments[0].compareTo("client") == 0) {
						try {
							hookObjSem.acquire();
						} catch (InterruptedException e) {
						}
						clientObj.HooksObj.removeListeners();
						hookObjSem.release();
						if (!exit2) {
							try {
								client.socket = new Socket(start.host, port);
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (Error er) {

							}
							System.out.println("client aufgebaut");
							try {
								client.schreibeNachricht(client.socket,
										start.ComputerAndoperatingSystem
												+ " aus");
							} catch (IOException e) {
								e.printStackTrace();
							}
							if (!client.socket.isClosed()) {
								try {
									client.socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				/*
				 * if (!client.socket.isClosed()) { try { client.socket.close();
				 * } catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } }
				 */

			}
		});
		System.out.println("args 3");
		if (args.length > 3)
			
			if (args[0].compareTo("client") == 0) {
				System.out.println("ist ein Client");
				// System.out.println("jarstart: "+System.getProperty("java.class.path"));
				port = Integer.parseInt(arguments[3]);
				ComputerAndoperatingSystem = args[2];
				host = args[1];
				start start = new start();
				start.start2 = start;
				start.setLocation(100, 100);
				start.setSize(100, 100);
				start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// start.pack();
				start.setVisible(false);
				System.out.println("client1");
				clientObj = new client();
				System.out.println("client2");
				Thread clientfaden = new Thread(clientObj);
				System.out.println("client3");
				clientfaden.start();
				System.out.println("client4");

			}
		if (args.length > 2) {
			if (args[0].compareTo("server") == 0) {
				port = Integer.parseInt(arguments[2]);
				System.out.println(args[1]);
				pfadfile = "";
				if (args[1].length() > 1) {
					if (args[1].charAt(1) == ':') {
						for (int i = 0; i < args[1].length(); i++) {
							if (args[1].charAt(i) == '\\')
								pfadfile += '/';// File.pathSeparator;
							else
								pfadfile += args[1].charAt(i);
						}
					} else
						pfadfile = args[1];
				}
				System.out.println(pfadfile);
				Thread serverfaden = new Thread(new server());
				serverfaden.start();
				Thread timerfaden = new Thread(new TimerGoOffline());
				timerfaden.start();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == exitItem) {
			System.exit(0);
		}
		if (arg0.getSource() == exitItem2) {
			exit2 = true;
			System.exit(0);
		}
	}

}
