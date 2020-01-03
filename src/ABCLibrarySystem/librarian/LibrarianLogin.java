package ABCLibrarySystem.librarian;

import com.sun.tools.javac.Main;
import ABCLibrarySystem.librarian.LibrarianMenu;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LibrarianLogin extends Application {

	private Label lblLibrarianID = new Label("Librarian ID");
	private Label lblPassword = new Label("Password");
	private Label lblloginStatus = new Label();
	private TextField tfLibrarianID = new TextField();
	private PasswordField tfPassword = new PasswordField();
	private Button btLibrarianLogin = new Button("Librarian Login");
	public static Stage loginStage = new Stage();

	@Override
	public void start(Stage stageAdmin) throws Exception {
		loginStage = stageAdmin;

		GridPane paneLogin = new GridPane();
		paneLogin.setHgap(5);
		paneLogin.setVgap(5);
		paneLogin.add(lblLibrarianID, 0, 1);
		paneLogin.add(tfLibrarianID, 1, 1);
		paneLogin.add(lblPassword, 0, 2);
		paneLogin.add(tfPassword, 1, 2);
		paneLogin.add(btLibrarianLogin, 1, 3);
		paneLogin.add(lblloginStatus, 1, 6);

		paneLogin.setAlignment(Pos.CENTER);
		paneLogin.setPadding(new Insets(40, 40, 40, 40));

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(paneLogin, primScreenBounds.getWidth() / 2, primScreenBounds.getHeight() / 2);
		stageAdmin.setScene(scene);
		stageAdmin.show();

		btLibrarianLogin.setOnAction(e -> goLogin());
		tfPassword.setOnAction(e -> goLogin());

	}

	private void goLogin() {
		LibrarianMenu librarianMenu = new LibrarianMenu();

		String[][] userPassword = new String[10][2];
		userPassword = new String[][]{{"sam", "sam"}, {"jack", "jack"}, {"wang", "wang"}, {"jim", "jim"}};

		for (int i = 0; i < userPassword.length; i++) {
			if (tfLibrarianID.getText().equals(userPassword[i][0])
					&& tfPassword.getText().equals(userPassword[i][1])) {
				try {
					librarianMenu.start(librarianMenu.menuStage);
					loginStage.close();

				} catch (Exception ex) {
					Logger.getLogger(LibrarianLogin.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				lblloginStatus.setText("wrong user or password");
			}
		}
	}

}
