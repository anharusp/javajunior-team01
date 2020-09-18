@echo off
:: run from command line like: serverStart.bat 'path to jar'
java -cp %1;C:/Users/User/.m2/repository/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar com.acme.edu.server.Server
