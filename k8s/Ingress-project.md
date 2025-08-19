# Simple Ingress Demo on MiniKube:


**Pod-1:** apple-app + apple-service <br>
**Pod-2:** banana-app + banana-service

#### Starting apple-app Pod.
```
kind: Pod
apiVersion: v1
metadata:
  name: apple-app
  labels:
    app: apple
spec:
  containers:
    - name: apple-app
      image: hashicorp/http-echo
      args:
        - "-text=apple"

---

kind: Service
apiVersion: v1
metadata:
  name: apple-service
spec:
  selector:
    app: apple
  ports:
    - port: 5678 # Default port for image
```

#### Starting banana-app Pod.

```
kind: Pod
apiVersion: v1
metadata:
  name: banana-app
  labels:
    app: banana
spec:
  containers:
    - name: banana-app
      image: hashicorp/http-echo
      args:
        - "-text=banana"

---

kind: Service
apiVersion: v1
metadata:
  name: banana-service
spec:
  selector:
    app: banana
  ports:
    - port: 5678 # Default port for image

```

#### Creating a Kubernetes Ingress
```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - http:
      paths:
        - path: /apple
          pathType: Prefix
          backend:
            service:
              name: apple-service
              port:
                number: 5678
        - path: /banana
          pathType: Prefix
          backend:
            service:
              name: banana-service
              port:
                number: 5678
```

After creating all the objects, we need to start both the service tunnel and the ingress tunnel.

- #minikube service apple-service --url
- #minikube service banana-service --url
- #minikube tunnel
- #curl --resolve "hello-world.example:80:127.0.0.1" -i http://hello-world.example/apple OR curl localhost/banana
