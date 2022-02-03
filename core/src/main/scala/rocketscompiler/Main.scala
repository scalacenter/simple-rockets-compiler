package rocketscompiler

import java.io.File
import rocketscompiler.dsl.{ *, given }

val testProgramFolder = File("/Users/kmetiuk/Library/Application Support/com.jundroo.SimpleRockets2/UserData/FlightPrograms/")

def countdown: SimpleRocketsProgram =
  for i <- 5 to 1 by -1 do
    displayText(i)
    waitSeconds(1)
  displayText("Liftoff!")
  activateStage()
  Throttle := 1

def pitch: SimpleRocketsProgram =
  waitUntil(Altitude.AGL > 500)
  displayText("Pitching for max flight distance")
  Heading := 270
  Pitch := 45

def ditchFuelTank: SimpleRocketsProgram =
  waitUntil(Fuel.InStage === 0)
  ifTrue(Altitude.ASL > 10000) {
    displayText("We've reached higher atmosphere! Congratulations!")
  }
  activateStage()
  lockHeading(Retrograde)

def landing: SimpleRocketsProgram =
  waitUntil(Altitude.AGL < 500)
  displayText("Deploying chute")
  activateStage()

  waitUntil(Altitude.AGL < 10)
  displayText("Touchdown!")

@main def main2 =
  program("eeeTestProgram", testProgramFolder)(onStart {
    countdown
    pitch
    ditchFuelTank
    landing
  })
