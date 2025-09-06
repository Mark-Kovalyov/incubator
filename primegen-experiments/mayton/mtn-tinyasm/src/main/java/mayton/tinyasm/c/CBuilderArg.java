package mayton.tinyasm.c;

public class CBuilderArg {
    public final JNITypes jniType;
    public final String name;

    public CBuilderArg(JNITypes jniType, String name) {
        this.jniType = jniType;
        this.name = name;
    }
}
