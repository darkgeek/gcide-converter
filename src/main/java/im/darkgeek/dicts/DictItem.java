package im.darkgeek.dicts;

/**
 * Created by justin on 15-8-28.
 */
public class DictItem {
    private String word;
    private String explanation;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = handleUnsafeChars(word);
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = handleUnsafeChars(explanation);
    }

    private String handleUnsafeChars(String raw) {
        return
                raw.replace("\n", "")
                   .replace("\r", "")
                   .replace("\t", "")
                   .replace("'", "\\'");
    }

    @Override
    public String toString() {
        return "DictItem{" +
                "word='" + word + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
