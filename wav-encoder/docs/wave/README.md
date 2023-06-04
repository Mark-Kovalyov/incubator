# WAVE, Sun/Next audio

## Links

* https://docs.fileformat.com/audio/wav/
* http://www-mmsp.ece.mcgill.ca/Documents/AudioFormats/WAVE/Samples.html

## WAV

### Header

```

```

### Samples

M1F1-Alaw-AFsp.wav
```
soxi M1F1-Alaw-AFsp.wav 

Input File     : 'M1F1-Alaw-AFsp.wav'
Channels       : 2
Sample Rate    : 8000
Precision      : 13-bit
Duration       : 00:00:02.94 = 23493 samples ~ 220.247 CDDA sectors
File Size      : 47.2k
Bit Rate       : 129k
Sample Encoding: 8-bit A-law
```
dump
```
header:
ckID 	4 	Chunk ID: "RIFF" 
cksize 	4 	Chunk size: 4+n  ( 62 b8 00 00 == 0xb862 == 47202
	WAVEID 	4 	WAVE ID: "WAVE"
	WAVE chunks 	n 	Wave chunks containing format information and sampled data


fmt:
ckID 	4 	Chunk ID: "fmt "
cksize 	4 	Chunk size: 16, 18 or 40
	wFormatTag 	        2 	Format code
	nChannels 	        2 	Number of interleaved channels
	nSamplesPerSec      4   Sampling rate (blocks per second)
	nAvgBytesPerSec 	4 	Data rate
	nBlockAlign 	    2 	Data block size (bytes)
	wBitsPerSample 	    2 	Bits per sample
	cbSize 	            2 	Size of the extension (0 or 22)
	wValidBitsPerSample 2 	Number of valid bits
	dwChannelMask 	    4  	Speaker position mask
	SubFormat 	        16 	GUID, including the data format code

00000000  52 49 46 46 62 b8 00 00  57 41 56 45 66 6d 74 20  |RIFFb...WAVEfmt |
00000010  12 00 00 00 06 00 02 00  40 1f 00 00 80 3e 00 00  |........@....>..|
00000020  02 00 08 00 00 00 66 61  63 74 04 00 00 00 c5 5b  |......fact.....[|
00000030  00 00 64 61 74 61 8a b7  00 00 d5 d5 d5 d5 d5 d5  |..data..........|
00000040  55 d5 d5 55 d5 d5 55 d5  55 55 d5 55 55 55 55 55  |U..U..U.UU.UUUUU|
00000050  d5 55 d5 d5 55 d5 d5 d5  55 55 55 55 d5 55 d5 d5  |.U..U...UUUU.U..|
00000060  55 55 d5 d5 55 d5 55 55  d5 d5 d5 d5 d5 d5 55 d5  |UU..U.UU......U.|
00000070  55 d5 d5 d5 d5 55 d5 55  d5 d5 d5 55 55 55 d5 d5  |U....U.U...UUU..|
...
```
M1F1-int16WE-AFsp.wav
```
00000000  52 49 46 46 0a 70 01 00  57 41 56 45 66 6d 74 20  |RIFF.p..WAVEfmt |
00000010  28 00 00 00 fe ff 02 00  40 1f 00 00 00 7d 00 00  |(.......@....}..|
00000020  04 00 10 00 16 00 10 00  03 00 00 00 01 00 00 00  |................|
00000030  00 00 10 00 80 00 00 aa  00 38 9b 71 64 61 74 61  |.........8.qdata|
00000040  14 6f 01 00 00 00 00 00  00 00 00 00 01 00 02 00  |.o..............|
00000050  fd ff 00 00 00 00 fc ff  06 00 04 00 fe ff 01 00  |................|
...
```

M1F1-uint8-AFsp.wav
```
00000000  52 49 46 46 54 b8 00 00  57 41 56 45 66 6d 74 20  |RIFFT...WAVEfmt |
00000010  10 00 00 00 01 00 02 00  40 1f 00 00 80 3e 00 00  |........@....>..|
00000020  02 00 08 00 64 61 74 61  8a b7 00 00 80 80 80 80  |....data........|
00000030  80 80 80 80 80 80 80 80  80 80 80 80 80 80 80 80  |................|
...
```


### Channels
|Channels|
|--------|
|stereo|
|mono|

### Formats/Encoding
|Format|Type|
|------|----|
|Microsoft PCM||
|IMA ADPCM||
|ITU G.711 mu-law||
|MPEG Layer 3||
|?|22|
|?|70|
|GSM||

### Sample encoding
|Encoding|
|--------|
|8-bit Unsigned Integer PCM|
|16-bit Signed Integer PCM|

### Frequency

|Freq(hz)|
|--------|
|48000|
|44100|
|16000|
|11025|
|8000|

## AU

Reports from soxi, file:
```
Sun/NeXT audio data: 8-bit ISDN mu-law, mono, 8000 Hz
Sun/NeXT audio data: 8-bit A-law (CCITT G.711), stereo, 8000 Hz
```

### Formats/Encoding

|Precision|
|---------|
|54-bit|


|Encoding|
|------|
|64-bit Floating Point PCM|



## Java AudioFormat.Encoding

|Constant|Desc|
|--------|----|
|ALAW|Specifies a-law encoded data.
|PCM_FLOAT|Specifies floating-point PCM data.
|PCM_SIGNED|Specifies signed, linear PCM data.
|PCM_UNSIGNED|Specifies unsigned, linear PCM data.
|ULAW|Specifies u-law encoded data.

