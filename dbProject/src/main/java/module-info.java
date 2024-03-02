module com.example.dbproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
            
                            
    opens com.example.dbproject to javafx.fxml;
    exports com.example.dbproject;
}