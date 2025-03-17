kubectl delete all --all -n dev-app
kubectl delete all --all -n default
# kubectl delete namespace ingress-nginx
# minikube addons enable ingress
kubectl apply -f .\ecommerce-namespace.yml
kubectl apply -f .\order-configmap.yml
kubectl apply -f .
kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 80:80