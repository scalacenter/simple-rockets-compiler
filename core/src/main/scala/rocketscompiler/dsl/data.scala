package rocketscompiler
package dsl

import rocketscompiler.compiler.*


// Inputs
val Throttle = Input("throttle")

// Autopilot
val Pitch = Autopilot("pitch")
val Heading = Autopilot("heading")

// Heading
val Retrograde = "Retrograde"

object Fuel:
  val FuelInStage = CraftProperty("Fuel.FuelInStage", "prop-fuel")

object Altitude:
  val ASL = CraftProperty("Altitude.ASL", "prop-altitude")
  val AGL = CraftProperty("Altitude.AGL", "prop-altitude")
