package mayton;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.RussianStemmer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("fast")
class SnowballTest {

    public static String wrap(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if (c < 0x20 || (c > 127 && c < 256)) {
                sb.append(String.format("\\u%04X", c));
            } else if (isRussian(c)) {
                sb.append((char) c);
            } else {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }

    private static boolean isRussian(int c) {
        return Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of((char) c));
    }

    public static final String SRC = """
              Так говорила в июле 1805 года известная Анна Павловна Шерер, фрейлина и
              приближенная  императрицы  Марии  Феодоровны,  встречая важного и  чиновного
              князя  Василия,  первого  приехавшего  на  ее вечер. Анна  Павловна  кашляла
              несколько  дней, у  нее был грипп, как она говорила (грипп  был тогда  новое
              слово, употреблявшееся только  редкими).  В записочках, разосланных  утром с
              красным лакеем, было написано без различия во всех:
              """;


    @Test
    public void test() {

        String[] split = StringUtils.split(SRC, " \n\r");
        SnowballProgram snowballProgram = new RussianStemmer();
        Arrays.stream(split).forEach(item -> {
            String currentWord = StringUtils.strip(item.toLowerCase(), " ,.():");
            snowballProgram.setCurrent(currentWord);
            snowballProgram.stem();
            System.out.print(wrap(snowballProgram.getCurrent()));
            System.out.print(" ");
        });
    }

}
