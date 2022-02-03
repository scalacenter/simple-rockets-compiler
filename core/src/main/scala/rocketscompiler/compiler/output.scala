package rocketscompiler

import scala.language.unsafeNulls

import org.w3c.dom.*
import org.xml.sax.InputSource
import javax.xml.parsers.*
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.{ StreamResult, StreamSource }
import java.io.*


def writeProgram(file: File, program: String): Unit =
  val docBldr = DocumentBuilderFactory.newInstance().newDocumentBuilder()
  val progDoc: Document = docBldr.parse(InputSource(StringReader(program)))
  writeXml(progDoc, file)

def writeXml(doc: Document, file: File): Unit =
  val transformer = TransformerFactory.newInstance().newTransformer()
  transformer.setOutputProperty(OutputKeys.INDENT, "yes")
  transformer.setOutputProperty(OutputKeys.STANDALONE, "no")
  transformer.transform(DOMSource(doc), StreamResult(file))
