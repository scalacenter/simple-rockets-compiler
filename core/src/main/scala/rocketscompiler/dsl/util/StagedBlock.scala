package rocketscompiler
package dsl

import java.io.File
import scala.collection.mutable.ListBuffer

import rocketscompiler.compiler.*


type StagedBlock = BlockBuilder ?=> Unit
type SimpleRocketsProgram = StagedBlock
def program(name: String, programFolder: File)(bs: Block*): Unit =
  val p = Program(name, bs.toList)
  writeProgram(File(programFolder, s"$name.xml"), compile(p))

def block(f: StagedBlock, event: Event | Null = null): Block =
  val pa = BlockBuilder(event)
  f(using pa)
  pa.mkBlock

class BlockBuilder(event: Event | Null):
  private val lb = ListBuffer.empty[Instruction]
  def pushInstruction(x: Instruction) = lb.append(x)
  def mkBlock = Block(event, lb.toList)

extension (i: Instruction) def push(using bldr: BlockBuilder) =
  bldr.pushInstruction(i)
