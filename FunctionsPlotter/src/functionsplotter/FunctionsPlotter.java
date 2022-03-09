package functionsplotter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;
import javafx.scene.text.TextAlignment;
import javax.swing.ImageIcon;

public class FunctionsPlotter extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        ImageIcon image = new ImageIcon("chessQueens.png");

        Scene scene = new Scene(grid, 700, 675);
        stage.setScene(scene);
        Text scenetitle = new Text("         Graph Plotter  ");
        //scenetitle.setTextAlignment(TextAlignment.);

        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        grid.add(scenetitle, 1, 0, 3, 1);
            
        Label polynomial = new Label("Polynomial");
        ///polynomial.setFont(Font.font("Tahoma", FontWeight.NORMAL, 8));
        grid.add(polynomial, 0, 2);

        TextField function = new TextField();
        grid.add(function, 1, 2,6,1);

        Label min = new Label("Min");
        grid.add(min, 0, 3);

        TextField minBox = new TextField();
        grid.add(minBox, 1, 3,6,1);

        Label max = new Label("Max");
        grid.add(max, 0, 4);

        TextField maxBox = new TextField();
        grid.add(maxBox, 1, 4,6,1);

        Button btn = new Button("Draw Equation Graph");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1 , 5 ,1, 6);
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String poly = function.getText();
                boolean isMinNumber = Pattern.matches("[-+]?[0-9]+", minBox.getText());
                boolean isMaxNumber = Pattern.matches("[-+]?[0-9]+", maxBox.getText());
                int min1, max1;
                // Input Validation
                if (poly.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Empty Input");
                    errorAlert.setContentText("Enter A valid Function !");
                    errorAlert.showAndWait();
                } else if (!isMinNumber || !isMaxNumber) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Empty Input");
                    errorAlert.setContentText("Enter A valid Range !");
                    errorAlert.showAndWait();
                } else {
                    min1 = Integer.parseInt(minBox.getText());
                    max1 = Integer.parseInt(maxBox.getText());
                    if(min1 > max1){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Min is Greater than Max");
                    errorAlert.setContentText("Enter A valid Range !");
                    errorAlert.showAndWait();
                    }
                    xAxis.setLabel("x-Axis");
                    yAxis.setLabel("y-Axis");
                    LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                    //lineChart.setTitle("\n              Function -> " + poly + "\n               Min -> " + min + "\n               Max -> " + max);
                    XYChart.Series series = new XYChart.Series();
                    series.setName("Data Points");
                    Polynomial pl = new Polynomial(poly, min1, max1);

                    System.out.println("Function : " + poly);

                    if (!pl.Right_Flag) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Invalid Input");
                        errorAlert.setContentText("Enter A valid Function to plot !");
                        errorAlert.showAndWait();
                    } else {
                        for (int i = 0; i < pl.result.size(); i++) {
                            series.getData().add(new XYChart.Data(pl.Range.get(i), pl.result.get(i)));
                        }
                        //                    Scene scene = new Scene(lineChart, 600, 500);
                        lineChart.getData().add(series);
                        //grid.setAlignment(Pos.BOTTOM_CENTER);
                        grid.add(lineChart, 0, 8, 5, 1);
                        stage.setScene(scene);
                        stage.show();

                    }
                }
            }
        });
        stage.show();
        xAxis.setVisible(false);
        yAxis.setVisible(false);
    }

    public static void main(String[] args) {
        launch();

    }
}
