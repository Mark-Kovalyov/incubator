package mayton.elastic.jaxb;

import static org.junit.Assert.assertEquals;

import mayton.elastic.http.ElasticIndexHelper;
import org.junit.Ignore;
import org.junit.Test;

public class ElasticIndexHelperTest {

    @Test
    @Ignore
    public void test() {
        assertEquals("{\"settings\": null }", ElasticIndexHelper.buildEnglishIndex());
    }

    @Test
    @Ignore
    public void testR() {
        assertEquals("{\"settings\": null }", ElasticIndexHelper.buildRussianIndex());
    }

}
