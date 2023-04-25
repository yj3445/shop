# itshop/shop

## 版本

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
- cbd/shop:0.8


### v0.9
- 支持单点登录
- source 完全来自 it-shop-sync-dev，未作修改调整
- 成果：cbd/shop:0.8

### v0.8
- 自带 login 路由
- source 完全来自 it-shop-sync-dev
- 成果：cbd/shop:0.8
