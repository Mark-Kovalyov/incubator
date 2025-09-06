#!/bin/bash -v

cp ~/.m2/repository/mayton/chess/QueenProblem/1.0-SNAPSHOT/QueenProblem-1.0-SNAPSHOT.jar .

for i in {1..5}
do
  java -cp .:QueenProblem-1.0-SNAPSHOT.jar mayton.chess.experimental.QueensCsvToImageWriter "desk-1000x1000-$i.txt" "desk-1000x1000-$i.png" 1000
done
