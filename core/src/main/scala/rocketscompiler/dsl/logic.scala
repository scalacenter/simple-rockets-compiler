package rocketscompiler
package dsl

import rocketscompiler.compiler.*


// Events
def onStart(b: StagedBlock): Block = block(b, FlightStart)
def onPartExplode(b: StagedBlock): Block = block(b, PartExplode)

// Autopilot
extension (prop: Input) def :=(value: Expr)(using BlockBuilder) = SetInput(prop, value).push
extension (prop: Autopilot) def :=(value: Expr)(using BlockBuilder) = SetTargetHeading(prop, value).push
def activateStage()(using BlockBuilder) = ActivateStage.push
def lockHeading(hdg: String)(using BlockBuilder) = LockHeading(hdg).push

// Control Flow
def displayText(text: Expr)(using BlockBuilder) = DisplayText(text).push
def waitUntil(expr: Expr)(using BlockBuilder) = WaitUntil(expr).push
def waitSeconds(expr: Expr)(using BlockBuilder) = WaitSeconds(expr).push
def ifTrue(condition: Expr)(body: BlockBuilder ?=> Unit)(using BlockBuilder) = If(condition, block(body), Block(null, Nil)).push
def ifElse(condition: Expr)(body: BlockBuilder ?=> Unit)(elseBody: BlockBuilder ?=> Unit)(using BlockBuilder) = If(condition, block(body), block(elseBody)).push

