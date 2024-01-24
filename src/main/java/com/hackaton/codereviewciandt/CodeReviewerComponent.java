package com.hackaton.codereviewciandt;

import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class CodeReviewerComponent {
    private final KeysConfig keys;

    public CodeReviewerComponent(KeysConfig keys) {
        this.keys = keys;
    }

    public void processPullRequest(String payload) {
        var review = sendToOpenAI("Código para análise: " + payload + "\n\n");
        sendCommentToGitHub(review);
    }

    private void sendCommentToGitHub(String comment) {
         Unirest.post("https://api.github.com/repos/USER/REPO/issues/1/comments")
                 .header("Authorization", "Bearer " + keys.getGITHUB_TOKEN())
                 .header("Content-Type", "application/json")
                 .body("{\"body\": \"" + comment + "\"}")
                 .asString();
    }

    public String sendToOpenAI(String code) {
        var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(keys.getOPENAI_ENDPOINT()))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + keys.getOPENAI_API_KEY())
                .POST(HttpRequest.BodyPublishers.ofString(buildPayload(code)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Houve um erro ao enviar a requisição para a OpenAI: " + e.getMessage();
        }
    }

    private String buildPayload(String code) {
        return "{"
                + "\"prompt\": \"### Fix bugs in the below function\\n\" + code + \"\\n###\","
                + "\"temperature\": 0.8,"
                + "\"max_tokens\": 150"
                + "}";
    }
}
