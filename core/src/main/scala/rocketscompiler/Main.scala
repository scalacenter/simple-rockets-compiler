package rocketscompiler

import java.io.File
import rocketscompiler.dsl.{ *, given }

val testProgramFolder = File("/Users/kmetiuk/Library/Application Support/com.jundroo.SimpleRockets2/UserData/FlightPrograms/")
@main def main2 =
  def mainSequence = onStart {
    activateStage()
    displayText("LIFTOFF!!!")
    Throttle := 1

    waitUntil(Altitude.ASL > 500)
    displayText("Starting the roll!")
    Heading := 270
    Pitch := 0

    waitUntil(Fuel.FuelInStage === 0)
    lockHeading(Retrograde)
    ifElse(Altitude.AGL > 1000) {
      displayText("Yeeey, we survived!")
      waitSeconds(3)
    } {
      displayText("We've almost crashed here!")
    }
    activateStage()

    waitUntil(Altitude.AGL < 500)
    activateStage()
  }

  program("eeeTestProgram", testProgramFolder)(
    mainSequence,
    onPartExplode {
      displayText("You just blew up!")
    }
  )
