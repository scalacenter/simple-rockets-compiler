package rocketscompiler
package compiler


private[rocketscompiler] sealed trait Structural
private[rocketscompiler] case class Program(name: String, bodies: List[Callback]) extends Structural
private[rocketscompiler] case class Block(instructions: List[Instruction]) extends Structural
private[rocketscompiler] case class Callback(event: Event, block: Block) extends Structural

private[rocketscompiler] sealed trait Event
private[rocketscompiler] case object FlightStart extends Event
private[rocketscompiler] case object PartExplode extends Event

private[rocketscompiler] sealed trait Instruction
private[rocketscompiler] case object ActivateStage extends Instruction
private[rocketscompiler] case class LockHeading(hdg: String) extends Instruction
private[rocketscompiler] case class SetInput(input: CraftProperty, value: Expr) extends Instruction
private[rocketscompiler] case class SetTargetHeading(hdg: CraftProperty, value: Expr) extends Instruction

// Control flow
private[rocketscompiler] case class WaitUntil(value: Expr) extends Instruction
private[rocketscompiler] case class WaitSeconds(sds: Expr) extends Instruction
private[rocketscompiler] case class Repeat(times: Expr, body: Block) extends Instruction
private[rocketscompiler] case class While(condition: Expr, body: Block) extends Instruction
private[rocketscompiler] case class ForLoop(varName: String, from: Expr, to: Expr, by: Expr, body: Block) extends Instruction
private[rocketscompiler] case object Break extends Instruction
private[rocketscompiler] case class If(condition: Expr, body: Block, elseBody: Block) extends Instruction
private[rocketscompiler] case class DisplayText(text: Expr) extends Instruction
private[rocketscompiler] case class LogMessage(text: Expr) extends Instruction


sealed trait Expr  // TODO make typed
case class Operator(opNodeType: String, op: String, style: String, operands: Expr*)(customParams: (String, String)*) extends Expr
case class Not(rhs: Expr) extends Expr
case class Constant[T](style: String, x: T) extends Expr
case class CraftProperty(name: String, style: String, setterName: String = "") extends Expr
case class VarRef(variableName: String, list: Boolean, local: Boolean) extends Expr
case class Vector(x: Expr, y: Expr, z: Expr) extends Expr
