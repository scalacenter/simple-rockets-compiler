package rocketscompiler

import rocketscompiler.compiler.*


given [T <: Double | Int]: Conversion[T, Constant[T]] = Constant[T]("number", _)
given Conversion[String, Constant[String]] = Constant("text", _)
given Conversion[Boolean, Constant[Boolean]] = Constant("bool", _)


extension (x: Expr)
  def >(y: Expr): Expr = Operator("Comparison", "g", "op-gt", List(x, y))
  def <(y: Expr): Expr = Operator("Comparison", "l", "op-lt", List(x, y))
  def >=(y: Expr): Expr = Operator("Comparison", "ge", "op-gte", List(x, y))
  def <=(y: Expr): Expr = Operator("Comparison", "le", "op-lte", List(x, y))
  def ===(y: Expr): Expr = Operator("Comparison", "=", "op-eq", List(x, y))
  def =!=(y: Expr): Expr = !(x === y)

  def &&(y: Expr): Expr= Operator("BoolOp", "and", "op-and", List(x, y))
  def ||(y: Expr): Expr = Operator("BoolOp", "or", "op-or", List(x, y))
  def unary_! : Expr = Not(x)

  def +(y: Expr): Expr = Operator("BinaryOp", "+", "op-add", List(x, y))
  def -(y: Expr): Expr = Operator("BinaryOp", "-", "op-sub", List(x, y))
  def *(y: Expr): Expr = Operator("BinaryOp", "*", "op-mul", List(x, y))
  def /(y: Expr): Expr = Operator("BinaryOp", "/", "op-div", List(x, y))
  def ^(y: Expr): Expr = Operator("BinaryOp", "^", "op-exp", List(x, y))
  def %(y: Expr): Expr = Operator("BinaryOp", "%", "op-mod", List(x, y))

extension (x: Expr)  // TODO type it to only work for String Exprs
  def letter(n: Expr) = Operator("StringOp", "letter", "letter", List(n, x))
  def substring(from: Expr, to: Expr) = Operator("StringOp", "substring", "substring", List(from, to, x))
  def contains(str: Expr) = Operator("StringOp", "contains", "contains", List(x, str))
  def join(str2: Expr) = Operator("StringOp", "join", "join", List(x, str2))
  def length = Operator("StringOp", "length", "length", List(x))

def friendly(x: Expr, format: String) = Operator("StringOp", "friendly", "friendly", List(x), Map("subOp" -> format))
extension (x: Expr)
  def acceleration = friendly(x, "acceleration")
  def angularVelocity = friendly(x, "angularvelocity")
  def density = friendly(x, "density")
  def distance = friendly(x, "distance")
  def energy = friendly(x, "energy")
  def force = friendly(x, "force")
  def specificImpulse = friendly(x, "isp")
  def mass = friendly(x, "mass")
  def power = friendly(x, "power")
  def pressure = friendly(x, "pressure")
  def temperature = friendly(x, "temperature")
  def time = friendly(x, "time")
  def velocity = friendly(x, "velocity")

extension (v: Vector)
  def x = Operator("VectorOp", "x", "vec-op-1", List(v))
  def y = Operator("VectorOp", "y", "vec-op-1", List(v))
  def z = Operator("VectorOp", "z", "vec-op-1", List(v))
  def length = Operator("VectorOp", "length", "vec-op-1", List(v))
  def norm = Operator("VectorOp", "norm", "vec-op-1", List(v))

  def angle(v2: Vector) = Operator("VectorOp", "angle", "vec-op-2", List(v, v2))
  def clamp(v2: Vector) = Operator("VectorOp", "clamp", "vec-op-2", List(v, v2))
  def cross(v2: Vector) = Operator("VectorOp", "cross", "vec-op-2", List(v, v2))
  def dot(v2: Vector) = Operator("VectorOp", "dot", "vec-op-2", List(v, v2))
  def dist(v2: Vector) = Operator("VectorOp", "dist", "vec-op-2", List(v, v2))
  def min(v2: Vector) = Operator("VectorOp", "min", "vec-op-2", List(v, v2))
  def max(v2: Vector) = Operator("VectorOp", "max", "vec-op-2", List(v, v2))
  def project(v2: Vector) = Operator("VectorOp", "project", "vec-op-2", List(v, v2))
  def scale(v2: Vector) = Operator("VectorOp", "scale", "vec-op-2", List(v, v2))

extension(x: AssignableCraftProperty)
  def :=(y: Expr): SRProgram = x match
    case x@CraftProperty(name, _, _) if name.startsWith("Heading") => SetTargetHeading(x, y).stage
    case x@CraftProperty(name, _, _) => SetInput(x, y).stage

def rand(from: Expr, to: Expr): Expr = Operator("BinaryOp", "rand", "op-rand", List(from, to))
def min(x: Expr, y: Expr): Expr = Operator("BinaryOp", "min", "op-min", List(x, y))
def max(x: Expr, y: Expr): Expr = Operator("BinaryOp", "max", "op-max", List(x, y))
def atan2(x: Expr, y: Expr): Expr = Operator("BinaryOp", "atan2", "op-atan-2", List(x, y))

def abs(x: Expr): Expr = MathFunction("abs", x)
def floor(x: Expr): Expr = MathFunction("floor", x)
def ceiling(x: Expr): Expr = MathFunction("ceiling", x)
def round(x: Expr): Expr = MathFunction("round", x)
def sqrt(x: Expr): Expr = MathFunction("sqrt", x)
def sin(x: Expr): Expr = MathFunction("sin", x)
def cos(x: Expr): Expr = MathFunction("cos", x)
def tan(x: Expr): Expr = MathFunction("tan", x)
def asin(x: Expr): Expr = MathFunction("asin", x)
def acos(x: Expr): Expr = MathFunction("acos", x)
def atan(x: Expr): Expr = MathFunction("atan", x)
def ln(x: Expr): Expr = MathFunction("ln", x)
def log(x: Expr): Expr = MathFunction("log", x)
def deg2rad(x: Expr): Expr = MathFunction("deg2rad", x)
def rad2deg(x: Expr): Expr = MathFunction("rad2deg", x)
