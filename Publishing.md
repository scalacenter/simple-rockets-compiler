# Steps to publish to Maven
1. `sbt`
2. `project core`
3. `publishSigned`, enter password for GPG
4. [Sonatype](https://oss.sonatype.org) (make sure to log in with username NOT email) -> Staging Repositories
5. Close -> Release -> Drop staged  repo

## Resources
https://www.scala-sbt.org/1.x/docs/Publishing.html
https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html
https://central.sonatype.org/publish/release/
