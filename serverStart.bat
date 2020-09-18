@echo off
javac -sourcepath src/main/java -d target  src/main/java/com/acme/edu/server/Server.java
java -jar target/Client.jar

