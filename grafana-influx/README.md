# Grafana-influx

## Exrerlan ports:

- Grafana:
- Influx:

## Start all services
```sh
kubectl apply -f .
```

## Stop all in namespace
```sh
kubectl delete all --all -n enot-bot-monitoring
```

## Config
```sh
$ kubectl config view

```

## Show config place

```sh
$ echo $KUBECONFIG
/home/mayton/.kube/config
```

```sh
$ kubectl get nodes
NAME        STATUS   ROLES    AGE     VERSION
ryzen-ssd   Ready    <none>   2y15d   v1.28.15

$ kubectl get pods
No resources found in enot-bot-monitoring namespace.

$ kubectl get services
No resources found in enot-bot-monitoring namespace.
```

## Run the Kube application

```sh
kubectl apply -f .

or

kubectl apply -f 00-namespace.yaml
kubectl apply -f 01-service.yaml
kubectl apply -f 02-monitoring.yaml
```


```
 kubectl apply -f 00-namespace.yaml 
namespace/enot-bot-monitoring created

$ kubectl get ns
NAME                  STATUS   AGE
default               Active   2y15d
enot-bot-monitoring   Active   57s
kube-node-lease       Active   2y15d
kube-public           Active   2y15d
kube-system           Active   2y15d
```

## Create services
```
$ kubectl apply -f 01-service.yaml 
service/influxdb created
service/grafana created
```

## Set namespace
```sh
$ kubectl config set-context --current --namespace=enot-bot-monitoring
Context "microk8s" modified.
```
## Show services: 

```sh
$ kubectl get svc 
NAME       TYPE       CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
grafana    NodePort   10.152.183.*     <none>        3000:32300/TCP   3m13s
influxdb   NodePort   10.152.183.*     <none>        8086:32086/TCP   3m13s

```


## Provider managed kuber:

| Provider     | Service |
| ------------ | ------- |
| Azure        | AKS     |
| AWS          | EKS     |
| Google Cloud | GKE     |


## Docker and versions


Influx:
- https://hub.docker.com/_/influxdb

Grafana:
