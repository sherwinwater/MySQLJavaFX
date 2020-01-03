package ABCLibrarySystem;

import ABCLibrarySystem.DB.DataBaseConnection;
import ABCLibrarySystem.admin.AdminLogin;
import ABCLibrarySystem.librarian.LibrarianLogin;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LibrarySystem extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Button btAdmin = new Button("Admin Login");
	private Button btLibrarian = new Button("Librarin Login");
	private Label lblStatus = new Label("Today: " + LocalDateTime.now());
	public Font font = Font.font("Tahoma", FontWeight.NORMAL, 18);

	public static Stage mainStage = new Stage();

	public void start(Stage primaryStage) throws UnknownHostException {
		mainStage = primaryStage;
		DataBaseConnection.initializeDB();

		btAdmin.setFont(font);
		btLibrarian.setFont(font);

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(btAdmin, btLibrarian);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(40, 40, 40, 40));
		vbox.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		Text txLirarySystem = new Text(100,0,"Library System V1.0");
//		txLirarySystem.setTextAlignment(TextAlignment.CENTER);

		pane.setCenter(vbox);
		pane.setTop(txLirarySystem);
		pane.setBottom(lblStatus);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(pane, primScreenBounds.getWidth() / 2, primScreenBounds.getHeight() / 2);
		primaryStage.setTitle("welcome guest from " + InetAddress.getLocalHost());
		primaryStage.setScene(scene);
		primaryStage.show();

		btAdmin.setOnAction(e -> adminLogin());

		btLibrarian.setOnAction(e -> librarianLogin());

	}

	private void adminLogin() {
		AdminLogin adm = new AdminLogin();
		try {
			adm.start(AdminLogin.adminLoginStage);
			mainStage.close();
		} catch (Exception ex) {
			Logger.getLogger(LibrarySystem.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void librarianLogin() {
		LibrarianLogin lib = new LibrarianLogin();
		try {
			lib.start(LibrarianLogin.loginStage);
			mainStage.close();
		} catch (Exception ex) {
			Logger.getLogger(LibrarySystem.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
