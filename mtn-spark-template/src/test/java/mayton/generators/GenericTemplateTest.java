package mayton.generators;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

class GenericTemplateTest {

    @Test
    void camelTest() {
        assertEquals("JavaTemplate", GenericTemplate.hyphenToCamel("java-template"));
        assertEquals("Main", GenericTemplate.hyphenToCamel("main"));
        assertEquals("", GenericTemplate.hyphenToCamel(""));
    }

    @Test
    void test() {
        assertEquals("com.google", GenericTemplate.trimDomain("com.google.www"));
        assertEquals("com", GenericTemplate.trimDomain("com.google"));
        assertEquals("", GenericTemplate.trimDomain("com"));
    }

    @Test
    void testAllButLast() {
        assertEquals(Arrays.asList(), GenericTemplate.allButLast(Arrays.asList()));
        assertEquals(Arrays.asList(), GenericTemplate.allButLast(Arrays.asList("x")));
        assertEquals(Arrays.asList("x"), GenericTemplate.allButLast(Arrays.asList("x","y")));
        assertEquals(Arrays.asList("x","y"), GenericTemplate.allButLast(Arrays.asList("x","y","z")));
    }

}
