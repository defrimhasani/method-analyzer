package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AnalyzerRunner extends Application
{
	@Override
	public void start(final Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Java Method Analyzer");

		Pane pane = FXMLLoader.load(this.getClass().getResource("/main.fxml"));

		Scene scene = new Scene(pane);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}

