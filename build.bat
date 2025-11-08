@echo off
echo Building Maven projects...
echo.

echo Building gui_entrega2 (JAR)...
cd gui_entrega2
call mvn clean install
if %errorlevel% neq 0 (
    echo Error building gui_entrega2
    pause
    exit /b 1
)
echo gui_entrega2 build completed successfully
echo.

echo Building tarea2 (WAR)...
cd ..\tarea2
call mvn clean install
if %errorlevel% neq 0 (
    echo Error building tarea2
    pause
    exit /b 1
)
echo tarea2 build completed successfully
echo.

echo Build completed! 
echo JAR file: gui_entrega2\target\ServidorWS-0.0.1-SNAPSHOT-jar-with-dependencies.jar
echo WAR file: tarea2\target\tarea2-0.0.1-SNAPSHOT.war
pause