package com.darshan.springsingleflight.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;


@Getter
@Setter
@AllArgsConstructor
public class InflightEntry {
    private final CompletableFuture<Object> future =
            new CompletableFuture<>();

}
