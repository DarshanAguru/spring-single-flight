package com.darshan.springsingleflight.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a single ongoing method execution for a given cache key.
 * This object holds the {@link CompletableFuture} that will eventually
 * resolve with the actual method result (or throw an exception).
 */
@Getter
@Setter
@AllArgsConstructor
public class InflightEntry {
    private final CompletableFuture<Object> future = new CompletableFuture<>();

}
