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
                   .replace("\r", "");
    }

    @Override
    public String toString() {
        return "DictItem{" +
                "word='" + word + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictItem item = (DictItem) o;

        if (word != null ? !word.equals(item.word) : item.word != null) return false;
        return !(explanation != null ? !explanation.equals(item.explanation) : item.explanation != null);

    }

    @Override
    public int hashCode() {
        return 0;
    }
}
