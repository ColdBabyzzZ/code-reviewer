package com.hans.codereviewer.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.codereviewer.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DeepSeekBIz {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BaseConfiguration baseConfiguration;

    public String sendContent(String content) throws JsonProcessingException {
        Map<String, Object> messages = new HashMap<String, Object>();
        messages.put("role", "system");
        messages.put("content", content);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(messages);
        return process(list);
    }

    private String process(List list) throws JsonProcessingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseConfiguration.baseUrl);

        // Set headers
        httpPost.setHeader("Authorization", "Bearer " + baseConfiguration.apiKey);
        httpPost.setHeader("Content-Type", "application/json");

        // Create the request body
        Map<String,Object> params = new HashMap<>();
        params.put("model", "deepseek-coder");
        params.put("messages", list);
        params.put("stream", false);
        String jsonBody = objectMapper.writeValueAsString(params);
        log.info("jsonBody: {}", jsonBody);
        httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        // Execute the request
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getEntity().getContent());
                return jsonResponse.get("choices").get(0).get("message").get("content").asText();
            } else {
                log.error("Failed to get response from DeepSeek API, status code: " + statusCode);
            }
        } catch (Exception e) {
            log.error("Failed to get response from DeepSeek API", e);
        }
        return "fail";
    }

}
