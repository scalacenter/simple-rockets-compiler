//> using scala "3.1.1"
import $dep.`com.akmetiuk::simple-rockets-compiler:0.1.1`, rocketscompiler.{ *, given }

/**
 * This example is designed to put a simple spacecraft with up to two stages into a 70km Droo orbit,
 * given enough delta-v for the operation.
 */
def ascentProfile: SRProgram =
  def countdown: SRProgram =
    for i <- 5 to 1 by -1 do
      displayText(i)
      waitSeconds(1)

  def liftOff: SRProgram =
    activateStage()
    Throttle := 1
    displayText("LIFTOFF!!!")

  def gradualTurn(startAltitude: Double, endAltitude: Double, startPitch: Double, endPitch: Double): SRProgram =
    waitUntil(Altitude.ASL >= startAltitude)
    displayText("Starting the gradual turn")
    whileLoop(Altitude.ASL < endAltitude) {
      val fractionOfPath = (Altitude.ASL - startAltitude) / (endAltitude - startAltitude)
      val pitchDifference = endPitch - startPitch
      Pitch := startPitch + fractionOfPath * pitchDifference
    }

  def coasting(targetApo: Double): SRProgram =
    waitUntil(Orbit.Apoapsis >= targetApo)
    displayText("Coasting towards apoapsis")
    Throttle := 0

  def periapsisRiseManeuver(targetOrbitalVelicity: Double, targetPeriapsis: Double,
      maxTimeToApo: Double, minTimeToApo: Double, correctionThrottle: Double): SRProgram =
    val maxStageAcceleration = Performance.StageDeltaV / Performance.BurnTime
    val burnDeltaV = targetOrbitalVelicity - Velocity.Orbit
    val burnTime = burnDeltaV / maxStageAcceleration
    val startBurnAt = burnTime / 2

    waitUntil(Orbit.TimeToApoapsis <= startBurnAt)
    displayText("Rising periapsis")
    Pitch := 0
    Throttle := 1

    whileLoop(Orbit.Periapsis < targetPeriapsis) {
      ifTrue(Orbit.TimeToApoapsis > maxTimeToApo && Throttle =!= 0) {
        Throttle := 0
      }
      ifTrue(Orbit.TimeToApoapsis < minTimeToApo && Throttle =!= 1) {
        Throttle := correctionThrottle
      }
    }
    Throttle := 0
    displayText("Congratulations! You've reached orbit with Scala!")

  countdown
  liftOff
  gradualTurn(
    startAltitude = 500, startPitch = 80,
    endAltitude = 8000, endPitch = 25
  )
  coasting(targetApo = 70000)
  periapsisRiseManeuver(
    targetOrbitalVelicity = 3420, targetPeriapsis = 70000,
    maxTimeToApo = 60, minTimeToApo = 10, correctionThrottle = 0.2,
  )
end ascentProfile

def engineStaging: SRProgram =
  waitUntil(Altitude.AGL > 100 && Fuel.InStage === 0)
  activateStage()
end engineStaging

callbacks("FlightToOrbit")(
  onStart { ascentProfile },
  onStart { engineStaging },
)
