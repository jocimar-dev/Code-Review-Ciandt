package com.hackaton.codereviewciandt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1/")
public class CodeReviewerController {
    public CodeReviewerComponent service;

    @PostMapping("ciandt/code-reviewer")
    public ResponseEntity<String> handleGitHubWebhook(@RequestBody String payload) {
        service.processPullRequest(payload);
        return ResponseEntity.ok().build();
    }
}
