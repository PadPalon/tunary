package ch.neukom.tunary.services.database;

import java.util.List;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class ListConverter implements AttributeConverter<List<String>, String> {
	private final static Joiner COMMA_JOINER = Joiner.on(",").useForNull("-");
	private final static Splitter COMMA_SPLITTER = Splitter.on(",").trimResults();
	@Override
	public String toGraphProperty(List<String> value) {
		return COMMA_JOINER.join(value);
	}

	@Override
	public List<String> toEntityAttribute(String value) {
		return COMMA_SPLITTER.splitToList(value);
	}
}
