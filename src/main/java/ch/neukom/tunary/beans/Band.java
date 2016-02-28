package ch.neukom.tunary.beans;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Band extends Tuneable {
	@Relationship(type = "CREATED_BY", direction = Relationship.INCOMING)
	private transient List<Album> albums;
	@Relationship(type = "CREATED_BY", direction = Relationship.INCOMING)
	private transient List<Song> songs;
}
