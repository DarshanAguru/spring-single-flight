spring-singleflight
===================

A lightweight, developer-friendly Spring Boot Starter to prevent **Cache Stampedes** using the Single Flight pattern.

[![Maven Central](https://img.shields.io/maven-central/v/com.darshan/spring-singleflight.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.darshan%20AND%20a:spring-singleflight)
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

📦 Installation
--------------

Add it to your `pom.xml`:

```xml
<dependency>
    <groupId>com.darshan</groupId>
    <artifactId>spring-singleflight</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
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
  resul-ttl-ms: 200
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
