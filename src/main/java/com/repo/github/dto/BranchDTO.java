package com.repo.github.dto;

import java.util.Objects;

public record BranchDTO(String name,
                        String sha) {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (o instanceof BranchDTO(String name, String sha)) {
            return Objects.equals(this.name, name)
                    && Objects.equals(this.sha, sha);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sha);
    }
}
