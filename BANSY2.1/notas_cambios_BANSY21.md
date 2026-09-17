# Informe de cambios y correcciones - BANSY 2.1

**Fecha:** 17 de septiembre de 2026  
**Proyecto:** BANSY 2.1 - Redes bayesianas

## Resumen

Se corrigieron errores de carga de bases, ejecucion de Bayes 9, tablas de probabilidades, Inferencia Naive y visualizacion de ventanas.

## Cambios

- `Archivo.java` detecta comas o tabuladores y valida el numero de columnas.
- Las filas invalidas se omiten y se informa al usuario. En UNAM 2006-2 se encontro una fila con dos registros pegados.
- `Bayes9.java` usa rutas absolutas, elimina salidas anteriores y tiene limite de 120 segundos.
- Bayes 9 acepta una salida valida aunque el ejecutable devuelva codigo 44.
- `Archivo2.java` interpreta correctamente los arcos `--` y `->` y evita bucles.
- Se corrigieron los layouts y tamanos de las ventanas.
- Inferencia Naive ahora muestra el resultado en una ventana.
- La tabla general de probabilidades ya no usa una red nula.

## Pruebas

- Compilacion final con `javac`: correcta.
- `Asia.txt`: 8 columnas, 1000 registros.
- `Base UNAM 2006-2.txt`: 28 columnas, 143508 registros validos y 1 registro omitido.
- `Base UNAM 2006-3.txt`: 28 columnas, 76150 registros.
- Bayes 9 genero correctamente los arcos de la red.

## Ejecucion

```powershell
cd "C:\Users\emanu\Downloads\BANSY2.1\BANSY2.1"
java -cp "build\classes;dist\BANSY2.1.jar" bansy21.frmPrincipal
```

