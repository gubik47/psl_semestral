name := "psl"

version := "1.0"

lazy val `psl` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws, "mysql" % "mysql-connector-java" % "5.1.18" )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  