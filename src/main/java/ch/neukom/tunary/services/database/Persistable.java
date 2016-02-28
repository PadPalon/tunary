package ch.neukom.tunary.services.database;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public abstract class Persistable {
	@GraphId
	private Long id;
	
	public long getId() {
		return id;
	};
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || id == null || getClass() != o.getClass()) return false;

        Persistable peristable = (Persistable) o;

        if (!id.equals(peristable.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (id == null) ? -1 : id.hashCode();
    }
}
