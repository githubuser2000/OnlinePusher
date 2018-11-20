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
import java.sql.Date;

public class server implements Runnable {

	public static long time;
	public static String clientAdress = "";
	public static String systemAndStatus = null;
	public static ServerSocket serverSocket=null;
	public static String getComputerAndOperatingSystem(String text) {
		String[] text2 = text.split(" ");
		return text2[0];
	}

	public static void server() {

		// Warte auf Anfragen auf Port 13000:
		while (true) {			
			clientAdress = null;
			systemAndStatus = null;
			serverSocket=null;
			System.gc();
			try {				
				time = System.currentTimeMillis() / 1000 / 60;
				System.out.println("Server wartet gleich an " + start.port);
				serverSocket = new ServerSocket(start.port);
				System.out.println("server wartet");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client akzeptiert");
				clientAdress = null;
				clientAdress = clientSocket.getInetAddress().toString();
				// Eine einzige Anfrage entgegennehmen:
				systemAndStatus = leseNachricht(clientSocket);
				start.ComputerAndoperatingSystem = getComputerAndOperatingSystem(systemAndStatus);
				System.out.println("System = "
						+ start.ComputerAndoperatingSystem);

				System.out.println("1. Nachricht empfangen");

				schreibeNachricht(clientSocket, "ack");

				String Client30sProcesses = leseNachricht(clientSocket);
				System.out.println("2. Nachricht empfangen");
				// WriteTextFile.anhaengen(Client30sProcesses);
				WriteTextFile.write(systemAndStatus + " " + clientAdress
						+ "\n" + Client30sProcesses);
				System.out.println("und beide geschrieben");

				// Die R�ckgabe in einen Ausgabestream schreiben:
				// PrintWriter out = new
				// PrintWriter(clientSocket.getOutputStream(), true);
				// Senden eines Newline sorgt daf�r, dass der PrintWriter die
				// Ausgabe "abschickt". Alternativ m�sste "flush" aufgerufen
				// werden.
				// out.println("Serverantwort");
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			} finally {
				if (serverSocket != null)
					if (!serverSocket.isClosed()) {
						System.out.println("ServerSocket close");
						try {
							serverSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

			}			
		}

	}

	static String leseNachricht(java.net.Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[400];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 400); // blockiert
																	// bis
																	// Nachricht
																	// empfangen
		System.out.println("leseNachricht anzahlZeichen: " + anzahlZeichen
				+ ", buffer.length: " + buffer.length);
		String nachricht = "";
		if (anzahlZeichen > 0) {
			nachricht = new String(buffer, 0, anzahlZeichen);
		}
		System.out.println("leseNachricht Nachricht: " + nachricht);
		return nachricht;
	}

	static void schreibeNachricht(java.net.Socket socket, String nachricht)
			throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("start server");
		server();

	}

}
