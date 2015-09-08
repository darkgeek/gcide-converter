package im.darkgeek.dicts;

import org.dom4j.Element;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by justin on 15-9-8.
 */
public class DictUtils {

    public static void convertElement(Element element) {
        String elemName = element.getName();
        Set<String> wordsNotToBeProcessed = new HashSet<String>(Arrays.asList("br", "p"));

        if (wordsNotToBeProcessed.contains(elemName))
            return;

        element.setName("div");
        element.addAttribute("class", elemName);
    }
}
