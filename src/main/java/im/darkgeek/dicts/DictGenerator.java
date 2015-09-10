package im.darkgeek.dicts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by justin on 15-9-10.
 */
public class DictGenerator {
    private static String GENERATOR_SCRIPT_HEADER = "#!/usr/bin/env python\n" +
            "import slob\n" +
            "with slob.create('gcide.slob') as w:\n";
    private static String GENERATOR_TEMPLATE =
            "    w.add(\"\"\"#DEFINATION#\"\"\".encode('utf-8'), \"\"\"#WORD#\"\"\", content_type='text/html; charset=utf-8')";
    private static final String SCRIPT_NAME = "dict_creator.py";

    public static void createGeneratorScript(List<DictItem> items) {
        if (items == null)
            return;

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(SCRIPT_NAME, "UTF-8");

            printWriter.print(GENERATOR_SCRIPT_HEADER);

            for (DictItem item : items) {
                printWriter.println(
                        GENERATOR_TEMPLATE
                            .replace("#DEFINATION#", item.getExplanation())
                            .replace("#WORD#", item.getWord())
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
}
