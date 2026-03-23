Tarea integradora
Directorio de Pacientes 

Este proyecto consiste en una aplicación de escritorio desarrollada en Java utilizando JavaFX. Su propósito es apoyar a un consultorio médico en la administración de un directorio de pacientes, permitiendo registrar, consultar, actualizar y gestionar su información básica.

Objetivo

Desarrollar una aplicación funcional que permita gestionar pacientes de forma sencilla, aplicando conceptos de Programación Orientada a Objetos, manejo de archivos y construcción de interfaces gráficas con JavaFX.

Tecnologías utilizadas
 Java
JavaFX (FXML y Controllers)
Programación Orientada a Objetos
Archivos CSV para almacenamiento de datos

Funcionalidades principales

 CRUD de pacientes

 Alta de pacientes mediante formulario
Consulta de pacientes en una tabla
 Edición de información existente
Eliminación lógica (cambio de estatus a inactivo)

 Los datos se almacenan en un archivo CSV
Al iniciar la aplicación se cargan automáticamente
Cada cambio se guarda en el archivo

Validaciones implementadas

No se permiten campos vacíos
El nombre debe tener al menos 5 caracteres
La edad debe estar en un rango de 0 a 120
 El teléfono debe contener solo números y mínimo 10 dígitos
 No se permiten pacientes con CURP duplicada

 Funcionalidades adicionales
Manejo de estatus del paciente (activo/inactivo)
Resumen en pantalla con:

Total de pacientes
   Pacientes activos
Pacientes inactivos

 Interfaz de usuario
 Pantalla principal

Incluye una tabla donde se muestran los pacientes registrados, junto con botones para realizar las operaciones principales (nuevo, editar, cambiar estatus y eliminar). También se muestra un resumen con el conteo de pacientes.
Formulario

Permite capturar y editar la información de un paciente:

 CURP
Nombre
Edad
Teléfono
Alergias




