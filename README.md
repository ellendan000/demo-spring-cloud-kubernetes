# demo-spring-cloud-kubernetes

`order-service` 会调用 `inventory-service` API。
order-service 使用 k8s 部署 1 pod，inventory-service 部署 2 pods。
order-service 依赖 `spring-cloud-starter-kubernetes` 库：
- 通过 k8s API server 获得服务 pods list，然后 `spring-cloud-starter-loadbalancer` 进行客户端负载均衡，实现 API retry 对 pods 的轮询。
- 通过 k8s API server 获得 configmap，对 Spring propertySource 进行配置注入和加载。

### 0. 预安装
本地使用 minikube、skaffold，完成预安装，
然后进行启动和配置：
```
$ minikube start --profile local-custom
$ skaffold config set --global local-cluster true
$ eval $(minikube -p custom docker-env)
```

### 1. 使用`skaffold`一键部署
```
$ skaffold run
```
或者使用开发模式
```
$ skaffold dev
```

### 2. API calls
macOS 的 minikube ip不能使用，想要从 k8s 外部调用 k8s cluster的话，需要“额外”使用`minikube tunnel`的方式。
针对服务打开tunnel的方式：
```
$ minikube service order-service -p local-custom
$ minikube service inventory-service -p local-custom
$ minikube service spring-admin -p local-custom
# 这时 minikube 会将内部 Service 的端口，暴露成宿主机的一个随机端口<random-port>。
```

从 k8s 外部调用  
- inventory-service API: `curl http://localhost:<random-port>/storages/123`  
- order-service API: `curl http://localhost:<random-port>/orders --header 'Content-Type: application/json' --data '{"productId": "123"}'`

如果使用`minikube tunnel` 打开 ingress 的话，使用命令`curl --resolve "microservices.test:80:127.0.0.1" -i http://microservices.test/orders --header 'Content-Type: application/json' --data '{"productId": "123"}'`

### 3. random exception for circuit-break 
为了展示resilience4j的熔断器效果，在 StorageController 中添加了random exception，如果影响展示 load-balance 的效果，可将代码注释掉。
