spark.read.format("csv").load("/mnt/c/git.my/incubator/azure-set-tier/bin/out2.csv").select(regexp_extract(col("_c1"), "file:(.*)", 1).as("substr")).distinct().orderBy(col("substr")).show(400,false)
