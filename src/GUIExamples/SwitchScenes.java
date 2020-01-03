package GUIExamples;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SwitchScenes extends Application {

	private Stage primaryStage;
	private BorderPane mainLayout = new BorderPane();
	private Label lblname = new Label("hello to the world");
	private Button btSub = new Button("change pane");

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		this.primaryStage.setTitle("app");

		showScene();
	}

	private void showScene() throws Exception {
		mainLayout.setAlignment(btSub, Pos.CENTER);
		mainLayout.setCenter(lblname);
		mainLayout.setBottom(btSub);

		Scene scene = new Scene(mainLayout, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.show();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * 0.5;
		double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * 0.5;
//		double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth())/2;
//		double y = 0;
		primaryStage.setX(x);
		primaryStage.setY(y);

		btSub.setOnAction(e -> {
			try {
				changeScene();
			} catch (Exception ex) {
				Logger.getLogger(SwitchScenes.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	private void changeScene() throws IOException, Exception {
		if (!lblname.getText().equals("scene2")) {
			lblname.setText("scene2");
		} else {
			lblname.setText("scene1");
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
