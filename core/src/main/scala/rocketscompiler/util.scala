package rocketscompiler

import java.io.File


private[rocketscompiler] def flightProgramsFolder: File =
  sys.props("os.name") match
    case "Mac OS X" =>
      val homedir = sys.props("user.home")
      File(homedir, "Library/Application Support/com.jundroo.SimpleRockets2/UserData/FlightPrograms/")
    case _ =>
      val appdataFolder = sys.env("APPDATA")
      File(appdataFolder, """..\LocalLow\Jundroo\SimpleRockets 2\UserData\FlightPrograms""")
