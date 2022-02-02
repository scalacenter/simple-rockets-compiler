package rocketscompiler


case class Program(val instructions: List[CraftInstruction])

sealed trait CraftInstruction
case object ActivateStage extends CraftInstruction
case class WaitUntil(value: Expr) extends CraftInstruction
case class LockHeading(hdg: String) extends CraftInstruction
case class SetInput(input: String, value: Expr) extends CraftInstruction
case class SetTargetHeading(hdg: String, value: Expr) extends CraftInstruction

sealed trait Expr
case class Comparison(sign: String, lhs: Expr, rhs: Expr) extends Expr
case class Constant(x: Double) extends Expr
case class CraftProperty(name: String) extends Expr


object Input:
  val Throttle = "throttle"
object Autopilot:
  val Pitch = "pitch"
object Heading:
  val Retrograde = "Retrograde"
object CraftProperty:
  object Fuel:
    val FuelInStage = CraftProperty("Fuel.FuelInStage")
  object Altitude:
    val ASL = CraftProperty("Altitude.ASL")
    val AGL = CraftProperty("Altitude.AGL")
