package rocketscompiler
package compiler


private[rocketscompiler] def compile(x: Structural): String = x match
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
end compile

private[rocketscompiler] def compile(e: Event): String = e match
  case FlightStart =>
    """<Event event="FlightStart" style="flight-start" />"""
  case PartExplode =>
    """<Event event="PartExplode" style="part-explode" />"""
end compile

private[rocketscompiler] def compile(i: Instruction): String = i match
  case ActivateStage =>
    """<ActivateStage style="activate-stage"/>"""
  case LockHeading(hdg) =>
    s"""<LockNavSphere indicatorType="$hdg" style="lock-nav-sphere"/>"""
  case SetInput(CraftProperty(_, _, input), value) => s"""
    |<SetInput input="$input" style="set-input">
    |  ${compile(value)}
    |</SetInput>""".stripMargin
  case SetTargetHeading(CraftProperty(_, _, hdg), value) => s"""
    |<SetTargetHeading property="$hdg" style="set-heading">
    |  ${compile(value)}
    |</SetTargetHeading>""".stripMargin

  // Control Flow
  case WaitUntil(e) =>
    s"""<WaitUntil style="wait-until">${compile(e)}</WaitUntil>"""
  case WaitSeconds(e) =>
    s"""<WaitSeconds style="wait-seconds">${compile(e)}</WaitSeconds>"""
  case Repeat(times, body) => s"""
    |<Repeat style="repeat">
    |  ${compile(times)}
    |  ${compile(body)}
    |</Repeat>""".stripMargin
  case While(condition, body) => s"""
    |<While style="while">
    |  ${compile(condition)}
    |  ${compile(body)}
    |</While>""".stripMargin
  case ForLoop(varName, from, to, by, body) => s"""
    |<For var="$varName" style="for">
    |  ${compile(from)}
    |  ${compile(to)}
    |  ${compile(by)}
    |  ${compile(body)}
    |</For>

    """.stripMargin
  case Break => """<Break style="break" />"""
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
  case LogMessage(text) => s"""
    |<LogMessage style="log">
    |  ${compile(text)}
    |</LogMessage>""".stripMargin
end compile

private[rocketscompiler] def compile(e: Expr): String = e match
  case BinaryOp(opType, sign, style, lhs, rhs) => s"""
    |<$opType op="$sign" style="$style">
    |  ${compile(lhs)}
    |  ${compile(rhs)}
    |</$opType>""".stripMargin
  case Not(rhs) => s"""<Not style="op-not">${compile(rhs)}</Not>"""
  case Constant(_, x: Boolean) => s"""<Constant style="$x" bool="$x" />"""
  case Constant(tpe, x) => s"""<Constant $tpe="$x" />"""
  case CraftProperty(name, style, _) => s"""<CraftProperty property="$name" style="$style" />"""
  case VarRef(name, list, local) => s"""<Variable list="${list}" local="${local}" variableName="${name}" />"""
end compile
