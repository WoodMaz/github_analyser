package com.repo.github.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repo.github.dto.BranchDTO;
import com.repo.github.dto.RepositoryDTO;
import com.repo.github.model.Branch;
import com.repo.github.model.Repository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RepoSearchServiceTest {
    private static MockWebServer mockWebServer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private RepoSearchService repoSearchService;


    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        repoSearchService = new RepoSearchService(baseUrl);
    }

    @Test
    void getRepositories() throws JsonProcessingException {
        RepositoryDTO expectedRepoDTO = new RepositoryDTO("name", "owner", List.of(new BranchDTO("name", "sha")));
        RepositoryDTO expectedRepoDTO3 = new RepositoryDTO("name3", "owner", List.of(new BranchDTO("name3", "sha3")));

        Repository mockRepo = new Repository("name", new Repository.Owner("owner"), false);
        Repository mockRepo2 = new Repository("name2", new Repository.Owner("owner"), true);
        Repository mockRepo3 = new Repository("name3", new Repository.Owner("owner"), false);

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockRepo) + objectMapper.writeValueAsString(mockRepo2) + objectMapper.writeValueAsString(mockRepo3))
                .addHeader("Content-Type", "application/json"));

        Branch mockBranch = new Branch("name", new Branch.Commit("sha"));
        Branch mockBranch3 = new Branch("name3", new Branch.Commit("sha3"));

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockBranch))
                .addHeader("Content-Type", "application/json"));

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockBranch3))
                .addHeader("Content-Type", "application/json"));

        Flux<RepositoryDTO> repoFlux = repoSearchService.getRepos("owner");

        StepVerifier.create(repoFlux)
                .expectNextMatches(repo -> repo.equals(expectedRepoDTO))
                .expectNextMatches(repo -> repo.equals(expectedRepoDTO3))
                .verifyComplete();

    }
}
