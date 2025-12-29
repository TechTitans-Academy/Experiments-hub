## 🔹 What is a Service in Kubernetes?
A Service is an abstraction that exposes a set of Pods as a network service.

👉 Pods are ephemeral (they can die and get new IPs). <br>
👉 Services provide a stable IP + DNS name to access Pods.

## 🔹 Why do we need Services?
Without a Service:
- Pod IPs change
- Load balancing is manual
- Pod-to-Pod communication breaks

With a Service:
- Stable endpoint
- Built-in load balancing
- Easy service discovery

### ClusterIP

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
  labels:
    app: nginx
spec:
  containers:
  - name: nginx
    image: nginx
    ports:
    - containerPort: 80

```
```
apiVersion: v1
kind: Service
metadata:
  name: nginx-clusterip
spec:
  type: ClusterIP
  selector:
    app: nginx
  ports:
  - port: 80
    targetPort: 80
```


### NodePort
```
apiVersion: v1
kind: Service
metadata:
  name: nginx-nodeport
spec:
  type: NodePort
  selector:
    app: nginx
  ports:
  - port: 80
    targetPort: 80
    nodePort: 30007
```

### 🧠 Concept Summary (for demo)
| Service Type | Accessible From     | Use Case                 |
| ------------ | ------------------- | ------------------------ |
| ClusterIP    | Inside cluster only | Internal microservice    |
| NodePort     | Outside cluster     | Dev/Test external access |
