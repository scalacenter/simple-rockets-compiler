package rocketscompiler.examples

import rocketscompiler.dsl.{ *, given }


def ascentProfile: SRProgram =
  def countdown: SRProgram =
    for i <- 5 to 1 by -1 do
      displayText(i)
      waitSeconds(1)

  def liftOff: SRProgram =
    activateStage()
    Throttle := 1
    displayText("LIFTOFF!!!")

  def gravityTurn(startAltitude: Double, endAltitude: Double, startPitch: Double, endPitch: Double): SRProgram =
    waitUntil(Altitude.ASL >= startAltitude)
    displayText("Starting the gravity turn")
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
    val burnDeltaV = targetOrbitalVelicity - Velocity.OrbitVelocity
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
  gravityTurn(
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

@main def main =
  program("Orbit")(
    onStart { ascentProfile },
    onStart { engineStaging },
  )
