package mayton.sound;

import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WaveEncoder implements AutoCloseable {

    public static final int HEADER_SIZE = 44;

    private OutputStream os;
    int samplingFrequency;
    int bitsPerSample;
    int chunks;
    int samplesPerChunk;
    int channels;

    public WaveEncoder(OutputStream os, int samplingFrequency, int bitsPerSample, int chunks, int samplesPerChunk, int channels) throws IOException {
        Validate.inclusiveBetween(4000, 44100, samplingFrequency);
        Validate.isTrue(bitsPerSample == 16 || bitsPerSample == 8);
        Validate.inclusiveBetween(1, 2, channels);
        Validate.isTrue(chunks > 0);
        Validate.isTrue(samplesPerChunk > 0);
        this.os = os;
        writeHeader();
    }

    private void writeHeader() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(0x46_46_49_52); // RIFF
        int dataSize = bitsPerSample * chunks * samplesPerChunk * channels - 8;

        buffer.putInt(dataSize); // Size of the overall file - 8 bytes, in bytes (32-bit integer). Typically, you’d fill this in after creation.
        buffer.putInt(0x66_45_56_41); // WAVE
        buffer.put((byte)'f');
        buffer.put((byte)'m');
        buffer.put((byte)'t');
        buffer.putShort((short) 16); // Length of format data as listed above ?
        buffer.putShort((short) 1);  // PCM
        buffer.putShort((short) channels);
        buffer.putInt(samplingFrequency); // Sample Rate - 32 byte integer. Common values are 44100 (CD), 48000 (DAT). Sample Rate = Number of Samples per second, or Hertz.
        buffer.putInt((samplingFrequency * bitsPerSample * channels) / 8); // (Sample Rate * BitsPerSample * Channels) / 8.
        // (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo
        // Bits per sample
        // “data” chunk header. Marks the beginning of the data section.
        // Size of the data section.
        // ....
        byte[] bytes = buffer.array();
        os.write(bytes);
    }

    public void writeChunk(short[] samples) throws IOException {
        os.write(new byte[0]);
    }

    @Override
    public void close() throws Exception {
        os.close();
    }
}
