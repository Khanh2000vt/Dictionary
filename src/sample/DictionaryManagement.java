package sample;

import com.sun.speech.freetts.VoiceManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.*;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();
    Scanner scanner = new Scanner(System.in);

    public static final String FILE = "D:\\Java\\DicProject\\resources\\dictionaries.txt";
    File file = new File(FILE);

    public void insertFromCommanline() {
        int n = scanner.nextInt();
        for (int i = 0; i < n; i ++) {
            String wordTarget = scanner.nextLine();
            String wordExplain = scanner.nextLine();
            Word word = new Word(wordTarget, wordExplain);
            dictionary.listWord.add(word);
        }
    }

    public void showAllWords() {
        String show = "";
        if (dictionary.listWord.size() == 0) {
            System.out.println("Từ điển rỗng");
            return;
        }
        for (int i = 0; i < dictionary.listWord.size(); i ++) {
            show += dictionary.listWord.get(i).getWord_target() + dictionary.listWord.get(i).getWord_explain()
                    + "\n";
        }
        System.out.println(show);
    }

    public List<Word> insertFromFile() {
        List<Word> listWord = new ArrayList<>();
        if (!file.exists())
            file.mkdir();
        try {
            Scanner scannerFile = new Scanner(file);
            String wordFile = "";
            while (scannerFile.hasNextLine()) {
                wordFile = scannerFile.nextLine();
                String[] arrayTemp = wordFile.split("\t");
                Word word = new Word(arrayTemp[0], arrayTemp[1]);
                listWord.add(word);
                dictionary.setListWord(listWord);
            }
            scannerFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listWord;
    }

    public void writeFile(List<Word> listWord) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            //This will add a new line to the file content
            for (int i = 0; i < listWord.size(); i ++) {
                pw.println(listWord.get(i).getWord_target() + '\t' + listWord.get(i).getWord_explain());
            }
            pw.close();
        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }

    public List<Word> updateListWord(List<Word> listWord) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(FILE);
        writer.println("");
        writeFile(listWord);
        listWord = insertFromFile();
        //writer.close();
        return listWord;
    }

    public static String convert(String text) {
        text = text.replace("-*", "*");
        text = text.replace("-", "\n-");
        text = text.replace("*", "\n*");
        return text;
    }

    public boolean lookUp(String text, List<Word> list) {
        return indexOf(text, list) != -1;
    }

    public int indexOf(String text, List<Word> list) {
        int l= 0;
        int r = list.size() - 1;

        while (l <= r) {
            int index = l + (r -l)/2;
            if (text.compareTo(list.get(index).getWord_target()) == 0) {
                return index;
            }
            if (text.compareTo(list.get(index).getWord_target()) < 0) {
                r = index - 1;
            }
            if (text.compareTo(list.get(index).getWord_target()) > 0) {
                l = index + 1;
            }
        }
        return -1;
    }

    public List<Word> searchList(String text, List<Word> list) {
        Dictionary search = new Dictionary();
        dictionary.listWord = list;
        boolean checkStartWith;
        boolean checkContain;
        for (int i = 0; i < dictionary.listWord.size(); i++) {
            checkStartWith =
                    dictionary.listWord.get(i).getWord_target().startsWith(text);
            if (checkStartWith) {
                search.listWord.add(dictionary.listWord.get(i));
                if (search.listWord.size() == 25) {
                    return search.listWord;
                }
            }
        }
        for (int i = 0; i < dictionary.listWord.size(); i++) {
            checkContain = dictionary.listWord.get(i).getWord_target().contains(text);
            if (checkContain && !lookUp(dictionary.listWord.get(i).getWord_target(), search.listWord)) {
                search.listWord.add(dictionary.listWord.get(i));
            }
            if (search.listWord.size() == 25) {
                return search.listWord;
            }
        }
        if (text.equals("")) {
            for (int i = 0; i  < 25; i++) {
                search.listWord.add(list.get(i));
            }
        }
        return search.listWord;
    }

    public boolean choose(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cảnh báo");
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        ButtonType buttonTypeCo = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeKhong = new ButtonType("Không", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeCo, buttonTypeKhong);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeCo) {
            return true;
        }
        return false;
    }

    public void warning(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(null);
        alert.showAndWait();
    }

    public void remove(String text, List<Word> list) {
        if (text.equals("")) {
            warning("Vui lòng nhập từ khóa");
            return;
        }
        if (!lookUp(text, list)) {
            warning("TỪ khóa không tồn tại!");
            return;
        }
        int index = indexOf(text, list);
        boolean select;
        select = choose("Xóa từ vựng", "Bạn muốn xóa từ này?");

        if (select) {
            list.remove(list.get(index));
            Collections.sort(list);
            warning("Đã xóa thành công từ khóa '" + text + "'");
        }
        return;
    }

    public void config(String wordTarget, String text, List<Word> list) {
        if (wordTarget.equals("") || text.equals("")) {
            warning("Vui lòng nhập đầy đủ thông tin từ khóa");
            return;
        }
        if (!lookUp(wordTarget, list)) {
            warning("Từ khóa không có trong từ điển");
            return;
        }
        boolean select = choose("Sửa từ khóa", "Bạn có muốn sửa từ này?");
        if (select) {
            int index = indexOf(wordTarget, list);
            if (!list.get(index).getWord_explain().equals(text)) {
                list.get(index).setWord_explain(text);
                warning("Đã sửa thành công từ khóa '" + wordTarget + "'");
            } else {
                warning("Bạn chưa thay đổi thông tin từ khóa!");
            }
        }
    }

    public void add(String wordTarget, String wordExplain, List<Word> list) {
        if (lookUp(wordTarget, list)) {
            warning("Từ khóa đã có trong từ điển");
            return;
        }
        if (wordExplain.equals("") || wordTarget.equals("")) {
            warning("Vui lòng nhập đầy đủ thông tin từ khóa");
            return;
        }
        boolean select;
        select = choose("Thêm từ mới!", "Bạn có muốn thêm từ này?");
        if (select) {
            Word wordInsert = new Word(wordTarget, wordExplain);
            list.add(wordInsert);
            Collections.sort(list);
            warning("Đã thêm thành công từ khóa '" + wordTarget + "'");
        }
    }

    public void speak(String text) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        com.sun.speech.freetts.Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();;
        voice.speak(text);
        voice.deallocate();
    }
}
