package functionsplotter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FunctionPlotterController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}