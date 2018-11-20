package onlinePusher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteTextFile {
	private static PrintWriter pWriter = null;

	public static void write(String text) {

		try {
			System.out.println("Pfad: " + start.pfadfile);
			pWriter = null;
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					start.pfadfile)));
			pWriter.println(text);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
			}
		}
		System.gc();
	}
/*
	public static void anhaengen(String text) {
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					start.pfadfile)));
			pWriter.append("angehangen: "+text+"\n");			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (pWriter != null) {				
				pWriter.close();
			}
		}
	}*/
}