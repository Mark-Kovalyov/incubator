# Markov

## Graalvm dependencies
```

```

## Shenandouh

```
set SBT_OPTS="-XX:+UseShenandoahGC -Xmx6G"
```
## Enable G1GC
```

```

## Enable ZGC
```
set SBT_OPTS="-XX:+UseZGC -Xmx6G"
```
Unfortunatelly ZGC can produce lags
```antlrv4-tool
[warn] In the last 6 seconds, 5.763 (100.0%) were spent in GC. [Heap: 4.72GB free of 6.00GB, max 6.00GB] Consider increasing the JVM heap using `-Xmx` or try a different collector, e.g. `-XX:+UseG1GC`, for better performance.
```



## Compile with deprecation

```
set ThisBuild/scalacOptions ++= Seq("-unchecked", "-deprecation")



```