package rocketscompiler

// Helpers
given Conversion[Double, Constant] = Constant(_)
given Conversion[Int, Constant] = Constant(_)
extension (x: Expr)
  def >(y: Expr) = Comparison("g", x, y)
  def ===(y: Expr) = Comparison("=", x, y)
  def <(y: Expr) = Comparison("l", x, y)


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
  println(p)
end main


// Constants
enum Input:
  case Throttle

object CraftProperty:
  enum Fuel extends Expr:
    case FuelInStage
  enum Altitude extends Expr:
    case ASL, AGL

enum Autopilot:
  case Pitch

enum Heading:
  case Retrograde

// Domain
case class Program(val instructions: List[CraftInstruction])

sealed trait CraftInstruction
case object ActivateStage extends CraftInstruction
case class WaitUntil(value: Expr) extends CraftInstruction
case class LockHeading(hdg: Heading) extends CraftInstruction
case class SetInput(input: Input, value: Expr) extends CraftInstruction
case class SetTargetHeading(hdg: Autopilot, value: Expr) extends CraftInstruction

sealed trait Expr
case class Comparison(sign: String, lhs: Expr, rhs: Expr) extends Expr
case class Constant(x: Double) extends Expr
