To start your services:
```shell
$ podman-compose -f custom-compose.yml up -d
```


To build your services:
```shell
$ podman-compose -f custom-compose.yml build
```


To stop your services:
```shell
$ podman-compose -f custom-compose.yml down
```



## examples:
### start postgreSQL:

```shell
$ podman-compose -f postgres-compose.yml up -d
```
