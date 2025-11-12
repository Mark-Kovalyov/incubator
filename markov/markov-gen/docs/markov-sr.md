# Markov Chain based on 'rockyou' dataset

* Requirements
  * rockyou file 
    * 
  * implement markov chain generator
  * consider probability of transitions
    ```
    passw0rd2025$:  pas -> ass -> 
    ```


## Rock you sample:


## Graph generated views

### Top by freq
```scala

scala> spark.sql("select * from graph order by frequency desc").show(false)
+---+---+---------+
|v1 |v2 |frequency|
+---+---+---------+
|(((|((1|694758   |
|(((|((s|678550   |
|(((|((m|664989   |
|(((|((b|536417   |
|(((|((c|531312   |
|(((|((a|527554   |
|(((|((p|435508   |
|(((|((j|430371   |
|(((|((l|420027   |
|(((|((0|369700   |
|(((|((d|361344   |
|(((|((t|346613   |
|(((|((r|307912   |
|(((|((k|296460   |
|((1|(12|287360   |
|(((|((2|284812   |
|((m|(ma|256635   |
|(((|((h|241304   |
|(((|((g|236871   |
|123|234|228487   |
+---+---+---------+
only showing top 20 rows

```

#### Count 1.3 mln
```
scala> graph.count()
val res3: Long = 1 343 124
```

### Frequency view

### Compressed transitions view

## Report 1 (Graphviz)



