package im.darkgeek.dicts;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test on DictItemTest
 * Created by justin on 15-9-20.
 */
public class DictItemTest {

    @Test
    public void testSetWithUnsafeChars() {
        DictItem dictItem = new DictItem();
        String unsafeWord = "word \r is unsafe\n";
        String unsafeDefinition = "this definition \n has unsafe chars\r";

        dictItem.setWord(unsafeWord);
        dictItem.setExplanation(unsafeDefinition);
        Assert.assertEquals("handling unsafe chars test failed", "word  is unsafe",
                dictItem.getWord());
        Assert.assertEquals("handling unsafe chars test failed", "this definition  has unsafe chars",
                dictItem.getExplanation());
    }
}
