package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveController implements Initializable {

    DictionaryManagement management = new DictionaryManagement();


    @FXML
    protected TextField tuKhoa;

    @FXML
    protected Button hoanTat;

    @FXML
    protected Button ketThuc;

    public void hoanTat_onClick() throws FileNotFoundException {
        management.remove(tuKhoa.getText(), Controller.listWord);
        Controller.listWord = management.updateListWord(Controller.listWord);
        tuKhoa.setText("");
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
