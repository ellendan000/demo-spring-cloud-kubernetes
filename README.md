# demo-spring-cloud-kubernetes

`order-service` 会调用 `inventory-service` API。
order-service 使用 k8s 部署 1 pod，inventory-service 部署 2 pods。
order-service 依赖 `spring-cloud-starter-kubernetes` 库：
- 通过 k8s API server 获得服务 pods list，然后 `spring-cloud-starter-loadbalancer` 进行客户端负载均衡，实现 API retry 对 pods 的轮询。
- 通过 k8s API server 获得 configmap，对 Spring propertySource 进行配置注入和加载。

### 1. 使用`skaffold`一键部署
```
$ skaffold run
```
或者使用开发模式
```
$ skaffold dev
```

### 2. API calls
从 k8s 外部调用  
inventory-service API: `curl http://localhost:31003/storages/123`  
order-service API: `curl http://localhost:31002/orders --header 'Content-Type: application/json' --data '{"productId": "123"}'`

### 3. random exception for circuit-break 
为了展示resilience4j的熔断器效果，在 StorageController 中添加了random exception，如果影响展示 load-balance 的效果，可将代码注释掉。
