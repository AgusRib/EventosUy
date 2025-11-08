#!/bin/bash

echo "Building Maven projects..."
echo

echo "Building gui_entrega2 (JAR)..."
cd gui_entrega2
mvn clean install
if [ $? -ne 0 ]; then
    echo "Error building gui_entrega2"
    exit 1
fi
echo "gui_entrega2 build completed successfully"
echo

echo "Building tarea2 (WAR)..."
cd ../tarea2
mvn clean install
if [ $? -ne 0 ]; then
    echo "Error building tarea2"
    exit 1
fi
echo "tarea2 build completed successfully"
echo

echo "Building dispositivoMobile (WAR)..."
cd ../mobile
mvn clean install
if [ $? -ne 0 ]; then
    echo "Error building mobile"
    exit 1
fi
echo "mobile build completed successfully"
echo

echo "Build completed!"
echo "JAR file: gui_entrega2/target/ServidorWS-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
echo "WAR file: tarea2/target/tarea2-0.0.1-SNAPSHOT.war"
echo "WAR file: tarea2/target/movil-0.0.1-SNAPSHOT.war"