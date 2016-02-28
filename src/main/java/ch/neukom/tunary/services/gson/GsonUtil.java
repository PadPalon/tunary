package ch.neukom.tunary.services.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.gsonfire.GsonFireBuilder;

public class GsonUtil {
	private static GsonUtil INSTANCE;
	private GsonBuilder builder;
	
	private GsonUtil() {
		GsonFireBuilder fireBuilder = new GsonFireBuilder();
		fireBuilder.enableExposeMethodResult();
		builder = fireBuilder.createGsonBuilder();
	}
	
	public static Gson create() {
		if(INSTANCE == null) {
			INSTANCE = new GsonUtil();
		}
		return INSTANCE.createGson();
	}
	
	private Gson createGson() {
		return builder.create();
	}
}
