package rocketscompiler

import rocketscompiler.compiler.*


given [T <: Double | Int]: Conversion[T, Constant[T]] = Constant[T]("number", _)
given Conversion[String, Constant[String]] = Constant("text", _)
given Conversion[Boolean, Constant[Boolean]] = Constant("bool", _)


extension (x: Expr)
  def >(y: Expr) = BinaryOp("Comparison", "g", "op-gt", x, y)
  def <(y: Expr) = BinaryOp("Comparison", "l", "op-lt", x, y)
  def >=(y: Expr) = BinaryOp("Comparison", "ge", "op-gte", x, y)
  def <=(y: Expr) = BinaryOp("Comparison", "le", "op-lte", x, y)
  def ===(y: Expr) = BinaryOp("Comparison", "=", "op-eq", x, y)
  def =!=(y: Expr) = !(x === y)

  def &&(y: Expr) = BinaryOp("BoolOp", "and", "op-and", x, y)
  def ||(y: Expr) = BinaryOp("BoolOp", "or", "op-or", x, y)
  def unary_! = Not(x)

  def +(y: Expr) = BinaryOp("BinaryOp", "+", "op-add", x, y)
  def -(y: Expr) = BinaryOp("BinaryOp", "-", "op-sub", x, y)
  def *(y: Expr) = BinaryOp("BinaryOp", "*", "op-mul", x, y)
  def /(y: Expr) = BinaryOp("BinaryOp", "/", "op-div", x, y)
  def ^(y: Expr) = BinaryOp("BinaryOp", "^", "op-exp", x, y)
  def %(y: Expr) = BinaryOp("BinaryOp", "%", "op-mod", x, y)

extension(x: AssignableCraftProperty)
  def :=(y: Expr): SRProgram = x match
    case x@CraftProperty(name, _, _) if name.startsWith("Input") => SetInput(x, y).stage
    case x@CraftProperty(name, _, _) if name.startsWith("Heading") => SetTargetHeading(x, y).stage
