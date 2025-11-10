#!/bin/bash

# Configurar Java 21 para Maven
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$JAVA_HOME/bin:$PATH

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

# Copiar datosPrueba desde gui_entrega2 a la carpeta target
echo "Copying datosPrueba to target folder..."
cp -r ./datosPrueba ./target/
if [ $? -ne 0 ]; then
    echo "Error copying datosPrueba"
    exit 1
fi
echo "datosPrueba copied successfully to target folder."
echo

# Ejecutar el archivo JAR generado
echo "Running the generated JAR..."
JAR_PATH="./target/ServidorWS-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
if [ -f "$JAR_PATH" ]; then
    java -jar "$JAR_PATH"
else
    echo "JAR file not found: $JAR_PATH"
    exit 1
fi
echo "JAR executed successfully."
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
