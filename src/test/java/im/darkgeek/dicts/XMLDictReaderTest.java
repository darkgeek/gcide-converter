package im.darkgeek.dicts;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by justin on 15-9-9.
 */
public class XMLDictReaderTest
    extends TestCase {

    @Test
    public void testReadEntities() {
        XMLDictReader reader = XMLDictReader.getInstance();
        System.setProperty("entityExpansionLimit", "640000");
        reader.loadRawDict("gcide.xml");
    }
}
