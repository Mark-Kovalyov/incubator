package mayton.tinyasm.c;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimaryTestMain {

    public static void main(String[] args) throws IOException {
        try(CBuilder cBuilder = new CBuilder(new PrintWriter(new FileWriter("out/out.c")))) {

            cBuilder.writeJniCall("primeCheck", "", new CBuilderArg(JNITypes.jint, "candidate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
