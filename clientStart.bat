@echo off
javac -sourcepath src/main/java -d target  src/main/java/com/acme/edu/client/Client.java
java -cp target com.acme.edu.client.Client

