package com.repo.github.service;

import com.repo.github.dto.BranchDTO;
import com.repo.github.model.Branch;
import com.repo.github.model.Repository;
import com.repo.github.dto.RepositoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RepoSearchService {
    private final WebClient webClient;

    @Value("${repos-url}")
    private String reposUrl;

    @Value("${branches-url}")
    private String branchesUrl;

    public RepoSearchService(@Value("${base-url}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Flux<RepositoryDTO> getRepos(String username) {
        return this.webClient.get()
                .uri(reposUrl, username)
                .retrieve()
                .bodyToFlux(Repository.class)
                .filter(repository -> !repository.fork())
                .flatMap(repository -> {
                    Mono<List<BranchDTO>> branchesMono = getBranches(username, repository.name()).collectList();
                    return branchesMono.map(branches -> new RepositoryDTO(repository.name(), repository.owner().login(), branches));
                });
    }

    Flux<BranchDTO> getBranches(String username, String repoName) {
        return this.webClient.get()
                .uri(branchesUrl, username, repoName)
                .retrieve()
                .bodyToFlux(Branch.class)
                .map(branch -> new BranchDTO(branch.name(), branch.commit().sha()));
    }
}
