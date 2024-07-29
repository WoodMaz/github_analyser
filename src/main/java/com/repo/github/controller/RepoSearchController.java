package com.repo.github.controller;

import com.repo.github.dto.RepositoryDTO;
import com.repo.github.service.RepoSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class RepoSearchController {
    private final RepoSearchService repoSearchService;

    @GetMapping("/repo/{username}")
    public ResponseEntity<Flux<RepositoryDTO>> getRepos(@PathVariable String username) {
            return new ResponseEntity<>(repoSearchService.getRepos(username), HttpStatus.OK);
    }
}
