package rocketscompiler
package dsl

import rocketscompiler.compiler.*


given Conversion[Double, NumConstant] = NumConstant(_)
given Conversion[Int, NumConstant] = NumConstant(_)
given Conversion[String, StrConstant] = StrConstant(_)
extension (x: Expr)
  def >(y: Expr) = Comparison("g", "op-gt", x, y)
  def <(y: Expr) = Comparison("l", "op-lt", x, y)
  def >=(y: Expr) = Comparison("ge", "op-gte", x, y)
  def <=(y: Expr) = Comparison("le", "op-lte", x, y)
  def ===(y: Expr) = Comparison("=", "op-eq", x, y)
