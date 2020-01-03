package ABCLibrarySystem.admin;

import ABCLibrarySystem.librarian.LibrarianMenu;
import ABCLibrarySystem.librarian.SearchBooks;
import com.sun.tools.javac.Main;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminMenu extends Application {
	
	private Button btViewLibrarian = new Button("View Librarian");
	private Button btViewBooks = new Button("View Books");
	private Button btReturnMainMenu = new Button("Return");
	private Text txBooksResults = new Text();
	private Text txLibrarianResults = new Text();
	
	public static Stage adminMenuStage = new Stage();
	private BorderPane pane = new BorderPane();
	
	@Override
	public void start(Stage stageAdmin) throws Exception {
		adminMenuStage = stageAdmin;
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(btViewBooks, btViewLibrarian, btReturnMainMenu);

//		BorderPane pane = new BorderPane();
		pane.setLeft(vBox);
		
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		Scene sceneAdmin = new Scene(pane, primScreenBounds.getWidth() / 2, primScreenBounds.getHeight() / 2);
		stageAdmin.setScene(sceneAdmin);
		stageAdmin.show();
		
		btViewLibrarian.setOnAction(e -> {
			try {
				showLibrarian();
			} catch (Exception ex) {
				Logger.getLogger(AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		btReturnMainMenu.setOnAction(e -> returnMenu());
		btViewBooks.setOnAction(e -> {
			System.out.println("btviewbook");
			SearchBooks sb = new SearchBooks();
			try {
				sb.showBook();
				stageAdmin.close();
			} catch (Exception ex) {
				Logger.getLogger(AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
	}
	
	private void returnMenu() {
		AdminLogin login = new AdminLogin();
		try {
			login.start(login.adminLoginStage);
			adminMenuStage.close();
			
		} catch (Exception ex) {
			Logger.getLogger(AdminMenu.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void showLibrarian() throws Exception {
		
		VBox vBoxRight = new VBox();
		vBoxRight.getChildren().addAll(new Button("hello"));
		System.out.println("showl");
//		pane.setRight(new Button("hello"));
		
		AdminMenu ad = new AdminMenu();
		ad.pane.setRight(new Button("hello"));
		
		ad.start(AdminMenu.adminMenuStage);
		
	}
	
}
