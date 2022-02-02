## Funky Trees Compiler
This is a runtime-staged compiler that compiles Scala 3 to [Simple Planes'](https://www.simpleplanes.com/) [Funky Trees](https://snowflake0s.github.io/funkyguide/). Based on [LMS work](https://infoscience.epfl.ch/record/150347).

Stages:
1. Scala Macros: Scala program -> Scala program that produces Funky Trees IR
2. Scala Runtime: Program from (1) -> Funky Trees IR
3. IR compilation: IR (2) -> Funky Trees AST
4. Output: AST (3) -> XML, ready to use in the game