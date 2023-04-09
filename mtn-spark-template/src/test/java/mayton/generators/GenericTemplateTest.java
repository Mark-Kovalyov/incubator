package mayton.generators;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class GenericTemplateTest {

    @Test
    void test() {
        assertEquals("com.google", GenericTemplate.trimDomain("com.google.www"));
        assertEquals("com", GenericTemplate.trimDomain("com.google"));
        assertEquals("", GenericTemplate.trimDomain("com"));
    }

}
