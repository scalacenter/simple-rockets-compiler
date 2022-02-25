package rocketscompiler

import rocketscompiler.compiler.*

/**
 * You can control the spacecraft by assigning variables of this type with the `:=` operator.
 * For example, `Thrust := 1` sets the engine to full power.
 * @group Types */
opaque type AssignableCraftProperty <: Expr = CraftProperty

// Inputs
/**
 * Engine power, a Double from 0 to 1.
 * @group Inputs */
val Throttle: AssignableCraftProperty = CraftProperty("Input.Throttle", "prop-input", "throttle")

// Autopilot
/**
 * Pitch in degrees, 90 means straight up.
 * @group Inputs */
val Pitch: AssignableCraftProperty = CraftProperty("Heading.Pitch", "prop-heading", "pitch")  // TODO UNTESTED in output position
/**
 * Heading in degrees, 0 means North, 90 – East etc.
 *
 * @group Inputs */
val Heading: AssignableCraftProperty = CraftProperty("Heading.Heading", "prop-heading", "heading")  // TODO UNTESTED in output position

val Roll: AssignableCraftProperty = CraftProperty("roll")
val Pitch: AssignableCraftProperty = CraftProperty("pitch")
val Yaw: AssignableCraftProperty = CraftProperty("yaw")
val Brake: AssignableCraftProperty = CraftProperty("brake")
val Slider1: AssignableCraftProperty = CraftProperty("slider1")
val Slider2: AssignableCraftProperty = CraftProperty("slider2")
val Slider3: AssignableCraftProperty = CraftProperty("slider3")
val Slider4: AssignableCraftProperty = CraftProperty("slider4")
val TranslateForward: AssignableCraftProperty = CraftProperty("translateForward")
val TranslateRight: AssignableCraftProperty = CraftProperty("translateRight")
val TranslateUp: AssignableCraftProperty = CraftProperty("translateUp")
val TranslationMode: AssignableCraftProperty = CraftProperty("translationMode")


// Heading
/** @group Constants */
val Retrograde = "Retrograde"
/** @group Constants */
val Prograde = "Prograde"

// Craft & Environment Information
/** @group CraftInfo */
object Fuel:
  /** Fuel left in the current stage, from 0 to 1. */
  val InStage = CraftProperty("Fuel.FuelInStage", "prop-fuel")

/** @group CraftInfo */
object Altitude:
  /** Above sea level. */
  val ASL = CraftProperty("Altitude.ASL", "prop-altitude")
  /** Above ground level. */
  val AGL = CraftProperty("Altitude.AGL", "prop-altitude")

/** @group CraftInfo */
object Orbit:
  /** The highest point of the spacecraft's trajectory above the planet. */
  val Apoapsis = CraftProperty("Orbit.Apoapsis", "prop-orbit")
  /** The lowest point of the spacecraft's trajectory above the planet. */
  val Periapsis = CraftProperty("Orbit.Periapsis", "prop-orbit")
  val TimeToApoapsis = CraftProperty("Orbit.TimeToApoapsis", "prop-orbit")
  val TimeToPeriapsis = CraftProperty("Orbit.TimeToPeriapsis", "prop-orbit")

/** @group CraftInfo */
object Performance:
  /** The maximum change of speed the current stage is able to cause to this spacecraft. */
  val StageDeltaV = CraftProperty("Performance.StageDeltaV", "prop-performance")
  /** In seconds, how long this stage will take to exhaust all the fuel. */
  val BurnTime = CraftProperty("Performance.BurnTime", "prop-performance")
  val Mass = CraftProperty("Performance.Mass", "prop-performance")
  /** Spacecraft mass without fuel. */
  val DryMass = CraftProperty("Performance.DryMass", "prop-performance")
  val FuelMass = CraftProperty("Performance.FuelMass", "prop-performance")
  /** Max thrust in Newtons. */
  val MaxActiveEngineThrust = CraftProperty("Performance.MaxActiveEngineThrust", "prop-performance")
  /**
   * Thrust to weight ratio of the rocket – by what factor the engine is more powerful than the rocket is heavy.
   * Weight of the rocket is its mass times the gravitational constant, 9.8.
   * If TWR is greater than 1, the rocket can lift off. If it is equal to 1, the rocket can hover in place
   * without going up or down. If it is less than one, the rocket will fall down.
   */
  val TWR = CraftProperty("Performance.TWR", "prop-performance")

/** @group CraftInfo */
object Velocity:
  /** Orbital velocity doesn't take into account the planet rotation. */
  val Orbit = CraftProperty("Vel.OrbitVelocity", "prop-velocity")
  /** Orbital velocity plus (or minus, depending which direction you are going) the speed of the planet rotation. */
  val Surface = CraftProperty("Vel.SurfaceVelocity", "prop-velocity")
