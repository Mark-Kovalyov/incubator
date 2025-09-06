cmake_minimum_required(VERSION 2.8)
project(factorize)

option (USE_FLOATING_SQRT "Use float sqrt implementation" OFF)

if (USE_FLOATING_SQRT) 
  add_definitions(-DFLOATING_SQRT)
endif (USE_FLOATING_SQRT)

add_executable(factorize src/factorize.cpp)

install(TARGETS factorize RUNTIME DESTINATION bin)