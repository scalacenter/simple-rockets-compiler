package rocketscompiler
package compiler


def compile(x: Structural): String = x match
  case Program(name, bs) =>
    s"""<Program name="$name">${bs.map(compile).mkString("\n")}</Program>"""
  case Callback(event, Block(instructions)) => s"""
    |<Instructions>
    |  ${compile(event)}
    |  ${instructions.map(compile).mkString("\n")}
    |</Instructions>""".stripMargin
  case Block(instructions) => s"""
    |<Instructions>
    |  ${instructions.map(compile).mkString("\n")}
    |</Instructions>""".stripMargin

def compile(e: Event): String = e match
  case FlightStart =>
    """<Event event="FlightStart" style="flight-start" />"""
  case PartExplode =>
    """<Event event="PartExplode" style="part-explode" />"""

def compile(i: Instruction): String = i match
  case ActivateStage =>
    """<ActivateStage style="activate-stage"/>"""
  case WaitUntil(e) =>
    s"""<WaitUntil style="wait-until">${compile(e)}</WaitUntil>"""
  case WaitSeconds(e) =>
    s"""<WaitSeconds style="wait-seconds">${compile(e)}</WaitSeconds>"""
  case LockHeading(hdg) =>
    s"""<LockNavSphere indicatorType="$hdg" style="lock-nav-sphere"/>"""
  case SetInput(Input(input), value) => s"""
    |<SetInput input="$input" style="set-input">
    |  ${compile(value)}
    |</SetInput>""".stripMargin
  case SetTargetHeading(Autopilot(hdg), value) => s"""
    |<SetTargetHeading property="$hdg" style="set-heading">
    |  ${compile(value)}
    |</SetTargetHeading>""".stripMargin
  case If(condition, body, elseBody) =>
    val elseCompiled = if elseBody.instructions.isEmpty then "" else s"""
      |<ElseIf style="else">
      |  <Constant bool="true" />
      |  ${compile(elseBody)}
      |</ElseIf>""".stripMargin
    s"""
    |<If style="if">
    |  ${compile(condition)}
    |  ${compile(body)}
    |</If>
    |$elseCompiled""".stripMargin
  case DisplayText(text) => s"""
    |<DisplayMessage style="display">
    |  ${compile(text)}
    |  <Constant number="7" />
    |</DisplayMessage>""".stripMargin



def compile(e: Expr): String = e match
  case Comparison(sign, style, lhs, rhs) => s"""
    |<Comparison op="$sign" style="$style">
    |  ${compile(lhs)}
    |  ${compile(rhs)}
    |</Comparison>""".stripMargin
  case NumConstant(x) => s"""<Constant number="$x" />"""
  case StrConstant(x) => s"""<Constant text="$x" />"""
  case CraftProperty(name, style) => s"""<CraftProperty property="$name" style="$style" />"""
