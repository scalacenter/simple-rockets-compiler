package rocketscompiler
package dsl

import java.io.File
import scala.collection.mutable.ListBuffer

import rocketscompiler.compiler.*


def program(name: String, programFolder: File = flightProgramsFolder)(bs: Callback*): Unit =
  val p = Program(name, bs.toList)
  writeProgram(File(programFolder, s"$name.xml"), compile(p))

def programOnStart(name: String, programFolder: File = flightProgramsFolder)(p: SRProgram): Unit =
  program(name, programFolder)(onStart(p))

private[dsl] def callback(p: SRProgram, event: Event): Callback =
  Callback(event, reify(p))

class BlockBuilder:
  private val ib = ListBuffer.empty[Instruction]
  def pushInstruction(x: Instruction) = ib.append(x)
  def mkBlock = Block(ib.toList)

extension (i: Instruction) def stage: SRProgram =
  (bldr: BlockBuilder) ?=> bldr.pushInstruction(i)

def reify(srp: SRProgram): Block =
  val pa = BlockBuilder()
  srp(using pa)
  pa.mkBlock
