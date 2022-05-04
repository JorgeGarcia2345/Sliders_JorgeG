
/*

    Last week:
    Swing:
            JFrame (Window)
            JPanel (Canvas)

    THIS Week:

    JavaFX: - newest front-end GUI for java
            - Cross-Platform (desktop + mobile)

            Stage (Window) - JFrame
            Scene (Canvas) - JPanel

            Controls:
                        - Node (Parent Class)
                        - Nodes in JavaFx:
                                            - TextField (text box, used for input and output)
                                            - Label (Used for text only)
                                            - Slider (range of numerical values (double))
                                            - Buttons - Click Events (Lambda Expression),
                                                    call a method clear(), call method calculate()

            Panes:  - Pre-built Layouts

                    - Grid-panes - Layout where row + Column is



                                                                                        Col 0  (FIRST) Col 1  Col 2
                                                                        (SECOND) Row 0  Bill amount:    [billAmountTF]
                                                                                 Row 1
                                                                                 Row 2

                                                                                            gridpane.add(billAmountTF, 1, 0);




         Group = package - Directory Structure
                - Com ---> example (FOLDERS PATH)

                www.miracosta.edu
                "Reverse URL for package names"
                edu.miracosta.cs112.jgarcia
                // what we named our package


 */

package edu.miracosta.cs112.jgarcia.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

public class HelloApplication extends Application {

    // Define fields (instance variables) for each of
    // the nodes that change or interact w/ by the user

    private TextField billAmountTF = new TextField();
    private Label tipPercentLabel = new Label("15%");
    private Slider tipPercentSlider = new Slider(0.00, 30.00, 15.00);  // Min, Max, Default
    private TextField tipAmountTF = new TextField();
    private TextField totalAmountTF = new TextField();

    private Button clearButton = new Button("Clear");
    private Button calculateButton = new Button("Calculate");

    @Override
    public void start(Stage stage) throws IOException {


        // Event - Driven programming - wire up certain Node to method calls, using Lambda expressions





        GridPane gridpane = new GridPane(); // Blank White (invisible) canvas

        // Let's set the horizontal AND Vertical Gap of Gridpane (affects all nodes inside)
        gridpane.setHgap(5); // Horizontal 5 pixels
        gridpane.setVgap(5); // Vertical 5 pixels

        // Let's set the alignment to center (default top left)
        gridpane.setAlignment(Pos.CENTER);

        // Let's add "Bill amount: " label to gridPane
        // ADDED LABELS AND TEXT FIELDS
        gridpane.add(billAmountTF, 1, 0);                        //Top right Text Field   [     ]
        gridpane.add(new Label("Bill Amount: "), 0, 0);       // Top Left "Bill Amount" col 0, row 0

        gridpane.add(tipPercentLabel, 0, 1);                    // col 0, row 1 "15%"

        gridpane.add(new Label("Tip Amount: "), 0, 2);       // col 0, row 2
        gridpane.add(tipAmountTF, 1, 2);                       // col 1, row 2 Text Field [     ]

        gridpane.add(new Label("Total Amount: "),0 , 3);    // col 0, row 3
        gridpane.add(totalAmountTF, 1, 3);                    // col 1, row 3 Text Field  [     ]






        // ADDED SLIDER

        gridpane.add(tipPercentSlider, 1, 1); // Slider
        // Configure the slider, ticks, etc
        tipPercentSlider.setShowTickMarks(true); // boolean
        tipPercentSlider.setShowTickLabels(true); //
        tipPercentSlider.setBlockIncrement(5);
        tipPercentSlider.setSnapToTicks(true);
        tipPercentSlider.setMajorTickUnit(5);






        // wire-up the clearButton with the clear() method
        // using a lambda expression

        // Parameter of the setOnAction will be
        // a new, anonymous inner class
        // that implements the handle() method
        // handle() method will call our clear method

        // lambda expression for code
        // Syntax:
        // actionEvent -> clear()
        // parameter -> method call // actionEvent -> clear()
        // e short for event

        clearButton.setOnAction(e -> clear());
        calculateButton.setOnAction(e -> calculate());
        // listening for text
        //TODO: Under the hood for Lambdas methods above
        /*
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {           // actionEvent = mouseClick or Enter when button in focus
                clear();                                            // Handles the click and the ENTER key
            }                                                       // public interface EventHandler , public void handle();
        });
         */

        // wire up the slider and Bill Amount text field TF
        // for a slider, we are listening to its value
        // Listener is an interface, watches for changes in a node
        // Listener -- Changes -- Validations
        tipPercentSlider.valueProperty().addListener((obs, oldVal, newVal) -> calculate());
        //TODO: Under the hood for Lambdas method above
        /*
        tipPercentSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override                                                  obsVal    , oldValue,    newVal
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                calculate();
            }
        });

         */


        // HBox clearCalcBox = ; // Horizontal box of Node (BUTTONS ETC)
        // gridpane.add(new HBox(clearButton, calculateButton), 1, 4); //Horizontal box of Node (BUTTONS ETC) // didnt work becauase we need to instantiate outside of setting inside correct col and row

        // space out buttons
        HBox clearCalcBox = new HBox(clearButton, calculateButton);
        clearCalcBox.setSpacing(10);

        clearCalcBox.setAlignment(Pos.CENTER_RIGHT);
        gridpane.add(clearCalcBox,1 , 4); // added to gridpane:  COL 1, ROW 4


        //disable text fields
        tipAmountTF.setEditable(false);
        tipAmountTF.setFocusTraversable(false); // cant traverse
        tipAmountTF.setDisable(true);

        totalAmountTF.setEditable(false);
        totalAmountTF.setFocusTraversable(false);
        totalAmountTF.setDisable(true);

        billAmountTF.textProperty().addListener((obsVal, oldVal, newVal) -> calculate()); // automatically calls calculate method when listener checks for text changes and updates! WOW!

        Scene scene = new Scene(gridpane, 320, 240);
        stage.setTitle("Tip Calculator!!");
        stage.setScene(scene);
        stage.show();
    }


    // Clear method: Wired up to the clear button

    public void clear(){
        //billAmountTF.setText(""); // manual clear but already clear method!
        //clear
        billAmountTF.clear();
        tipAmountTF.clear();
        totalAmountTF.clear();
        tipPercentSlider.setValue(15); // Reset Slider back to 15%
        billAmountTF.requestFocus(); // Return the focus back to billAmountTF
    }

    // Calculate method will be "wired up" to the
    // calculateButton
    // billAmountTF
    // tipPercentSlider

    public void calculate(){


        // Lets update the label w/ the sliders value
        tipPercentLabel.setText((int) tipPercentSlider.getValue() + "%");

        // get bill amount
        // TODO: This caused an error due to trying to parse a null obj, commented out next 4 line
        /*
        if(!billAmountTF.getText().isEmpty())
        billAmount = Double.parseDouble(billAmountTF.getText());
         */


        if(billAmountTF.getText().isEmpty())    // if text is empty, just exit/return;
            return;
        double billAmount = Double.parseDouble(billAmountTF.getText());  // just reading from available text input for billamount [   ]

        // get tip Percent from slider
        double tipPercent = tipPercentSlider.getValue(); // / 100

        // calculate tip amount
        double tipAmount = billAmount * (tipPercent / 100);

        // calculate the total Amount
        double totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();

        // set the text to both fields
        tipAmountTF.setText(currency.format(tipAmount));        // automatically calculating tip to output
        totalAmountTF.setText(currency.format(totalAmount));    // automatically adding up total to output

        billAmountTF.requestFocus(); // blinker back to bill amount TF [    ]
    }

    public static void main(String[] args) {
        launch();
    }


}

/*

FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
 */