package mayton.lucene.text;

public class Main {

    static String formatterPatternSwitch(Object o) {
        return switch (o) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> o.toString();
        };
    }

    public static void main(String[] args) {
        System.out.println(formatterPatternSwitch(Integer.valueOf(1)));
    }

}
