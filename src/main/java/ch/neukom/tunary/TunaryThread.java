package ch.neukom.tunary;

import ch.neukom.tunary.routes.TuningRoute;
import ch.neukom.tunary.services.database.DatabaseHandler;

public class TunaryThread extends Thread {
	private DatabaseHandler db;
	
	public void run() {
		this.db = DatabaseHandler.getInstance();
		new TuningRoute(db);
	}

	public void halt() {
		this.interrupt();
		System.exit(0);
	}
}