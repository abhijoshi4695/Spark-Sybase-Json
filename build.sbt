name := "Spark-Sybase-Json"

version := "0.1"

scalaVersion := "2.11.12"

//resolvers += "Amazon redshift Maven2 Repository" at "http://redshift-maven-repository.s3-website-us-east-1.amazonaws.com/release"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

libraryDependencies += "org.zalando" %% "spark-json-schema" % "0.6.1"

//libraryDependencies += "com.databricks" %% "spark-redshift" % "3.0.0-preview1"
// https://mvnrepository.com/artifact/com.amazon.redshift/redshift-jdbc42
//libraryDependencies += "com.amazon.redshift" % "redshift-jdbc42" % "1.2.18.1036"
// https://mvnrepository.com/artifact/io.minio/minio
//libraryDependencies += "io.minio" % "minio" % "5.0.2"

// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk
//libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.7.4"
// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3
//libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.454"
// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-aws
//libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.3"

// https://mvnrepository.com/artifact/javax.json/javax.json-api
libraryDependencies += "javax.json" % "javax.json-api" % "1.1.4"
// https://mvnrepository.com/artifact/org.glassfish/javax.json
libraryDependencies += "org.glassfish" % "javax.json" % "1.1.4"

libraryDependencies += "org.apache.commons" % "commons-compress" % "1.11"
