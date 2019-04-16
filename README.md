# zcp-estimate-backend

## configure environment
```
# namespace
kubectl create namespace zcp-estimate

# configmap
kubectl apply -f k8s/zcp-estimate-backend-configmap.yaml -n zcp-estimate

# mariadb
kubectl apply -f k8s/zcp-estimate-mariadb-deployment.yaml -n zcp-estimate
kubectl apply -f k8s/zcp-estimate-mariadb-service.yaml -n zcp-estimate
```

## build
```
mvn clean package
docker build -t zcp-estimate-backend .
docker tag zcp-estimate-backend pog-dev-registry.cloudzcp.io/zcp-estimate/zcp-estimate-backend:0.0.1
docker push pog-dev-registry.cloudzcp.io/zcp-estimate/zcp-estimate-backend:0.0.1

kubectl apply -f k8s/zcp-estimate-backend-deployment.yaml -n zcp-estimate
kubectl apply -f k8s/zcp-estimate-backend-service.yaml -n zcp-estimate
```
