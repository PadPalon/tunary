package ch.neukom.tunary.services.database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class DatabaseHandler {
	private final static SessionFactory SESSION_FACTORY = new SessionFactory("ch.neukom.tunary.services.database", "ch.neukom.tunary.beans");
	private final static DatabaseHandler INSTANCE = new DatabaseHandler();
	
	private final Session session;
	
	public static DatabaseHandler getInstance() {
		return INSTANCE;
	}
	
	private DatabaseHandler() {
		Path currentPath = Paths.get("");
		Components.configuration()
			.driverConfiguration()
			.setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver")
			.setURI(String.format("file:/%s/db/tunary", currentPath.toAbsolutePath().normalize().toString().replace('\\', '/')));
		this.session = getNeo4jSession();
	}
	
	public Session getNeo4jSession() {
		return SESSION_FACTORY.openSession();
	}
	
	public <T extends Persistable> Iterable<T> find(Class<T> type) {
		return find(type, 0);
	}
	
	public <T extends Persistable> Iterable<T> find(Class<T> type, int depth) {
		return session.loadAll(type, depth);
	}
	
	public <T extends Persistable> T find(Class<T> type, Long id) {
		return find(type, id, 1);
	}
	
	public <T extends Persistable> T find(Class<T> type, Long id, int depth) {
		return session.load(type, id, depth);
	}
	
	public <T extends Persistable> void delete(T persistable) {
		session.delete(session.load(persistable.getClass(), persistable.getId()));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Persistable> T createOrUpdate(T persistable) {
		session.save(persistable);
		return (T) find(persistable.getClass(), persistable.getId());
	}

	public <T extends Persistable> Iterable<T> findByQuery(Class<T> type, String query, Map<String, ?> parameters) {
		return session.query(type, query, parameters);
	}

	public <T extends Persistable> Iterable<T> findByFilter(Class<T> type, Filters filters) {
		return session.loadAll(type, filters, 0);
	}
	
	public void halt() {
		session.purgeDatabase();
	}
}
