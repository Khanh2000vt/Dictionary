package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController  implements Initializable {

    DictionaryManagement management = new DictionaryManagement();

    @FXML
    public TextField tuKhoa;

    @FXML
    public TextArea nghia;

    @FXML
    public Button hoanTat;

    @FXML
    public Button ketThuc;

    public void hoanTat_onClick() throws FileNotFoundException {
        String string = nghia.getText();
        string = string.replace("\n", "- ");
        string = string.replace("\n*", "* ");
        management.config(tuKhoa.getText(), string, Controller.listWord);
        Controller.listWord = management.updateListWord(Controller.listWord);
    }

    public void ketThuc_onClick(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
