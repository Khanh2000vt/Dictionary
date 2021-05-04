package sample;

import java.util.List;

public class DictionaryCommanline {
    DictionaryManagement management = new DictionaryManagement();
    public void dictionaryBasic() {
        management.insertFromCommanline();
        management.showAllWords();
    }
    public void dictionaryRecent() {
        List<Word> wordList = management.insertFromFile();
        management.showAllWords();
    }
}
