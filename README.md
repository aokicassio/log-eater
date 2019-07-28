
This project is a Spring Batch application with Spring Boot example.

Program basically reads a NDJson file from source folder and persists data to an in-memory HSQLDB table.


Running with Gradle on command line

WINDOWS

1) Go to checkout directory.
2) Run command below. Make sure you are pointing to correct file.
gradlew bootRun -Pargs=--file.sourcefolder=C:/dev/log.txt

Note: if you do not provide param file.sourcefolder app will run using in-project file sample:
gradlew bootRun 

LINUX

1) Go to checkout directory.
2) Run command below. Make sure you are pointing to correct file.
./gradlew bootRun -Pargs=--file.sourcefolder=/c/dev/log.txt



