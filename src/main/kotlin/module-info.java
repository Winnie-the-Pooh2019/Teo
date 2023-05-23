module com.example.teo {
    requires javafx.controls;
    requires javafx.fxml;
                requires kotlin.stdlib;
    
                            
    opens com.example.teo to javafx.fxml;
    exports com.example.teo;
}