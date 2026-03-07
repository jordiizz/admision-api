package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.util.UUID;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, Object> {

    @Override
    public Object convertToDatabaseColumn(UUID attribute) {
        try {
            Class<?> pgObjectClass = Class.forName("org.postgresql.util.PGobject");
            Object pgObject = pgObjectClass.getDeclaredConstructor().newInstance();
            pgObjectClass.getMethod("setType", String.class).invoke(pgObject, "uuid");
            pgObjectClass.getMethod("setValue", String.class).invoke(pgObject,
                    attribute != null ? attribute.toString() : null);
            return pgObject;
        } catch (Exception e) {
            return attribute != null ? attribute.toString() : null;
        }
    }

    @Override
    public UUID convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        if (dbData instanceof UUID u) return u;
        return UUID.fromString(dbData.toString());
    }
}
