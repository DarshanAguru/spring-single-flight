# Performance Benchmark

This benchmark demonstrates the massive performance improvements obtained by using `spring-singleflight` to prevent cache stampedes under heavy concurrent load.

## The Test Scenario

An expensive operation (simulated database call) was exposed via a REST API. We used **Apache Bench (ab)** to simulate a high-concurrency "Cache Stampede".

**Test Parameters:**
- **Requests:** 1,000,000
- **Concurrency:** 1,000
- **Payload Size:** 127 bytes (small JSON object)
- **Total Data Transferred:** ~232 MB

```bash
ab -n 1000000 -c 1000 http://localhost:9010/blog/11001
```

> [!NOTE]
> *Performance metrics (especially requests/sec and transfer rates) scale inversely with payload size. This specific test demonstrates the overhead reduction of the `spring-singleflight` coordination mechanism rather than high-bandwidth network transfer.*

## Results Summary

| Metric | Without Single Flight | With Single Flight | Improvement |
| :--- | :--- | :--- | :--- |
| **Total Time Taken** | 137.19 seconds | 58.60 seconds | **⚡ 2.3x Faster** (-78.5s) |
| **Requests Per Second** | 7,288 req/sec | 17,063 req/sec | **🚀 2.3x Higher Throughput** |
| **Average Response Time** | 137 ms | 58 ms | **⏱️ 57% Reduction** |
| **P99 Response Time** | 265 ms | 73 ms | **📉 72% Drop in Tail Latency** |

## Detailed Breakdown

### 1. Throughput (Requests Per Second)
At 1,000 concurrent users asking for the exact same resource simultaneously, the traditional approach struggled to process redundant requests. SingleFlight condensed the redundant database hits, freeing up server threads. 

**Without Single Flight:** `7,288.82 req/sec`  
**With Single Flight:** `17,063.24 req/sec` 

### 2. Time Taken for 1 Million Requests
By executing the computation only once for the batch of overlapping requests, the overall CPU/DB time was drastically reduced. 

**Without Single Flight:** `137.196 seconds`  
**With Single Flight:** `58.606 seconds`

### 3. Server Response Times (Latency Distribution)

Tail latencies (the slowest requests) are where cache stampedes hurt user experience the most. SingleFlight practically eliminates the long tail of waiting requests.

| Percentile | Without Single Flight | With Single Flight |
| :--- | :--- | :--- |
| 50% (Median) | 127 ms | 57 ms |
| 90% | 186 ms | 62 ms |
| 95% | 207 ms | 64 ms |
| **99% (P99)** | **265 ms** | **73 ms** |
| 100% (Max) | 1283 ms | 1125 ms |

## Advanced Configuration Impact

We also tested SingleFlight with a custom, longer Time-To-Live (`resultTtlMs: 1000`) and a larger cache size (`maxSize: 50000`).

The performance remained extremely consistent and stable, showing slightly better metrics (17,177 req/sec vs 17,063 req/sec), proving that increasing the cache size and TTL does not degrade the core performance of the library.

| Configuration | Total Time | Requests per Second |
| :--- | :--- | :--- |
| Single Flight (Default - 200ms) | 58.60 s | 17,063 req/sec |
| Single Flight (Custom - 1000ms)| 58.21 s | 17,177 req/sec |

---

**Conclusion**: Under heavy concurrent loads requesting the same data, `spring-singleflight` more than doubles the application's throughput and cuts average latency by over 50%, completely neutralizing the threat of a cache stampede.
