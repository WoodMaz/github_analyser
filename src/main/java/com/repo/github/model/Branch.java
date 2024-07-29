package com.repo.github.model;

public record Branch(String name,
                     Commit commit) {
    public record Commit(String sha) {}
}
