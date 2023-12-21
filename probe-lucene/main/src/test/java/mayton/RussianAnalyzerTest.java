package mayton;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.apache.lucene.analysis.ru.RussianLightStemFilterFactory;
import org.apache.lucene.analysis.ru.RussianLightStemmer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

import static com.itextpdf.text.html.HtmlTags.SRC;

@Tag("fast")
class RussianAnalyzerTest {



    @Test
    void test() {



        CharTokenizer whitespaceTokenizer = new WhitespaceTokenizer();


        Analyzer russianAnalyzer = (Analyzer) new RussianAnalyzer();
        TokenStream tokenstr = russianAnalyzer.tokenStream("f1", new StringReader(SRC));

        RussianLightStemFilter russianLightStemFilter = new RussianLightStemFilter(tokenstr);




        /*RussianLightStemmer russianLightStemmer = new RussianLightStemmer();
        RussianLightStemFilterFactory russianLightStemFilterFactory = new RussianLightStemFilterFactory(Collections.EMPTY_MAP);*/







    }

}

