package rocketscompiler
package dsl

import rocketscompiler.compiler.*


// Types
type SRProgram = BlockBuilder ?=> Unit
type Callback = compiler.Callback
type Expr = compiler.Expr

// Events
def onStart(b: SRProgram): Callback = callback(b, FlightStart)
def onPartExplode(b: SRProgram): Callback = callback(b, PartExplode)

// Autopilot
def activateStage(): SRProgram = ActivateStage.stage
def lockHeading(hdg: String): SRProgram = LockHeading(hdg).stage

// Control Flow
// Waiting
def waitUntil(expr: Expr): SRProgram = WaitUntil(expr).stage
def waitSeconds(expr: Expr): SRProgram = WaitSeconds(expr).stage
// Loops
def repeat(times: Expr)(body: SRProgram): SRProgram = Repeat(times, reify(body)).stage
def whileLoop(condition: Expr)(body: SRProgram): SRProgram = While(condition, reify(body)).stage
def forLoop(varName: String, from: Expr, to: Expr, by: Expr)(body: SRProgram): SRProgram = ForLoop(varName, from, to, by, reify(body)).stage
def break(): SRProgram = Break.stage
// Conditions
def ifTrue(condition: Expr)(body: SRProgram): SRProgram = If(condition, reify(body), Block(Nil)).stage
def ifElse(condition: Expr)(body: SRProgram)(elseBody: SRProgram): SRProgram = If(condition, reify(body), reify(body)).stage
// Output
def displayText(text: Expr): SRProgram = DisplayText(text).stage
def logMessage(text: Expr): SRProgram = LogMessage(text).stage


