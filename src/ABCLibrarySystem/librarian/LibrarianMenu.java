package ABCLibrarySystem.librarian;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LibrarianMenu extends Application {

	private Button btSearchBooks = new Button("Search Books");
	private Button btAddBooks = new Button("Add Books");
	private Button btReturnBooks = new Button("Return Books");
	private Button btBorrowBooks = new Button("Borrow Books");
	private Button btReturnMainMenu = new Button("Return");
	private Text txBooksResults = new Text();
	private Text txLibrarianResults = new Text();

	public static Stage menuStage = new Stage();
	public BorderPane pane = new BorderPane();
	public Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	public double scenewidth;
	public double sceneHeight;
	public Font font = Font.font("Verdana", FontWeight.NORMAL, 18);

	public LibrarianMenu(){
		
	}
	
	public void setScenewidth(double scenewidth) {
		this.scenewidth = scenewidth;
	}

	public void setSceneHeight(double sceneHeight) {
		this.sceneHeight = sceneHeight;
	}

	
	public BorderPane getPane() {
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(20, 10, 20, 10));
		vBox.getChildren().addAll(btSearchBooks, btAddBooks, btReturnBooks, btBorrowBooks, btReturnMainMenu);
		pane.setLeft(vBox);
		return pane;
	}

	@Override
	public void start(Stage stage) throws Exception {
		menuStage = stage;

//		btSearchBooks.setFont(font);
//		btAddBooks.setFont(font);
//		btBorrowBooks.setFont(font);
//		btReturnBooks.setFont(font);
		scenewidth = primScreenBounds.getWidth() / 2;
		sceneHeight = primScreenBounds.getHeight() / 2;
		
		Scene scene = new Scene(getPane(), scenewidth, sceneHeight);
		stage.setScene(scene);
		stage.show();

		btReturnMainMenu.setOnAction(e -> returnMenu());

		btAddBooks.setOnAction(e -> {
			AddBooks addbook = new AddBooks();
			pane.setRight(null);
			pane.setCenter(addbook.addBooks());
		});

		btSearchBooks.setOnAction(e -> {
			SearchBooks searchbook = new SearchBooks();
			pane.setCenter(searchbook.searchBooks());
		});

	}

	public void returnMenu() {
		LibrarianLogin login = new LibrarianLogin();
		try {
			login.start(login.loginStage);
			menuStage.close();

		} catch (Exception ex) {
			Logger.getLogger(LibrarianMenu.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
