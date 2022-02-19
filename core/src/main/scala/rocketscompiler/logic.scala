package rocketscompiler

import rocketscompiler.compiler.*


// Types
/**
 * A block of statements that will be written to the game
 * as a program. Primitive statements of this type, such as `activateStage()` to start the engine
 * or assignment statements such as `Thruottle := 1`, are provided by this package.
 * You can group them in blocks if you explicitly type those blocks as `SRProgram`.
 * For example:
 *
 *  {{{
 *  def helloWorld: SRProgram =
 *    displayText("Hello")
 *    waitSeconds(1)
 *    displayText("World")
 *  }}}
 *
 * Under the hood, `SRProgram` is a context function which takes the object that
 * will remember all the expressions meant to be written to the game. If you omit the
 * type, the compiler will complain that it can't find that object.
 *
 * @group Types
 */
type SRProgram = BlockBuilder ?=> Unit
/**
 * A reaction to some event, such as the start of the game or a part
 * exploding.
 *
 * @group Types
 */
type Callback = compiler.Callback
/**
 * An expression, such as `2 + 2`.
 *
 * @group Types
 */
type Expr = compiler.Expr

// Events
/** @group Callbacks */
def onStart(b: SRProgram): Callback = callback(b, FlightStart)

/** @group Callbacks */
def onPartExplode(b: SRProgram): Callback = callback(b, PartExplode)

// Autopilot
/**
 * Triggers staging event.
 *
 * @group Autopilot */
def activateStage(): SRProgram = ActivateStage.stage
/**
 * Possible values for `hdg` are [[Retrograde]] or [[Prograde]].
 * [[Retrograde]] means "back" and [[Prograde]] means "forward" (technically those directions are
 * defined by the current velocity vector of the spacecraft).
 * @group Autopilot */
def lockHeading(hdg: String): SRProgram = LockHeading(hdg).stage

// Control Flow
// Waiting
/**
 * Blocks the program until the `expr` Boolean condition is `true`.
 * @group Control */
def waitUntil(expr: Expr): SRProgram = WaitUntil(expr).stage
/** @group Control */
def waitSeconds(expr: Expr): SRProgram = WaitSeconds(expr).stage
// Loops
/** @group Control */
def repeat(times: Expr)(body: SRProgram): SRProgram = Repeat(times, reify(body)).stage
/** @group Control */
def whileLoop(condition: Expr)(body: SRProgram): SRProgram = While(condition, reify(body)).stage
/**
 * Custom variables are not implemented yet in this library, so for the time being there is no way to use
 * `varName` from the `forLoop`, and the loop is thus equivalent to `repeat`.
 * @group Control */
def forLoop(varName: String, from: Expr, to: Expr, by: Expr)(body: SRProgram): SRProgram = ForLoop(varName, from, to, by, reify(body)).stage
/**
 * Immediately exit the loop where `break()` is called from.
 * @group Control */
def break(): SRProgram = Break.stage
// Conditions
/** @group Control */
def ifTrue(condition: Expr)(body: SRProgram): SRProgram = If(condition, reify(body), Block(Nil)).stage
/** @group Control */
def ifElse(condition: Expr)(body: SRProgram)(elseBody: SRProgram): SRProgram = If(condition, reify(body), reify(body)).stage
// Output
/**
 * Display text on screen for the player to see.
 *
 * @group Control */
def displayText(text: Expr): SRProgram = DisplayText(text).stage
/**
 * Log without displaying to the player. Logs can be viewed at the end of the flight.
 * @group Control */
def logMessage(text: Expr): SRProgram = LogMessage(text).stage


