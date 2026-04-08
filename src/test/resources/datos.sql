-- Datos base para el sistema (Diferentes a los usados en los tests BDD)

-- 1. Catálogos base (Tipo de prueba y Área)
INSERT INTO public.tipo_prueba (id_tipo_prueba, valor, activo)
VALUES ('10000000-0000-0000-0000-000000000001', 'Examen Psicométrico', true);

INSERT INTO public.area (id_area, id_area_padre, nombre, descripcion, activo)
VALUES ('20000000-0000-0000-0000-000000000001', NULL, 'Física', 'Área de ciencias físicas', true);

-- 2. Prueba y Claves
INSERT INTO public.prueba (id_prueba, nombre, indicaciones, id_tipo_prueba, puntaje_maximo, nota_aprobacion, duracion, fecha_creacion)
VALUES ('30000000-0000-0000-0000-000000000001', 'Prueba de Aptitud 2026', 'Sin calculadora', '10000000-0000-0000-0000-000000000001', 10.00, 6.00, 90, CURRENT_TIMESTAMP);

INSERT INTO public.prueba_clave (id_prueba_clave, nombre_clave, id_prueba)
VALUES ('40000000-0000-0000-0000-000000000001', 'Clave Base', '30000000-0000-0000-0000-000000000001');

INSERT INTO public.prueba_clave_area (id_prueba_clave, id_area, cantidad, porcentaje)
VALUES ('40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', 1, 100.00);

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

-- 4. Logística (Jornada y Aula)
INSERT INTO public.jornada (id_jornada, nombre, fecha_inicio, fecha_fin)
VALUES ('70000000-0000-0000-0000-000000000001', 'Jornada Matutina Base', '2026-11-01 07:00:00+00', '2026-11-01 11:00:00+00');

INSERT INTO public.jornada_aula (id_jornada_aula, id_jornada, id_aula)
VALUES ('80000000-0000-0000-0000-000000000001', '70000000-0000-0000-0000-000000000001', 'Aula 202');

INSERT INTO public.prueba_jornada (id_prueba, id_jornada)
VALUES ('30000000-0000-0000-0000-000000000001', '70000000-0000-0000-0000-000000000001');

-- 5. Aspirante, Asignación y Examen
INSERT INTO public.aspirante (id_aspirante, nombres, apellidos, fecha_nacimiento, documento_identidad, correo, fecha_creacion)
VALUES ('90000000-0000-0000-0000-000000000001', 'Carlos', 'López', '2004-05-10', '98765432-1', 'carlos.lopez@email.com', CURRENT_TIMESTAMP);

INSERT INTO public.aspirante_opcion (id_aspirante_opcion, id_aspirante, id_opcion, prioridad, fecha_creacion)
VALUES ('A0000000-0000-0000-0000-000000000001', '90000000-0000-0000-0000-000000000001', 'E90909', 1, CURRENT_TIMESTAMP);
