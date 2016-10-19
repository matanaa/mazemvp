@echo off
echo Detecting OS processor type

if "%PROCESSOR_ARCHITECTURE%"=="AMD64" goto 64BIT
echo 32-bit OS

goto END
:64BIT
echo 64-bit OS
java -classpath "guy.jar;lib\MyAlgorithms.jar;lib\swt.jar" boot.Run
:END
echo finish                      
pause