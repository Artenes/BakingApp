package com.artenesnogueira.bakingapp.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.lang.reflect.Type;

/**
 * Parser to convert a json string to a POJO.
 */
public class JsonParser {

    private final Gson parser = new Gson();

    /**
     * Parse a json string to the specified type.
     *
     * @param json the json to parse
     * @param type the type to parse to
     * @return the parsed object
     * @throws Exception if the given json contain erros or does not correspond to the given type
     */
    public <T> T fromJson(String json, Type type) throws Exception {

        try {
            return parser.fromJson(json, type);
        } catch (Exception exception) {
            throw new Exception(exception);
        }

    }

    /**
     * Convert the given object to json
     *
     * @param object the object to convert
     * @return the json representation of the object
     * @throws Exception if there was an error while parsing the object
     */
    public String toJson(Object object) throws Exception {

        try {
            return parser.toJson(object);
        } catch (JsonIOException exception) {
            throw new Exception(exception);
        }

    }

}