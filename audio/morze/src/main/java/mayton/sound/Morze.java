package mayton.sound;

import mayton.audio.BitsPerSample;
import mayton.audio.Channels;
import mayton.audio.SamplingFrequency;
import mayton.audio.Synthesizer;
import mayton.audio.serde.AudioWriter;
import mayton.audio.serde.WavWriter;

import java.io.RandomAccessFile;

public class Morze {

    public static void main(String[] args) throws Exception {
        String encoded = mayton.libs.encoders.Morze.encode("the quick brown fox jump over lazy dog");
        AudioWriter wavWriter = new WavWriter(new RandomAccessFile("/tmp/morze/morze.wav", "rw"), SamplingFrequency.FREQUENCY_22K, BitsPerSample.BITS_PER_SAMPLE_16, Channels.MONO);
        Synthesizer.generateSine(3000, 600, 0.9);
        wavWriter.close();
    }

}
