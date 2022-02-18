//> using scala "3.1.1"
import $dep.`com.akmetiuk::simple-rockets-compiler:0.1.0`, rocketscompiler.dsl.{ *, given }

/**
 * This example is designed to beat the Low Flyer built into game.
 * The objective of the challenge is to fly as far as possible on a single stage
 * while not exceeding 5km above sea level in altitude.
 */
programOnStart("Low Flyer") {
  for i <- 5 to 1 by -1 do
    displayText(i)
    waitSeconds(1)

  activateStage()
  Heading := 270
  Throttle := 1
  Pitch := 25

  waitUntil(Altitude.ASL >= 1500)
  whileLoop(true) {
    def maxTWR = Performance.MaxActiveEngineThrust / (Performance.Mass * 9.8)
    def throttleForTWR(targetTWR: Double): SRProgram =
      Throttle := targetTWR / maxTWR

    ifTrue(Altitude.ASL <= 3800 ) {
      Pitch := 15
      throttleForTWR(1)
    }
    ifTrue(Altitude.ASL >= 4000) {
      Pitch := 10
      throttleForTWR(0.4)
    }
  }
}