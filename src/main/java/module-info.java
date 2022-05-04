module edu.miracosta.cs112.jgarcia.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.miracosta.cs112.jgarcia.demo to javafx.fxml;
    exports edu.miracosta.cs112.jgarcia.demo;
}