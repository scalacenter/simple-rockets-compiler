package rocketscompiler


def compile(p: Program): String =
  s"""|<FlightProgram>
      |  <Program name="New Program">
      |    <Instructions>
      |      <Event event="FlightStart"/>
      |      ${p.instructions.map(compile).mkString("\n")}
      |    </Instructions>
      |  </Program>
      |</FlightProgram>""".stripMargin

def compile(i: CraftInstruction): String = i match
  case ActivateStage =>
    """<ActivateStage style="activate-stage"/>"""
  case WaitUntil(e) =>
    s"""<WaitUntil style="wait-until">${compile(e)}</WaitUntil>"""
  case LockHeading(hdg) =>
    s"""<LockNavSphere indicatorType="$hdg" style="lock-nav-sphere"/>"""
  case SetInput(input, value) => s"""
    |<SetInput input="$input" style="set-input">
    |  ${compile(value)}
    |</SetInput>""".stripMargin
  case SetTargetHeading(hdg, value) => s"""
    |<SetTargetHeading property="$hdg" style="set-heading">
    |  ${compile(value)}
    |</SetTargetHeading>""".stripMargin

def compile(e: Expr): String = e match
  case Comparison(sign, lhs, rhs) => s"""
    |<Comparison op="$sign">
    |  ${compile(lhs)}
    |  ${compile(rhs)}
    |</Comparison>""".stripMargin
  case Constant(x) => s"""<Constant text="$x" />"""
  case CraftProperty(name) => s"""<CraftProperty property="$name" />"""
