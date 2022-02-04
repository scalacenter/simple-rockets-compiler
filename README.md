# Simple Rockets 2 Compiler
This compiler allows you to define [Simple Rockets 2](https://www.simplerockets.com/) spacecraft flight programs using a [Scala 3](https://www.scala-lang.org/) DSL. This is a working prototype. Here's how to use it at its current stage of development:

## Compiling Examples
All the commands below are to be executed from a command line of your OS.

1. Install [sbt](https://www.scala-sbt.org/) and [git](https://git-scm.com/)
2. Clone the repo: `git clone https://github.com/anatoliykmetyuk/simple-rockets-compiler.git`
3. Run `sbt` from the repo – this will open an interactive console
4. Run `project examples` – the repo contains two projects, one with the core functionality and another with example Simple Rockets 2 programs
5. Run `run` to compile the sample [Orbit](examples/src/main/scala/rocketscompiler/examples/Orbit.scala) program and write it to Simple Rockets 2 under the name Orbit

## Loading Example Program to Simple Rockets 2 Craft
1. From Simple Rockets 2, open any craft
2. Click the menu button at the top-left of the screen, click "Edit Program"
3. From the program editor, click the menu button again, select "Load Program"
4. Find the "Orbit" program and load it

The example program in question is designed to put a simple spacecraft with up to two stages into a 70km Droo orbit, given enough delta-v for the operation.

## Documentation
TODO