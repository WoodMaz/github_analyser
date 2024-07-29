package com.repo.github.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RepoNotFound {
    private int status;
    private String message;
}
