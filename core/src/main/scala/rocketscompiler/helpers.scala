package rocketscompiler


given Conversion[Double, Constant] = Constant(_)
given Conversion[Int, Constant] = Constant(_)
extension (x: Expr)
  def >(y: Expr) = Comparison("g", x, y)
  def ===(y: Expr) = Comparison("=", x, y)
  def <(y: Expr) = Comparison("l", x, y)
