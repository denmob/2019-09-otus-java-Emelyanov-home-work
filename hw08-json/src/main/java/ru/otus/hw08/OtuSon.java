package ru.otus.hw08;

import org.slf4j.LoggerFactory;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;
import javax.json.*;

class OtuSon {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OtuSon.class);

        String toJson(Object object) {
            if (object == null) {
                logger.error("Object is null!");
                return JsonValue.NULL.toString();
            }
            return toJsonValue(object).toString();
        }

        private JsonValue toJsonValue(Object object) {
            Class<?> clazz = object.getClass();

            if (Byte.class.equals(clazz)) {
                return Json.createValue((Byte)object);
            } else if (Short.class.equals(clazz)) {
                return Json.createValue((Short)object);
            } else if (Integer.class.equals(clazz)) {
                return Json.createValue((Integer)object);
            } else if (Long.class.equals(clazz)) {
                return Json.createValue((Long)object);
            } else if (Float.class.equals(clazz)) {
                return Json.createValue((Float)object);
            } else if (Double.class.equals(clazz)) {
                return Json.createValue((Double)object);
            } else if (Boolean.class.equals(clazz)) {
                return (Boolean)object ? JsonValue.TRUE : JsonValue.FALSE;
            } else if (clazz.equals(String.class)) {
                return Json.createValue(object.toString());
            } else if (Character.class.equals(clazz)) {
                return Json.createValue(object.toString());
            } else if (clazz.isArray()) {
                return arrayObjToJsonValue(object); }
            else if (isCollection(clazz)) {
                return collectionToJsonValue((Collection)object);
            } else
               return objectToJson(object);
        }

    private JsonValue objectToJson(Object object) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    int modifiers = field.getModifiers();
                    logger.debug(" fieldName {} modifier {}",field.getName(), modifiers);
                    if (isNotStaticOrTransient(modifiers)) {
                        JsonValue jsonValue = toJsonValue(field.get(object));
                        jsonObjectBuilder.add(field.getName(), jsonValue);
                    }
                } catch (Exception e) {
                   logger.error("Exception with convert field name: {} "
                            + " to JsonValue. Exception message: {} ", field.getName(),  e.getMessage());
                }
            }
        return jsonObjectBuilder.build();
    }

    private boolean isNotStaticOrTransient(int modifiers) {
        return !(Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers));
    }

    private JsonValue arrayObjToJsonValue(Object arrayObj) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (Array.getLength(arrayObj) == 0) { return arrayBuilder.build(); }
        Class<?> componentType = arrayObj.getClass().getComponentType();
        if (componentType.isPrimitive()) {
            IntStream.rangeClosed(0, Array.getLength(arrayObj) - 1)
                    .mapToObj(i -> toJsonValue(Array.get(arrayObj, i)))
                    .forEach(arrayBuilder::add);
        } else
            for (Object el : (Object[])arrayObj) arrayBuilder.add(toJsonValue(el));

        return arrayBuilder.build();
    }

    private boolean isCollection(Class<?> type) {
        return  Collection.class.isAssignableFrom(type);
    }

    private JsonValue collectionToJsonValue(Collection collection) {

        if (collection.isEmpty()) { return Json.createArrayBuilder().build(); }

        Class<?> componentType = collection.stream().findFirst().get().getClass();

        if (componentType.isPrimitive()  || componentType.equals(String.class)) {
            return Json.createArrayBuilder(collection).build();
        }
        else {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Object el : collection) {
                arrayBuilder.add(toJsonValue(el));
            }
            return arrayBuilder.build();
        }

    }
}
