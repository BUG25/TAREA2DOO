@echo off
cd /d "D:\Archivos\Becoming Batman\Universidad\2026-1\DOO\TAREA 2\tarea2"

echo ========================================
echo  Compilando y ejecutando tests
echo ========================================
echo.

echo [1/4] Creando directorios...
if not exist target\classes mkdir target\classes
if not exist target\test-classes mkdir target\test-classes
echo OK
echo.

echo [2/4] Compilando codigo principal...
dir /s /B src\*.java > sources.txt
javac -d target\classes @"sources.txt"
if errorlevel 1 (
    echo ERROR: Fallo en compilacion
    type sources.txt
    pause
    exit /b 1
)
del sources.txt
echo OK
echo.

echo [3/4] Compilando tests...
javac -cp "target\classes;junit-platform-console-standalone.jar" -d target\test-classes tests\*.java
if errorlevel 1 (
    echo ERROR: Fallo en compilacion de tests
    pause
    exit /b 1
)
echo OK
echo.

echo [4/4] Ejecutando tests...
java -jar junit-platform-console-standalone.jar --class-path "target\classes;target\test-classes" --scan-class-path
echo.

pause