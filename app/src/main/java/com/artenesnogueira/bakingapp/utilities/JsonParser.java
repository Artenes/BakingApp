package com.artenesnogueira.bakingapp.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Parser to convert a json string to a POJO.
 */
public class JsonParser {

    /**
     * Parse a json string to the specified type.
     *
     * @param json the json to parse
     * @param type the type to parse to
     * @return the parsed object
     */
    public <T> T fromJson(String json, Type type) throws Exception {

        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        } catch (Exception exception) {
            throw new Exception(exception);
        }

    }

}