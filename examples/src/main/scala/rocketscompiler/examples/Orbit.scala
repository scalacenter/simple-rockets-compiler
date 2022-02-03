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

  def ascentLeg(startAltitude: Expr, pitch: Expr): SRProgram =
    waitUntil(Altitude.ASL >= startAltitude)
    Pitch := pitch

  def coasting(targetApo: Expr): SRProgram =
    waitUntil(Orbit.Apoapsis >= targetApo)
    Throttle := 0

  def periapsisRiseManeuver(targetOrbitalVelicity: Expr, targetPeriapsis: Expr): SRProgram =
    val maxStageAcceleration = Performance.StageDeltaV / Performance.BurnTime
    val burnDeltaV = targetOrbitalVelicity - Velocity.OrbitVelocity
    val burnTime = burnDeltaV / maxStageAcceleration
    val startBurnAt = burnTime / 2

    waitUntil(Orbit.TimeToApoapsis <= startBurnAt)
    Pitch := 0
    Throttle := 1

    whileLoop(Orbit.Periapsis < targetPeriapsis) {
      ifTrue(Orbit.TimeToApoapsis > 60 && Throttle =!= 0) {
        Throttle := 0
      }
      ifTrue(Orbit.TimeToApoapsis < 10 && Throttle =!= 1) {
        Throttle := 0.2
      }
    }
    Throttle := 0
    displayText("Congratulations! You've reached orbit with Scala!")

  countdown
  liftOff
  ascentLeg(startAltitude = 1000, pitch = 70)
  ascentLeg(startAltitude = 3000, pitch = 50)
  ascentLeg(startAltitude = 8000, pitch = 25)
  coasting(targetApo = 70000)
  periapsisRiseManeuver(targetOrbitalVelicity = 3600, targetPeriapsis = 70000)
end ascentProfile

def engineStaging: SRProgram =
  waitUntil(Altitude.AGL > 100 && Fuel.InStage === 0)
  activateStage()
end engineStaging

@main def main =
  program("Orbit", programFolder)(
    onStart { ascentProfile },
    onStart { engineStaging },
  )
