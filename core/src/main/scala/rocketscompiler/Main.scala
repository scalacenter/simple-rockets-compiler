package rocketscompiler

@main def main =
  val p = Program(List(
    // Take-off
    ActivateStage,
    SetInput(Input.Throttle, 1),

    // Fly horizontal
    WaitUntil(CraftProperty.Altitude.ASL > 5000),
    SetTargetHeading(Autopilot.Pitch, 20),

    // Ditch empty fuel tank
    WaitUntil(CraftProperty.Fuel.FuelInStage === 0),
    LockHeading(Heading.Retrograde),
    ActivateStage,

    // Deploy parachute
    WaitUntil(CraftProperty.Altitude.AGL < 500),
    ActivateStage,
  ))
  println(compile(p))
