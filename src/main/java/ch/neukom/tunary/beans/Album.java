package ch.neukom.tunary.beans;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Album extends Tuneable {
	@Relationship(type = "CREATED_BY")
	private Band band;
	@Relationship(type = "IS_ON", direction = Relationship.INCOMING)
	private transient List<Song> songs;
}
