package rocketscompiler

import java.io.File

val testProgramFolder = File("/Users/kmetiuk/Library/Application Support/com.jundroo.SimpleRockets2/UserData/FlightPrograms/")
@main def main2 =
  def mainSequence: Block = onStart {
    activateStage()
    displayText("LIFTOFF!!!")
    Input.Throttle := 1

    waitUntil(CraftProperty.Altitude.ASL > 500)
    displayText("Starting the roll!")
    Autopilot.Heading := 270
    Autopilot.Pitch := 0

    waitUntil(CraftProperty.Fuel.FuelInStage === 0)
    lockHeading(Heading.Retrograde)
    ifElse(CraftProperty.Altitude.AGL > 1000) {
      displayText("Yeeey, we survived!")
      waitSeconds(3)
    } {
      displayText("We've almost crashed here!")
    }
    activateStage()

    waitUntil(CraftProperty.Altitude.AGL < 500)
    activateStage()
  }

  program("eeeTestProgram", testProgramFolder)(
    mainSequence,
    onPartExplode {
      displayText("You just blew up!")
    }
  )

type StagedBlock = BlockBuilder ?=> Unit
def block(f: StagedBlock, event: Event | Null = null): Block =
  val pa = BlockBuilder(event)
  f(using pa)
  pa.mkBlock

def program(name: String, programFolder: File)(bs: Block*): Unit =
  val p = Program(name, bs.toList)
  writeProgram(File(programFolder, s"$name.xml"), compile(p))

def onStart(b: StagedBlock): Block = block(b, FlightStart)
def onPartExplode(b: StagedBlock): Block = block(b, PartExplode)


import scala.collection.mutable.ListBuffer
class BlockBuilder(event: Event | Null):
  private val lb = ListBuffer.empty[Instruction]
  def pushInstruction(x: Instruction) = lb.append(x)
  def mkBlock = Block(event, lb.toList)

def activateStage()(using pb: BlockBuilder) = pb.pushInstruction(ActivateStage)
extension (prop: Input) def :=(value: Expr)(using pb: BlockBuilder) = pb.pushInstruction(SetInput(prop, value))
extension (prop: Autopilot) def :=(value: Expr)(using pb: BlockBuilder) = pb.pushInstruction(SetTargetHeading(prop, value))
def lockHeading(hdg: String)(using pb: BlockBuilder) = pb.pushInstruction(LockHeading(hdg))
def waitUntil(expr: Expr)(using pb: BlockBuilder) = pb.pushInstruction(WaitUntil(expr))
def waitSeconds(expr: Expr)(using pb: BlockBuilder) = pb.pushInstruction(WaitSeconds(expr))

def ifTrue(condition: Expr)(body: BlockBuilder ?=> Unit)(using pb: BlockBuilder) =
  pb.pushInstruction(If(condition, block(body), Block(null, Nil)))
def ifElse(condition: Expr)(body: BlockBuilder ?=> Unit)(elseBody: BlockBuilder ?=> Unit)(using pb: BlockBuilder) =
  pb.pushInstruction(If(condition, block(body), block(elseBody)))

def displayText(text: Expr)(using pb: BlockBuilder) = pb.pushInstruction(DisplayText(text))
