program main;

type
  TQueenColRow= record
    QueenCol: integer;
    QueenRow: integer;
    end;
 
const
  MaxBoardSize= 100000;
 
var
  BoardSize: integer;
  QueenCount: integer;
  SolutionCount: int64;
  CountCol: array[0..MaxBoardSize-1] of byte;
  CountRow: array[0..MaxBoardSize-1] of byte;
  CountDiagP: array[0..2*MaxBoardSize-2] of byte;
  CountDiagM: array[-MaxBoardSize+1..MaxBoardSize-1] of byte;
  QueenColRow: array[0..MaxBoardSize-1] of TQueenColRow;

begin

end.
