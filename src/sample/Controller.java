package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    DictionaryManagement management = new DictionaryManagement();

    public Controller() {
        management.insertFromFile();
    }

    @FXML
    public TextField insertWord;

    @FXML
    public Button them;

    @FXML
    public Button xoa;

    @FXML
    public Button sua;

    @FXML
    public Button noi;

    @FXML
    public ListView hienTimKiem;

    @FXML
    public TextArea hienNghia;

    List<Word> list = new ArrayList<>();



    public void inserWord_OnKeyReleased() {
        list = management.searchList(insertWord.getText(), listWord);
        hienTimKiem.getItems().clear();
        if (list.size() == 0) {
            hienNghia.setText("");
        } else {
            hienNghia.setText("");
            for (int i = 0; i < list.size(); i ++) {
                hienTimKiem.getItems().add(list.get(i).getWord_target());
                hienNghia.setText(DictionaryManagement.convert(list.get(0).getWord_explain()));
            }
        }
    }

    public void hienNghia_onClick() throws NullPointerException{
        int index = hienTimKiem.getSelectionModel().getSelectedIndex();
        insertWord.setText(list.get(index).getWord_target());
        hienNghia.setText(DictionaryManagement.convert(list.get(index).getWord_explain()));
        hienTimKiem.getItems().clear();
        Word word = list.get(index);
        list.clear();
        list.add(word);
        hienTimKiem.getItems().add(insertWord.getText());
    }

    public void xoa_onClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FxmlFile/Remove.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void sua_onClick() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FxmlFile/Config.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void them_onClick() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FxmlFile/Add.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void noi_onClick() {
        if (insertWord.getText().equals("")) {
            management.warning("Nhập từ khóa!");
            insertWord.requestFocus();
        }
        management.speak(insertWord.getText());
    }

    public  static List<Word> listWord = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listWord = management.insertFromFile();
        Collections.sort(listWord);
    }
}
