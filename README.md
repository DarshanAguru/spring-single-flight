spring-singleflight
===================

A lightweight, developer-friendly Spring Boot Starter to prevent **Cache Stampedes** using the Single Flight pattern.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)](https://spring.io/projects/spring-boot)

---

🚀 Introduction
---------------

When a popular cache key expires, high-concurrency applications often suffer from a **cache stampede**—hundreds of simultaneous requests attempting to compute and cache the same exact data, leading to database overload. 

**Spring SingleFlight** solves this elegantly. Instead of letting all 100 requests hit your database, it allows the *first* request to proceed while the other 99 wait. Once the first request finishes, all 100 requests receive the same result instantly.

---

✨ Features
---------

- **Drop-in replacement:** Just add the dependency and use `@SingleFlight`.
- **SpEL Support:** Define highly dynamic cache keys using Spring Expression Language.
- **Auto-Configured:** Requires exactly zero boilerplate code.
- **Customizable:** Control Time-To-Live (TTL) and thread pool size directly from `application.yml`.

---

📊 Performance Impact
---------------------

Under a simulated Cache Stampede of **1,000,000 requests** (1,000 concurrent users), `spring-singleflight` delivered massive improvements:

- **🚀 2.3x Higher Throughput:** Increased from `7,288` to `17,063` requests per second.
- **⚡ 2.3x Faster Execution:** Total time dropped from `137s` to `58s`.
- **📉 72% Drop in Tail Latency:** P99 response time fell from `265ms` down to just `73ms`.

> *Note: Test conducted with a concurrency level of 1,000 for 1M requests. The endpoint returned a 127 byte payload (transferring ~232MB total). Absolute throughput will vary based on your payload sizes and database latency.*

Check out the full [**Performance Benchmark Report**](./PERFORMANCE.md) for detailed Apache Bench metrics!

---

📦 Installation
--------------

As the package is not yet published to Maven Central, you can use the snapshot JAR provided in the `snapshot` folder of the repository.

### Installing to Local Maven Repository (Recommended)

1. Download the `spring-singleflight-0.0.1-SNAPSHOT.jar` from the `snapshot/` directory.
2. Run the following command to install it into your local Maven cache:

```bash
mvn install:install-file \
   -Dfile=snapshot/spring-singleflight-0.0.1-SNAPSHOT.jar \
   -DgroupId=com.darshan \
   -DartifactId=spring-singleflight \
   -Dversion=0.0.1-SNAPSHOT \
   -Dpackaging=jar
```

3. Add the dependency to your project:

#### Maven

```xml
<dependency>
    <groupId>com.darshan</groupId>
    <artifactId>spring-singleflight</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### Gradle

```gradle
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation 'com.darshan:spring-singleflight:0.0.1-SNAPSHOT'
}
```

### Option 2: Using the JAR directly (Manual Dependencies)

If you strictly want to use the `.jar` file without installing it to your local Maven cache, you can reference it directly.

#### Maven

1. Place the JAR in a folder in your project (e.g., `snapshot/`).
2. Add the JAR and the required dependencies to your `pom.xml`:

```xml
<!-- Your local JAR file -->
<dependency>
    <groupId>com.darshan</groupId>
    <artifactId>spring-singleflight</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/snapshot/spring-singleflight-0.0.1-SNAPSHOT.jar</systemPath>
</dependency>

<!-- Required dependencies for spring-singleflight to work! -->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>3.1.8</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

#### Gradle

1. Place the JAR in a folder in your project (e.g., `libs/`).
2. Add the flat directory repository and the required dependencies to your `build.gradle`:

```gradle
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    // Your local JAR file
    implementation name: 'spring-singleflight-0.0.1-SNAPSHOT'

    // Required dependencies for spring-singleflight to work!
    implementation 'com.github.ben-manes.caffeine:caffeine:3.1.8'
    implementation 'org.springframework.boot:spring-boot-starter-aop'
}
```

---

💻 Usage
--------

Simply annotate any method in a Spring Bean with `@SingleFlight`. 

```java
import com.darshan.springsingleflight.annotation.SingleFlight;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Resolves the key using SpEL based on the incoming argument
    @SingleFlight(key = "'user:' + #userId", timeoutMs = 5000)
    public UserProfile getUserProfile(Long userId) {
        // This expensive DB call will only execute ONCE for concurrent requests asking for the same userId
        return db.findUserById(userId);
    }
}
```

---

⚙️ Properties Default Configuration
----------------------------------
You can customize the registry in your `application.yml`:

```yaml
singleflight:
  # How long the shared result stays in cache for subsequent instant requests (Default: 200 ms)
  result-ttl-ms: 200
  # Max number of single-flight keys to keep in memory (Default: 10000)
  max-size: 10000
```

---

🤝 Contributing
--------------

Contributions are welcome!
Please read the [Contributing Guide](./CONTRIBUTING.md) and follow the [Code of Conduct](./CODE_OF_CONDUCT.md).

We welcome contributions! To contribute:

1. Fork the repo.
2. Create a feature branch: `git checkout -b feature/my-feature`.
3. Commit changes: `git commit -m 'Add my feature'`.
4. Push and open a PR.

---

📜 Code of Conduct
-----------------

We follow the [Contributor Covenant](https://www.contributor-covenant.org/). Be respectful and inclusive.

---

📜 License
---------

This project is licensed under the **MIT License** — see the [LICENSE](./LICENSE) file for details.

---

💬 Author
--------

**Darshan Aguru**
React Native Developer • AWS Certified Solutions Architect
GitHub: [@DarshanAguru](https://github.com/DarshanAguru)
Portfolio: [DarshanAguru](https://www.thisdarshiii.in)

---

👥 Contributors
--------------

This project exists thanks to all the people who contribute.

See: https://github.com/DarshanAguru/spring-singleflight/graphs/contributors

---

⭐ Support
--------

If you find this library useful, give it a ⭐ on GitHub: https://github.com/DarshanAguru/spring-singleflight
