package com.example.qroc.util;

import com.example.qroc.pojo.behind.Problem;
import com.example.qroc.pojo.behind.Questionnaire;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class JsonUtils {
    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static Result jsonToResult(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Result.class);
    }

    public static Questionnaire jsonToQuestionnaire(String json) throws JsonProcessingException {
        Result result = jsonToResult(json);
        String tmpJson = objectToJson(result.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(tmpJson, Questionnaire.class);
    }

    public static List<Questionnaire> jsonToArrayQuestionnaire(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = objectMapper.readValue(json, Result.class);
        List<Questionnaire> questionnaireList = objectMapper.convertValue(result.getData(), new TypeReference<List<Questionnaire>>() {
        });
        return questionnaireList;
    }
}