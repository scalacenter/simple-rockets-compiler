package rocketscompiler
package compiler


case class Program(name: String, bodies: List[Block])
case class Block(onEvent: Event | Null, instructions: List[Instruction])

sealed trait Event
case object FlightStart extends Event
case object PartExplode extends Event

sealed trait Instruction
case object ActivateStage extends Instruction
case class LockHeading(hdg: String) extends Instruction
case class SetInput(input: Input, value: Expr) extends Instruction
case class SetTargetHeading(hdg: Autopilot, value: Expr) extends Instruction

case class WaitUntil(value: Expr) extends Instruction
case class WaitSeconds(sds: Expr) extends Instruction
case class If(condition: Expr, body: Block, elseBody: Block) extends Instruction
case class DisplayText(text: Expr) extends Instruction


sealed trait Expr
case class Comparison(sign: String, style: String, lhs: Expr, rhs: Expr) extends Expr
case class NumConstant(x: Double) extends Expr
case class StrConstant(txt: String) extends Expr
case class CraftProperty(name: String, style: String) extends Expr


case class Input(name: String)
case class Autopilot(name: String)
