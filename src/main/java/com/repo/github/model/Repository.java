package com.repo.github.model;

public record Repository(String name,
                         Owner owner,
                         boolean fork) {
    public record Owner(String login) { }
}

