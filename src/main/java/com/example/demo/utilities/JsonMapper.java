package com.example.demo.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper extends ObjectMapper {
    private static JsonMapper jsonMapper;

    private JsonMapper() {
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private static JsonMapper getInstance() {
        if (jsonMapper == null) {
            jsonMapper = new JsonMapper();
        }
        return jsonMapper;
    }

    @Override
    public String writeValueAsString(Object value) {
        try {
            return super.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String toJson(Object object) {
        return JsonMapper.getInstance().writeValueAsString(object);
    }
}
