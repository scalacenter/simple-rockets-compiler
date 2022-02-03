package rocketscompiler
package dsl

import rocketscompiler.compiler.*


// Inputs
val Throttle = CraftProperty("Input.Throttle", "prop-input", "throttle")

// Autopilot
val Pitch = CraftProperty("Heading.Pitch", "prop-heading", "pitch")  // TODO UNTESTED in output position
val Heading = CraftProperty("Heading.Heading", "prop-heading", "heading")  // TODO UNTESTED in output position

// Heading
val Retrograde = "Retrograde"

// Craft & Environment Information
object Fuel:
  val InStage = CraftProperty("Fuel.FuelInStage", "prop-fuel")

object Altitude:
  val ASL = CraftProperty("Altitude.ASL", "prop-altitude")
  val AGL = CraftProperty("Altitude.AGL", "prop-altitude")

object Orbit:
  val Apoapsis = CraftProperty("Orbit.Apoapsis", "prop-orbit")
  val Periapsis = CraftProperty("Orbit.Periapsis", "prop-orbit")
  val TimeToApoapsis = CraftProperty("Orbit.TimeToApoapsis", "prop-orbit")

object Performance:
  val StageDeltaV = CraftProperty("Performance.StageDeltaV", "prop-performance")
  val BurnTime = CraftProperty("Performance.BurnTime", "prop-performance")

object Velocity:
  val OrbitVelocity = CraftProperty("Vel.OrbitVelocity", "prop-velocity")
