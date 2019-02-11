@echo off
title Emulador-Aidemu
java -version 1>nul 2>nul || (
   echo Error: Java no esta instalado o no se encuentra en la ruta especificada
   pause
   exit /b
)
java -jar -Xms256m -Xmx2g Game.jar
