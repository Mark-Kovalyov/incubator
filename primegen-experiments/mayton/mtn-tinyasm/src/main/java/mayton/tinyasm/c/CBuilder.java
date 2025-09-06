package mayton.tinyasm.c;

import java.io.PrintWriter;



public class CBuilder implements AutoCloseable {

    private PrintWriter pw;

    public CBuilder(PrintWriter pw) {
        this.pw = pw;
    }

    public String format(CBuilderArg[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for(CBuilderArg x : args) {
            stringBuilder.append(x.jniType).append(" ").append(x.name);
        }
        return stringBuilder.toString();
    }

    public CBuilder writeJniCall(String method, String body, CBuilderArg ...args ) {
        pw.printf("JNIEXPORT void JNICALL %s(JNIEnv* env, jobject thisObj, %s) {\n", method, format(args));
        pw.printf("   __asm {\n");
        pw.printf("      %s", body);
        pw.printf("   }\n");
        pw.printf("}\n");
        return this;
    }

    @Override
    public void close() throws Exception {
        pw.flush();
        pw.close();
    }
}
