package mayton.sound;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.Math.sin;
import static org.apache.commons.io.IOUtils.readLines;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("quicktests")
class MainTest {

    boolean isCorrect(String filepath) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("/usr/bin/soxi", filepath);
        Process process = processBuilder.start();
        int errorCode = process.waitFor();
        List<String> out = IOUtils.readLines(process.getInputStream(), StandardCharsets.UTF_8);
        out.forEach(item -> System.out.println(item));
        List<String> err =  IOUtils.readLines(process.getErrorStream(), StandardCharsets.UTF_8);
        err.forEach(item -> System.err.println(item));
        return errorCode == 0;
    }

    @Test
    void shouldAnswerWithTrue() throws Exception {

        double AMPLITUDE = 16000.0;
        int SAMPLING_FREQ = 8000;
        double SOUND_FREQ = 400.0; // Hz
        double PHI = 1.0;

        WaveEncoder waveEncoder = new WaveEncoder(new FileOutputStream("docs/wave/test01.wav"), 8000, 16, 1, 1024, 1);
        short[] arr = new short[1024];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (short) (AMPLITUDE * sin(PHI * i));
        }

        waveEncoder.writeChunk(arr);
        waveEncoder.close();

        assertTrue(isCorrect("docs/wave/test01.wav"));
    }
}
