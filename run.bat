::set CODEPATH=.\target\classes\edu\richmond\mathcs\htmlunit
::set CODEPATH = %CODEPATH%test1
::set CLASSPATH=.;.\target\classes\;.\artifacts\htmlunit-2.16-SNAPSHOT-with-dependencies\htmlunit-2.16-SNAPSHOT\lib\*.jar
::set CLASSPATH=%CLASSPATH%;.\target\htmlunit-2.16-SNAPSHOT.jar

::java -cp .;%CLASSPATH% edu.richmond.mathcs.htmlunit.test1  


java -cp .;target\classes\;\artifacts\htmlunit-2.16-SNAPSHOT\lib\*.jar;target\htmlunit-2.16-SNAPSHOT.jar;artifacts\testing\htmlunit-2.16-SNAPSHOT\lib\*;artifacts\htmlunit-2.16-SNAPSHOT\lib\* edu.richmond.mathcs.htmlunit.OmarTest


