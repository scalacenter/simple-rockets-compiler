//> using scala "3.1.1"
import $dep.`com.akmetiuk::simple-rockets-compiler:0.1.1`, rocketscompiler.{ *, given }

program("Simple Launch") {
  // Countdown
  for i <- 5 to 1 by -1 do
    displayText(s"T-$i")
    waitSeconds(1)
  displayText("LAUNCH")

  // Launch sequence
  activateStage() // Start the engine
  Throttle := 1  // Set engine power to 100%
}
