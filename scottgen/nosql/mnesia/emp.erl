-module(scott).
-export([install/1]).

-record(emp).

install(Nodes) ->
  ok = mnesia:create_schema(Nodes),
  application:start(mnesia),
  mnesia:create_table(emp),
  application:stop(mnesia).

