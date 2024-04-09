module com.github.nuuttiniiranen.projektigithubiin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.nuuttiniiranen.projektigithubiin to javafx.fxml;
    exports com.github.nuuttiniiranen.projektigithubiin;
}