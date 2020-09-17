@echo off
javac -sourcepath src/main/java -d target -cp scr/server/command src/main/java/com/acme/edu/server/Server.java
java -cp target com.acme.edu.server.Server

