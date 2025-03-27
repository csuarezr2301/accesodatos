/*

DELETE
int numeroCochesBorrados = em.createQuery(
		"DELETE Coche c WHERE c.precio > 10000")
		.executeUpdate();
System.out.println("Número de registros afectados: "
		+ numeroCochesBorrados);


UPDATE
int numeroCochesActualizados = em.createQuery(
		"UPDATE Coche c SET c.precio = c.precio * 1.1")
		.executeUpdate();
System.out.println("Número de registros afectados: "
		+ numeroCochesActualizados);


Funciones en consultas JPQL
Tipo	Función
De fecha/hora actual---current_date(), current_time(), current_timestamp()
    SELECT g FROM Grupo g WHERE g.fechaCreacion = current_date
    SELECT v FROM Voto v WHERE v.fechaHoraVoto = current_timestamp
De extracción de fecha---	day(fecha), month(fecha), year(fecha)
    SELECT c FROM Cancion c WHERE year(c.fechaCreacion) = 2023
    SELECT c FROM Cancion c WHERE month(c.fechaLanzamiento) = 12
    SELECT u FROM Usuario u WHERE day(u.fechaNacimiento) = 15
De extracción de tiempo	---hour(time), minute(time), second(time)
    SELECT v FROM Voto v WHERE hour(v.horaVoto) = 0 -----Usa hour()para extraer la hora de un campo de tiempo y filtrar los votos realizados entre las 00:00 y las 00:59.
    SELECT c FROM Cancion c WHERE minute(c.horaCreacion) = 30 ---- Usa minute()para extraer el minuto y filtrar canciones agregadas en el minuto 30.
De String---	substring(), trim(), lower(), upper(), length(), locate(), concat()
    SELECT c FROM Cancion c WHERE substring(c.titulo, 1, 1) = 'A'----Usa substring()para extraer el primer carácter del título y lo compara con "A".
    SELECT lower(g.nombre) FROM Grupo g--Usa lower()para convertir los nombres de los grupos a minúsculas.
    SELECT u FROM Usuario u WHERE locate('admin', u.nombre) > 0
Conversión a string---	str(valor)
    SELECT concat(g.nombre, ' - ', str(year(g.fechaCreacion))) FROM Grupo g-----Usa concat()para unir el nombre del grupo con el año de creación convertido a texto con str().
De agregación o resumen---	count(..), avg(..), sum(..), max(..), min(..)
    SELECT count(v) FROM Voto v------Usa count()para obtener el número total de votos en la tabla.
    SELECT avg(c.duracion) FROM Cancion c
    SELECT max(c.totalVotos) FROM Cancion c------Usa max()para encontrar el mayor número de votos entre todas las canciones.
Sobre colecciones--	Size(coleccion), index(colección)
    SELECT g FROM Grupo g WHERE size(g.canciones) > 5----------Usa size()para contar el número de canciones en la colección cancionesde cada grupo y filtrar aquellos con más de 5.
    SELECT index(c) FROM Grupo g JOIN g.canciones c WHERE g.nombre = 'Rockers'-------Usa index()para obtener la posición de cada canción en la colección de canciones de un grupo llamado "Rockers".


SELECT c FROM Coche c ORDER BY c.precio DESC
"SELECT u FROM Usuario u WHERE u.fechanac >= :fechaInicio ORDER BY u.nombre"



 */