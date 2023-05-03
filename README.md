# itshop/shop

## 版本

### v0.95

#### 概述
- 整合 it-shop-sync 里最后一个版本（6a7099） 0.95
- 增加了1个返回头，表明当前用户信息
- 删除清理了大量的 log 输出

#### 更新
- AuthenticationInterceptor.java ： add header
- PrintReqRespAspect.java:  45 UserInfoVo
- RedisLOckAspect.java
- CaffeineCacheCOnfigurer.java
- RestTemplateCOnfigurer.java
- MinioController.java
- StatsRepository.java
- InternetAccessProductPriceRepository.java
- DeviceControlleManager.java
- AuthenticationInterceptor.java
- SQLOutInterceptor.java
- CompareObjectPropertyUtil.java

#### 输出
- cbd/shop:0.93



### v0.93

#### 概述
- 不再检查referer

#### 更新
- AuthenticationInterceptor.java

#### 输出
- cbd/shop:0.93


### v0.92

#### 概述
- 通过配置中的 traefikurl ，可以控制是否检查reffer

#### 更新
- AuthenticationInterceptor.java
    - traeficurl=""的时候，不再检查reffer
    - reffer出现问题时，增加了一条打印信息

- application-docker.properties 
    - traefikurl -> null

#### 输出
- cbd/shop:0.92

### v0.91

#### 概述
- 支持单点登录
- 增加对本地 docker 环境的支持
- env.dev 出现错误，猜测是需要本地集成开发环境

#### 更新
  
- prod.env
    - ldap -> smartcloud
    - minio: http -> https
    - deviceControl ->boss
- Dockerfile
    - ENV SPRING_ENV=prod
    - ENTRYPOINT --spring.profiles.active=$SPRING_ENV
    - 删除 VOLUME /tmp

#### 输出
- cbd/shop:0.91


### v0.9
- 支持单点登录
- source 完全来自 it-shop-sync-dev，未作修改调整


### v0.8
- 自带 login 路由
- source 完全来自 it-shop-sync-dev
- 成果：cbd/shop:0.8
