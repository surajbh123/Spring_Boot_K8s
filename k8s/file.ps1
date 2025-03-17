minikube stop
minikube delete
minikube start --nodes 2
minikube addons enable ingress
minikube image load order-service:1.0
minikube image load dpc-order-service:1.0
kubectl apply -f .\ecommerce-namespace.yml
kubectl apply -f .\order-configmap.yml
kubectl apply -f .
kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 80:80
