-- Datos base para el sistema (Diferentes a los usados en los tests BDD)

-- 1. Catálogos base (Tipo de prueba y Área)
INSERT INTO public.tipo_prueba (id_tipo_prueba, valor, activo)
VALUES ('10000000-0000-0000-0000-000000000001', 'Examen Psicométrico', true);

INSERT INTO public.tipo_prueba (id_tipo_prueba, valor, activo)
VALUES ('10000000-0000-0000-0000-000000000002', 'INGRESO_UNIVERSITARIO_PRIMERA_RONDA', true);


INSERT INTO public.area (id_area, id_area_padre, nombre, descripcion, activo)
VALUES ('20000000-0000-0000-0000-000000000001', NULL, 'Física', 'Área de ciencias físicas', true);

-- 2. Prueba y Claves
INSERT INTO public.prueba (id_prueba, nombre, indicaciones, id_tipo_prueba, puntaje_maximo, nota_aprobacion, duracion, fecha_creacion)
VALUES ('30000000-0000-0000-0000-000000000001', 'Prueba de Aptitud 2026', 'Sin calculadora', '10000000-0000-0000-0000-000000000001', 10.00, 6.00, 90, CURRENT_TIMESTAMP);

INSERT INTO public.prueba (id_prueba, nombre, indicaciones, id_tipo_prueba, puntaje_maximo, nota_aprobacion, duracion, fecha_creacion)
VALUES ('30000000-0000-0000-0000-000000000002', 'NUEVO_INGRESO_2026', 'Sin calculadora', '10000000-0000-0000-0000-000000000002', 100.00, 60.00, 120, CURRENT_TIMESTAMP);


INSERT INTO public.prueba_clave (id_prueba_clave, nombre_clave, id_prueba)
VALUES ('40000000-0000-0000-0000-000000000001', 'Clave Base', '30000000-0000-0000-0000-000000000001');


INSERT INTO public.prueba_clave (id_prueba_clave, nombre_clave, id_prueba)
VALUES ('40000000-0000-0000-0000-000000000002', 'PRIMERA_CLAVE', '30000000-0000-0000-0000-000000000002');


INSERT INTO public.prueba_clave_area (id_prueba_clave, id_area, cantidad, porcentaje)
VALUES ('40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', 1, 100.00);

INSERT INTO public.prueba_clave_area (id_prueba_clave, id_area, cantidad, porcentaje)
VALUES ('40000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', 10, 100.00);


-- 3. Banco de Preguntas y Distractores
INSERT INTO public.pregunta (id_pregunta, valor, activo, imagen_url)
VALUES ('50000000-0000-0000-0000-000000000001', '¿Fórmula de la fuerza?', true, NULL);

INSERT INTO public.pregunta_area (id_pregunta, id_area)
VALUES ('50000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001');

INSERT INTO public.distractor (id_distractor, valor, activo, imagen_url)
VALUES
    ('60000000-0000-0000-0000-000000000001', 'F = m * a', true, NULL),
    ('60000000-0000-0000-0000-000000000002', 'F = m / a', true, NULL);

INSERT INTO public.distractor_area (id_distractor, id_area)
VALUES
    ('60000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001');

INSERT INTO public.pregunta_distractor (id_pregunta, id_distractor, correcto)
VALUES
    ('50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000001', true),
    ('50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000002', false);

-- Armado de clave con pregunta y distractores
INSERT INTO public.prueba_clave_area_pregunta (id_prueba_clave, id_area, id_pregunta, porcentaje)
VALUES ('40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', 10.00);

INSERT INTO public.prueba_clave_area_pregunta_distractor (id_prueba_clave, id_area, id_pregunta, id_distractor)
VALUES
    ('40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000001'),
    ('40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000002');

INSERT INTO public.prueba_clave_area_pregunta (id_prueba_clave, id_area, id_pregunta, porcentaje)
VALUES ('40000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', 10.00);

INSERT INTO public.prueba_clave_area_pregunta_distractor (id_prueba_clave, id_area, id_pregunta, id_distractor)
VALUES
   ('40000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000001'),
    ('40000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', '50000000-0000-0000-0000-000000000001', '60000000-0000-0000-0000-000000000002');


-- 4. Logística (Jornada y Aula)
INSERT INTO public.jornada (id_jornada, nombre, fecha_inicio, fecha_fin)
VALUES ('70000000-0000-0000-0000-000000000001', 'Jornada Matutina Base', '2026-11-01 07:00:00+00', '2026-11-01 11:00:00+00');

INSERT INTO public.jornada_aula (id_jornada_aula, id_jornada, id_aula)
VALUES ('80000000-0000-0000-0000-000000000001', '70000000-0000-0000-0000-000000000001', 'Aula 202');

INSERT INTO public.prueba_jornada (id_prueba, id_jornada)
VALUES ('30000000-0000-0000-0000-000000000001', '70000000-0000-0000-0000-000000000001');

INSERT INTO public.prueba_jornada (id_prueba, id_jornada)
VALUES ('30000000-0000-0000-0000-000000000002', '70000000-0000-0000-0000-000000000001');


-- 5. Aspirante, Asignación y Examen
INSERT INTO public.aspirante (id_aspirante, nombres, apellidos, fecha_nacimiento, documento_identidad, correo, fecha_creacion)
VALUES ('90000000-0000-0000-0000-000000000001', 'Carlos', 'López', '2004-05-10', '98765432-1', 'carlos.lopez@email.com', CURRENT_TIMESTAMP);

INSERT INTO public.aspirante (id_aspirante, nombres, apellidos, fecha_nacimiento, documento_identidad, correo, fecha_creacion)
VALUES ('90000000-0000-0000-0000-000000000002', 'Maria', 'Lopez', '2004-05-11', '98765422-2', 'maria.lopez@email.com', CURRENT_TIMESTAMP);


INSERT INTO public.aspirante_opcion (id_aspirante_opcion, id_aspirante, id_opcion, prioridad, fecha_creacion)
VALUES ('A0000000-0000-0000-0000-000000000001', '90000000-0000-0000-0000-000000000001', 'E90909', 1, CURRENT_TIMESTAMP);

INSERT INTO public.aspirante_opcion (id_aspirante_opcion, id_aspirante, id_opcion, prioridad, fecha_creacion)
VALUES ('A0000000-0000-0000-0000-000000000002', '90000000-0000-0000-0000-000000000002', 'E90909', 1, CURRENT_TIMESTAMP);


INSERT INTO public.prueba_clave (id_prueba_clave, nombre_clave, id_prueba)
VALUES ('50000000-0000-0000-0000-000000000001', 'Clave BDD Cascada', '30000000-0000-0000-0000-000000000001');



-- 7. Área Nueva (Física ya existe, solo agregamos Matemática)
INSERT INTO public.area (id_area, id_area_padre, nombre, descripcion, activo)
VALUES ('20000000-0000-0000-0000-000000000002', NULL, 'Matemática', 'Área de ciencias exactas', true);

-- 8. Banco de Preguntas adicionales (IDs 002 al 005 para no chocar con la base)
INSERT INTO public.pregunta (id_pregunta, valor, activo, imagen_url)
VALUES
    ('50000000-0000-0000-0000-000000000002', '¿Qué es la inercia?', true, NULL),
    ('50000000-0000-0000-0000-000000000003', '¿Cuál es la constante de la gravedad?', true, NULL),
    ('50000000-0000-0000-0000-000000000004', '¿Cuál es la derivada de x^2?', true, NULL),
    ('50000000-0000-0000-0000-000000000005', '¿Qué es un número primo?', true, NULL);

-- Enlace de preguntas adicionales con sus áreas (Física: ID ...001 | Matemática: ID ...002)
INSERT INTO public.pregunta_area (id_pregunta, id_area)
VALUES
    ('50000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001'),
    ('50000000-0000-0000-0000-000000000003', '20000000-0000-0000-0000-000000000001'),
    ('50000000-0000-0000-0000-000000000004', '20000000-0000-0000-0000-000000000002'),
    ('50000000-0000-0000-0000-000000000005', '20000000-0000-0000-0000-000000000002');

-- 9. Banco de Distractores adicionales (IDs 003 al 009 para no chocar)
INSERT INTO public.distractor (id_distractor, valor, activo, imagen_url)
VALUES
    ('60000000-0000-0000-0000-000000000003', 'Resistencia al cambio de estado', true, NULL),
    ('60000000-0000-0000-0000-000000000004', 'Una fuerza magnética', true, NULL),
    ('60000000-0000-0000-0000-000000000005', 'Ninguna de las anteriores', true, NULL),
    ('60000000-0000-0000-0000-000000000006', '9.8 m/s^2', true, NULL),
    ('60000000-0000-0000-0000-000000000007', '3.1416', true, NULL),
    ('60000000-0000-0000-0000-000000000008', '2x', true, NULL),
    ('60000000-0000-0000-0000-000000000009', 'x', true, NULL);

-- Enlace de los distractores con sus áreas correspondientes
INSERT INTO public.distractor_area (id_distractor, id_area)
VALUES
    ('60000000-0000-0000-0000-000000000003', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000004', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000005', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000006', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000007', '20000000-0000-0000-0000-000000000001'),
    ('60000000-0000-0000-0000-000000000008', '20000000-0000-0000-0000-000000000002'),
    ('60000000-0000-0000-0000-000000000009', '20000000-0000-0000-0000-000000000002');

-- Configuración final de distractores por pregunta
INSERT INTO public.pregunta_distractor (id_pregunta, id_distractor, correcto)
VALUES
    ('50000000-0000-0000-0000-000000000002', '60000000-0000-0000-0000-000000000003', true),
    ('50000000-0000-0000-0000-000000000002', '60000000-0000-0000-0000-000000000004', false),
    ('50000000-0000-0000-0000-000000000002', '60000000-0000-0000-0000-000000000005', false),
    ('50000000-0000-0000-0000-000000000003', '60000000-0000-0000-0000-000000000006', true),
    ('50000000-0000-0000-0000-000000000003', '60000000-0000-0000-0000-000000000007', false),
    ('50000000-0000-0000-0000-000000000004', '60000000-0000-0000-0000-000000000008', true),
    ('50000000-0000-0000-0000-000000000004', '60000000-0000-0000-0000-000000000009', false);

-- ================================================================================
-- 10. Datos de Maria para la prueba de INGRESO_UNIVERSITARIO_PRIMERA_RONDA
-- ================================================================================
-- Asignación de María a la jornada y aula
INSERT INTO public.prueba_jornada_aula_aspirante_opcion (id_prueba, id_jornada, id_aula, id_aspirante_opcion, fecha, activo)
VALUES ('30000000-0000-0000-0000-000000000002', '70000000-0000-0000-0000-000000000001', 'Aula 202', 'A0000000-0000-0000-0000-000000000002', CURRENT_TIMESTAMP, true);

-- Resultado del examen de María
INSERT INTO public.prueba_jornada_aula_aspirante_opcion_examen (id_prueba, id_jornada, id_aula, id_aspirante_opcion, id_prueba_clave, resultado, fecha_resultado)
VALUES ('30000000-0000-0000-0000-000000000002', '70000000-0000-0000-0000-000000000001', 'Aula 202', 'A0000000-0000-0000-0000-000000000002', '40000000-0000-0000-0000-000000000002', 45.00, CURRENT_TIMESTAMP);
