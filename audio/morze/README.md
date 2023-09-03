# Morze 

## Wav information

* https://www.mmsp.ece.mcgill.ca/Documents/AudioFormats/WAVE/WAVE.html
* https://audiocoding.cc/articles/2008-05-22-wav-file-structure/

## Testing 

```
$ soxi tone-mono-16-bit-PCM-signed.wav

Input File     : 'tone-mono-16-bit-PCM-signed.wav'
Channels       : 1
Sample Rate    : 44100
Precision      : 16-bit
Duration       : 00:00:30.00 = 1323000 samples = 2250 CDDA sectors
File Size      : 2.65M
Bit Rate       : 706k
Sample Encoding: 16-bit Signed Integer PCM
```

## Header

```
52 49 46 46 : RIFF
?? ?? ?? ?? : data size (will be init later) (14 60 28 00 = 0x00_28_60_14 = 2 646 036
              (the actual file size is 2 646 044)
57 41 56 45 : 'WAVE'
66 6d 74 20 : 'fmt '
10 00       : Length of format data as listed above ?
10 00       : 1 = PCM 
```



```
00000000  52 49 46 46 14 60 28 00  57 41 56 45 66 6d 74 20  |RIFF.`(.WAVEfmt |
00000010  10 00 00 00 01 00 01 00  44 ac 00 00 88 58 01 00  |........D....X..|
00000020  02 00 10 00 64 61 74 61  f0 5f 28 00 00 00 a6 0b  |....data._(.....|
00000030  23 17 58 22 14 2d 42 37  b1 40 50 49 f4 50 92 57  |#.X".-B7.@PI.P.W|
00000040  08 5d 4e 61 4a 64 01 66  5c 66 6b 65 24 63 96 5f  |.]NaJd.f\fke$c._|
00000050  ca 5a d0 54 bc 4d a6 45  a9 3c e4 32 70 28 7e 1d  |.Z.T.M.E.<.2p(~.|
00000060  23 12 90 06 e4 fa 4e ef  e9 e3 e7 d8 62 ce 86 c4  |#.....N.....b...|
00000070  6c bb 3b b3 03 ac e5 a5  f3 a0 3b 9d cd 9a ae 99  |l.;.......;.....|
00000080  e2 99 6c 9b 41 9e 5f a2  ae a7 29 ae ad b5 30 be  |..l.A._...)...0.|
00000090  85 c7 9e d1 49 dc 72 e7  e7 f2 8b fe 31 0a b9 15  |....I.r.....1...|
000000a0  f5 20 c6 2b 03 36 90 3f  48 48 0e 50 cf 56 68 5c  |. .+.6.?HH.P.Vh\|
000000b0  d8 60 fc 63 dd 65 62 66  9e 65 7c 63 1c 60 74 5b  |.`.c.ebf.e|c.`t[|
000000c0  9e 55 ae 4e b5 46 d5 3d  25 34 ca 29 df 1e 94 13  |.U.N.F.=%4.)....|
000000d0  04 08 5a fc be f0 51 e5  41 da aa cf b8 c5 85 bc  |..Z...Q.A.......|
000000e0  30 b4 de ac 97 a6 82 a1  9f 9d 0a 9b bd 99 cb 99  |0...............|
000000f0  28 9b d4 9d ca a1 f4 a6  49 ad b1 b4 10 bd 52 c6  |(.......I.....R.|
00000100  4f d0 ef da 05 e6 77 f1  14 fd c0 08 48 14 95 1f  |O.....w.....H...|
00000110  71 2a c7 34 68 3e 3d 47  24 4f 06 56 c6 5b 5a 60  |q*.4h>=G$O.V.[Z`|
00000120  ab 63 b0 65 69 66 c4 65  d6 63 99 60 18 5c 6b 56  |.c.eif.e.c.`.\kV|
00000130  9a 4f c1 47 ff 3e 63 35  1e 2b 43 20 02 15 78 09  |.O.G.>c5.+C ..x.|
```