package com.repo.github.dto;

import java.util.List;
import java.util.Objects;

public record RepositoryDTO(String name,
                            String owner,
                            List<BranchDTO> branches) {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (o instanceof RepositoryDTO(String name, String owner, List<BranchDTO> branches)) {
            return Objects.equals(this.name, name) &&
                    Objects.equals(this.owner, owner) &&
                    Objects.equals(this.branches, branches);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, branches);
    }
}