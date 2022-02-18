package rocketscompiler
package dsl

import java.io.File
import scala.collection.mutable.ListBuffer

import rocketscompiler.compiler.*


def program(name: String, programFolder: File = flightProgramsFolder)(bs: Callback*): Unit =
  val p = Program(name, bs.toList)
  val targetFile = File(programFolder, s"$name.xml")
  writeProgram(targetFile, compile(p))
  println(s"Written $targetFile")

def programOnStart(name: String, programFolder: File = flightProgramsFolder)(p: SRProgram): Unit =
  program(name, programFolder)(onStart(p))

private[dsl] def callback(p: SRProgram, event: Event): Callback =
  Callback(event, reify(p))

private[dsl] class BlockBuilder:
  private val ib = ListBuffer.empty[Instruction]
  def pushInstruction(x: Instruction) = ib.append(x)
  def mkBlock = Block(ib.toList)

extension (i: Instruction) private[dsl] def stage: SRProgram =
  (bldr: BlockBuilder) ?=> bldr.pushInstruction(i)

private[dsl] def reify(srp: SRProgram): Block =
  val pa = BlockBuilder()
  srp(using pa)
  pa.mkBlock
