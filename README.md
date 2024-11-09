# 超级简单的微服务入门模板

> 说不定以后还会接着更，但是春奈酱已经被 Golang 迷住了，所以我不好说

## 项目结构

```shell
.
├── core
│   └── src
│       ├── main
│       └── test
├── gateway
│   └── src
│       ├── main
│       └── test
└── simple-test
    └── src
        ├── main
        └── test
```

## 项目说明

其中，`core` 是平台核心服务，本身不持有 `Spring IoC` 容器，为其他微服务提供基础组件。模板中实现了一个简单的 `RedisUtil` 及相关 `RedisConfig`，一个 `Result` 类，以及原来写过的加密、锁、token 工具类 等。

---

网关服务 `gateway` 为微服务提供统一的入口。请看 `application.yml`：

```yaml
spring:
  application:
    name: GatewayService
  cloud:
    gateway:
      mvc:
        routes:
          - id: simple-test
            uri: http://localhost:52711  # 没做 lb，等到用得上的时候再说吧
            predicates:
              - Path=/simple-test/**
            filters:
              - StripPrefix=1
server:
  port: 52710
```

这样，请求 `simple-test` 服务的时候，只需要访问 `http://localhost:52710/simple-test/` 即可；而 `simple-test` 服务本身跑在 `52711` 上。

网关服务可以做很多事情，比如统一鉴权、统一限流、统一日志、统一监控等等。姑且写了点日志，以后用的着的时候可以添加更多功能。

---

`simple-test` 是一个用来测试的服务，写法和之前培训中写的单体 Spring Boot 项目几乎是一样的。

目前写了一个锁的测试接口，一个缓存接口。总之就当是 playground 就行。当基于这个模板写其他业务的时候，也是类似的写法。

---

根目录下的 `pom.xml` 是父 `pom`，`core`、`gateway`、`simple-test` 下的 `pom.xml` 是子 `pom`。关于 `pom` 的继承和依赖管理，可以自行搜索，或者等后续总监们的培训。

目前中间件只有 `Redis`（我才不会告诉你是春奈酱懒得整了），部署方式是 `docker compose`，请看根目录 `docker-compose.yml`：

```yaml
services:
  redis:
    image: redis:7.4.1
    container_name: RedisInstance
    ports:
      - "9763:6379"
    # 仅在需要持久化时启用
    # volumes:
    #   - ${REDIS_DATA}:/data
    deploy:
      restart_policy:
        condition: unless-stopped
```

后续需要添加数据库、消息对列等其他中间件的时候，只需要管理这个 `docker-compose.yml` 配置即可。

## 微服务学习

### 省流

要我说 Spring Cloud 微服务治理这一套其实还挺复杂的，而且在现在来看可能有那么一点点过时（其实也还好）。

所以搓了一个超级简单的微服务模板，入门起来应该会比较容易。

### Roadmap

- [x] 简单的微服务模板
- [ ] 配置中心
- [ ] 服务注册与发现
- [ ] 服务调用（负载均衡、熔断、限流）
- [ ] 容器化部署、编排
- [ ] 日志、监控、链路追踪
- [ ] CI/CD
- [ ] 分布式

> 不懂的问AI，不好问的查搜索引擎，还不会去找这一届群主（
