kubectl delete -f mysql.yaml
kubectl delete -f secrets.yaml

kubectl apply -f mysql.yaml
kubectl apply -f secrets.yaml