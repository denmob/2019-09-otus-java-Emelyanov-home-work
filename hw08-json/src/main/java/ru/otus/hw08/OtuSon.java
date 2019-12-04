package ru.otus.hw08;

import java.lang.reflect.*;
import javax.json.*;

class OtuSon {

    private String sResult = null;

        String toJson(Object object) {
            if (object == null)
                System.err.println("Object is null!");
            else
                sResult= objectToJson(object);
            return sResult;
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
            }
            // to do
//            - массивы объектов и примитивных типов
//            - коллекции из стандартный библиотеки.
            return null;
        }

        private String objectToJson(Object object) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                 field.setAccessible(true);
                try {
                    JsonValue jsonValue = toJsonValue(field.get(object));
                    jsonObjectBuilder.add(field.getName(), jsonValue);
                }
                catch (Exception e) {
                    System.err.println("Exception with convert field name: "+ field.getName()
                            +" to JsonValue. Exception message: "+e.getMessage());
                }
            }
            return jsonObjectBuilder.build().toString();
        }
}
