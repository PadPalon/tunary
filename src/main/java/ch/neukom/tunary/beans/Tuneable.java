package ch.neukom.tunary.beans;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import ch.neukom.tunary.services.database.Persistable;

@NodeEntity
public abstract class Tuneable extends Persistable {
	private String name;
	@Relationship(type = "USES")
	private List<Tuning> tunings;
}
