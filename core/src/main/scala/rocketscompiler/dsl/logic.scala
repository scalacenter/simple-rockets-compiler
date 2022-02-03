package rocketscompiler
package dsl

import rocketscompiler.compiler.*


// Types
type SRProgram = BlockBuilder ?=> Unit
type Callback = compiler.Callback

// Events
def onStart(b: SRProgram): Callback = callback(b, FlightStart)
def onPartExplode(b: SRProgram): Callback = callback(b, PartExplode)

// Autopilot
extension (prop: Input) def :=(value: Expr): SRProgram = SetInput(prop, value).stage
extension (prop: Autopilot) def :=(value: Expr): SRProgram = SetTargetHeading(prop, value).stage
def activateStage(): SRProgram = ActivateStage.stage
def lockHeading(hdg: String): SRProgram = LockHeading(hdg).stage

// Control Flow
def displayText(text: Expr): SRProgram = DisplayText(text).stage
def waitUntil(expr: Expr): SRProgram = WaitUntil(expr).stage
def waitSeconds(expr: Expr): SRProgram = WaitSeconds(expr).stage
def ifTrue(condition: Expr)(body: SRProgram): SRProgram = If(condition, reify(body), Block(Nil)).stage
def ifElse(condition: Expr)(body: SRProgram)(elseBody: SRProgram): SRProgram = If(condition, reify(body), reify(body)).stage
