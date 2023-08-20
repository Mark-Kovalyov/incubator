package mayton.bigdata;

import mayton.lib.SofarTracker;

import java.util.regex.Pattern;

public class GenericIndexer {

    protected SofarTracker sofarTracker;

    protected int processFiles = 0;
    protected int ignoredFiles = 0;

    public boolean isBookExtenstion(String filename) {
        Pattern book = Pattern.compile(".+\\.(pdf|djvu|ps|dvi|fb2|chm)$", Pattern.CASE_INSENSITIVE);
        return book.matcher(filename).matches();
    }

}
