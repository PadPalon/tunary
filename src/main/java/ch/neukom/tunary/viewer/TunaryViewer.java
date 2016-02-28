package ch.neukom.tunary.viewer;

import ch.neukom.tunary.TunaryThread;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TunaryViewer extends Application {
	private static TunaryThread TUNARY;
	
	private Scene scene;
	
	public static void start(TunaryThread tunary) {
		TunaryViewer.TUNARY = tunary;
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tunary");
		scene = new Scene(new Viewer(), 750, 500, Color.BLACK);
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				TUNARY.halt();
			}
		});
	}
	
	public class Viewer extends Region {
		private final WebView browser = new WebView();
		private final WebEngine engine = browser.getEngine();
		
		public Viewer() {
			engine.load("http://localhost:4567/");
			getChildren().add(browser);
		}
	 
	    @Override
	    protected void layoutChildren() {
	        double width = getWidth();
	        double height = getHeight();
	        layoutInArea(browser, 0, 0, width, height, 0, HPos.CENTER, VPos.CENTER);
	    }
		
		@Override
		protected double computePrefWidth(double height) {
	        return 750;
	    }
	 
	    @Override
	    protected double computePrefHeight(double width) {
	        return 500;
	    }
	}
}
