package rocketscompiler

import java.io.File
import rocketscompiler.dsl.{ *, given }

val testProgramFolder = File("/Users/kmetiuk/Library/Application Support/com.jundroo.SimpleRockets2/UserData/FlightPrograms/")

def countdown: SRProgram =
  for i <- 5 to 1 by -1 do
    displayText(i)
    waitSeconds(1)
  displayText("Liftoff!")
  activateStage()
  Throttle := 1

def pitch: SRProgram =
  waitUntil(Altitude.AGL > 500)
  displayText("Pitching for max flight distance")
  Heading := 270
  Pitch := 45

def ditchFuelTank: SRProgram =
  waitUntil(Fuel.InStage === 0)
  ifTrue(Altitude.ASL > 10000) {
    displayText("We've reached higher atmosphere! Congratulations!")
  }
  activateStage()
  lockHeading(Retrograde)

def landing: SRProgram =
  waitUntil(Altitude.AGL < 500)
  displayText("Deploying chute")
  activateStage()

  waitUntil(Altitude.AGL < 10)
  displayText("Touchdown!")

def altitudeCallout(altitude: Int, name: String): SRProgram =
  waitUntil(Altitude.ASL > altitude)
  displayText(s"We've reached $name! Congratulations!")

@main def main2 =
  program("eeeTestProgram", testProgramFolder)(
    onStart {
      countdown
      pitch
      ditchFuelTank
      landing
    },
    onStart {
      altitudeCallout(6000, "Thin Atmosphere")
      altitudeCallout(20000, "Upper Atmosphere")
      altitudeCallout(60000, "Space")
    }
  )
