package im.darkgeek.dicts;

import org.dom4j.Element;

/**
 * Any class used for callback purpose should implement this interface
 * Created by justin on 15-8-28.
 */
public interface Callback {
    int process(Element element);
}
