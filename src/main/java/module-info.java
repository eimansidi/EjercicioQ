module com.eiman.ejq {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eiman.ejq to javafx.fxml;
    exports com.eiman.ejq;
}