package ch.neukom.tunary;

import java.net.Socket;

import ch.neukom.tunary.viewer.TunaryViewer;

public class Tunary {
	public static void main(String[] args) throws InterruptedException {
		TunaryThread thread = new TunaryThread();
		thread.start();
		do {
			Thread.sleep(200);
		} while(serverNotStarted());
		
		TunaryViewer.start(thread);
	}

	private static boolean serverNotStarted() {
		try(Socket socket = new Socket("localhost", 4567)) {
			if(socket.isConnected()) {
				System.out.println("Tunary ready!");
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		return true;
	}
}
