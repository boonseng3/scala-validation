name := "Scala validation"

version := "0.1"

scalaVersion := "2.11.4"

libraryDependencies ++= {
  Seq(
  	 "org.scalaz" %% "scalaz-core" % "7.1.0",
  	 "org.springframework" % "spring-context" % "4.1.4.RELEASE",
  	 "javax.validation" % "validation-api" % "1.1.0.Final",
  	 "org.hibernate" % "hibernate-validator" % "5.1.3.Final",
  	 "javax.el" % "javax.el-api" % "2.2.4",
  	 "com.wix" %% "accord-core" % "0.4.1"
  )  
}

EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.Unmanaged, EclipseCreateSrc.Source, EclipseCreateSrc.Resource)

EclipseKeys.withSource := true
