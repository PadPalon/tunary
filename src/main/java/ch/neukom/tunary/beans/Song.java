package ch.neukom.tunary.beans;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Song extends Tuneable {
	@Relationship(type = "IS_ON")
	private Album album;
	@Relationship(type = "CREATED_BY")
	private Band band;
}
