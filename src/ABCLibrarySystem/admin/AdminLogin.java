package ABCLibrarySystem.admin;

import com.sun.tools.javac.Main;
import ABCLibrarySystem.LibrarySystem;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminLogin extends Application {

	private Label lblAdminID = new Label("Admin ID");
	private Label lblPassword = new Label("Password");
	private Label lblloginStatus = new Label();
	private TextField tfAdminID = new TextField();
	private PasswordField tfPassword = new PasswordField();
	private Button btAdminLogin = new Button("Admin Login");

	public static Stage adminLoginStage = new Stage();

	@Override
	public void start(Stage stageAdmin) throws Exception {
		adminLoginStage = stageAdmin;

		GridPane paneLogin = new GridPane();
		paneLogin.setHgap(10);
		paneLogin.setVgap(10);
		paneLogin.add(lblAdminID, 0, 1);
		paneLogin.add(tfAdminID, 1, 1);
		paneLogin.add(lblPassword, 0, 2);
		paneLogin.add(tfPassword, 1, 2);
		paneLogin.add(btAdminLogin, 1, 3);
		paneLogin.add(lblloginStatus, 1, 6);
		
		paneLogin.setAlignment(Pos.CENTER);
		paneLogin.setPadding(new Insets(40,40,40,40));

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene sceneAdmin = new Scene(paneLogin, primScreenBounds.getWidth() / 2, primScreenBounds.getHeight() / 2);
		stageAdmin.setScene(sceneAdmin);
		stageAdmin.show();

		btAdminLogin.setOnAction(e -> adminLogin());
		tfPassword.setOnAction(e -> adminLogin());

	}

	private void adminLogin() {
		AdminMenu adminMenu = new AdminMenu();

		String[][] userPassword = new String[10][2];
		userPassword = new String[][]{{"sam", "sam"}, {"jack", "jack"}, {"wang", "wang"}, {"jim", "jim"}};

		for (int i = 0; i < userPassword.length; i++) {
			if (tfAdminID.getText().equals(userPassword[i][0])
					&& tfPassword.getText().equals(userPassword[i][1])) {
				try {
					adminMenu.start(adminMenu.adminMenuStage);
					adminLoginStage.close();

				} catch (Exception ex) {
					Logger.getLogger(AdminLogin.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				lblloginStatus.setText("wrong user or password");
			}
		}
	}

}
