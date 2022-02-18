package rocketscompiler

import java.io.File
import scala.collection.mutable.ListBuffer

import rocketscompiler.compiler.*


/**
 * Use this entrypoint if you have more than one event you'd like to react to.
 * This entrypoint can be useful e.g. if you want to start programs in parallel at the start of the game.
 * In this situation, you can use it as follows:
 *
 * {{{
 *  program("Foo")(
 *    onStart { program1 },
 *    onStart { program2 }
 *  }
 * }}}
 *
 * @param name the name under which the program will be written to the game.
 * @group Entrypoint */
def program(name: String, programFolder: File = flightProgramsFolder)(bs: Callback*): Unit =
  val p = Program(name, bs.toList)
  val targetFile = File(programFolder, s"$name.xml")
  writeProgram(targetFile, compile(p))
  println(s"Written $targetFile")

/**
 * The program defined by this entry point will be executed at the start of the game. E.g.:
 * {{{
 *  program("Foo") {
 *    displayText("Hello World")
 *  }
 * }}}
 *
 * @param name the name under which the program will be written to the game.
 * @group Entrypoint */
def programOnStart(name: String, programFolder: File = flightProgramsFolder)(p: SRProgram): Unit =
  program(name, programFolder)(onStart(p))

private[rocketscompiler] def callback(p: SRProgram, event: Event): Callback =
  Callback(event, reify(p))

private[rocketscompiler] class BlockBuilder:
  private val ib = ListBuffer.empty[Instruction]
  def pushInstruction(x: Instruction) = ib.append(x)
  def mkBlock = Block(ib.toList)

extension (i: Instruction) private[rocketscompiler] def stage: SRProgram =
  (bldr: BlockBuilder) ?=> bldr.pushInstruction(i)

private[rocketscompiler] def reify(srp: SRProgram): Block =
  val pa = BlockBuilder()
  srp(using pa)
  pa.mkBlock
