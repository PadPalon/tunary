package ch.neukom.tunary.beans;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import ch.neukom.tunary.services.database.ListConverter;
import ch.neukom.tunary.services.database.Persistable;
import io.gsonfire.annotations.ExposeMethodResult;

@NodeEntity
public class Tuning extends Persistable {
	@Convert(ListConverter.class)
	private List<String> strings = Lists.newArrayList();
	@Relationship(type = "IS_FOR")
	private Instrument instrument;
	
	public List<String> getStrings() {
		return strings;
	}
	
	public void addString(String string) {
		this.strings.add(string);
	}

	@ExposeMethodResult("stringCount")
	public int getStringCount() {
		return strings.size();
	}
	
	@Override
	public boolean equals(Object object) {
		if(Predicates.instanceOf(Tuning.class).apply(object)) {
			return Iterables.elementsEqual(strings, ((Tuning) object).getStrings());
		} else {
			return false;
		}
	}
}
