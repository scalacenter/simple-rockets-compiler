# Simple Rockets 2 Compiler
[![javadoc](https://javadoc.io/badge2/com.akmetiuk/simple-rockets-compiler_3/javadoc.svg)](https://javadoc.io/doc/com.akmetiuk/simple-rockets-compiler_3/latest/rocketscompiler.html)

This compiler allows you to define [Simple Rockets 2](https://www.simplerockets.com/) spacecraft flight programs using a [Scala 3](https://www.scala-lang.org/) DSL.

## Prerequisites
1. [Simple Rockets 2](https://www.simplerockets.com/) installed
2. [Scala CLI](https://scala-cli.virtuslab.org/) installed
3. Basic command line skills

## Usage
Create a new file `Hello.sc` with the following content:

```scala
//> using scala "3.1.1"
//> using lib "com.akmetiuk::simple-rockets-compiler:0.1.1"
import rocketscompiler.{ *, given }

program("Hello") {
  displayText("Liftoff!")
  activateStage()
  Throttle := 1
}
```

Run `scala-cli Hello.sc`. This will convert the script into the Simple Rockets 2 program and write it to the game.

Next, from the game, open whatever craft you want to execute the project on. Click the menu button at the top-left of the screen, click "Edit Program". From the program editor, click the menu button again, select "Load Program". Find the "Hello" program and load it.

## Possible Issues
Sometimes when you compile a script as described above, you may not find the program in Simple Rockets. Chances are the DSL failed at detecting the directory where Simple Rockets looks for programs correctly. If that is the case, you need to manually find out the location (e.g. described [here](https://steamcommunity.com/app/870200/discussions/0/1750106661716039638/)). Then figure out where the game store the flight programs in that directory, by default should be `UserData/FlightPrograms/` relative to the game directory.

Once you have the directory, you can explicitly specify it when writin a program as follows: `program("Hello", java.io.File("/path/to/your/dir"))`

## Documentation
The scripting language is still a work in progress, so not everything is ported yet from the game's built-in language. See the complete list of what's available [here](https://javadoc.io/doc/com.akmetiuk/simple-rockets-compiler_3/latest/rocketscompiler.html).

## Workshop
Following are some materials to organise the Scala in Science workshop:

- [Presentation](https://docs.google.com/presentation/d/1tDTqdbgliSJ_D4Vg3D10M-wf3HoTTkweLljl1eEbjNI/edit?usp=sharing)
