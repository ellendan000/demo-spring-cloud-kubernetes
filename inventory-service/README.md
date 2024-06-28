### 注意
inventory-Service 为了区别不添加 spring cloud kubernetes config 的效果，
configmap需要手动创建、并且在 deployment.yml 中显式将 configmap 引用成环境变量。

如果像 order-service 一样添加了依赖 spring cloud kubernetes config 的话，则不需要额外进行这些的设置。