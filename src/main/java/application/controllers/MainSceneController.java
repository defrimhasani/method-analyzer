package application.controllers;


import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import application.ReportGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import logic.analyzer.Analyzer;
import logic.analyzer.AnalyzerTask;
import logic.models.FinalCollectedData;
import logic.parser.GenericException;

public class MainSceneController
{
	@FXML
	private Button analyzeButton;

	@FXML
	private TextArea codeArea;

	@FXML
	private TextArea logsArea;


	@FXML
	void analyzeMethod(ActionEvent event)
	{
		logsArea.clear();

		Analyzer analyzer = new Analyzer();

		FinalCollectedData finalCollectedData;

		try
		{
			finalCollectedData = analyzer.getFinalCollectedData(codeArea.getText().toLowerCase(Locale.ROOT), false);

			codeArea.clear();
			codeArea.setText(finalCollectedData.getMethodRaw());


			logsArea.setText(printLogMessages(finalCollectedData));

			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Where do you want to save the report?");
			directoryChooser.setInitialDirectory(
					new File(System.getProperty("user.home"))
			);

			File directory = directoryChooser.showDialog(null);

			if (directory != null)
			{
				File process = new ReportGenerator(finalCollectedData, directory.getAbsolutePath()).process();

				if (process != null)
				{
					Desktop.getDesktop().open(process);
				}
			}
		}
		catch (GenericException e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Oops, code analyze failed!");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

	private String printLogMessages(FinalCollectedData finalCollectedData)
	{
		StringBuilder stringBuilder = new StringBuilder();

		Map<AnalyzerTask.Task, ArrayList<String>> logMessages = finalCollectedData.getLogMessages();


		logMessages.forEach((key, value) -> {

			stringBuilder.append(key.getContent())
					.append(" -----------")
					.append("\n");

			for (String message : value)
			{
				stringBuilder.append("- ")
						.append(message)
						.append("\n");
			}

			stringBuilder.append("\n");
		});

		return stringBuilder.toString();
	}

}
