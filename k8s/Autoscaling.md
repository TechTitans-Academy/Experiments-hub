# 🧠 What HPA (Horizontal Pod Autoscaler) Does

A Horizontal Pod Autoscaler (HPA) automatically increases or decreases the number of Pod replicas for a Deployment (or ReplicaSet) based on observed metrics like CPU utilization (or custom metrics). Under load, HPA adds Pods; when load decreases, HPA removes Pods. 
Kubernetes

To make HPA work, these are required:

- Deployment with resource requests/limits defined
- Metrics Server running in the cluster
- HPA resource with min/max replicas and metric thresholds
- A way to generate load (e.g., ab, hey, busybox) Kubernetes



### ✅ Install Metrics Server on KillerKoda

#### 1️⃣ Install Metrics Server
```
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```

#### 2️⃣ Patch Metrics Server (REQUIRED on KillerKoda)
```
kubectl patch deployment metrics-server -n kube-system \
  --type='json' \
  -p='[
    {"op":"add","path":"/spec/template/spec/containers/0/args/-","value":"--kubelet-insecure-tls"},
    {"op":"add","path":"/spec/template/spec/containers/0/args/-","value":"--kubelet-preferred-address-types=InternalIP"}
  ]'
```
####  3️⃣ Restart Metrics Server
```
kubectl rollout restart deployment metrics-server -n kube-system
```

#### Verify Metrics API
```
kubectl top nodes
kubectl top pods
```



```
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: apache-hpa
  namespace: apache
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: apache-deployment
  minReplicas: 1
  maxReplicas: 5
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 5
```
📌 HPA never scales Pods directly<br>
📌 It always scales a controller (Deployment/ReplicaSet) <br>
📉 Scaling limits: `minReplicas: 1 || maxReplicas: 5` <br>
📊 Scaling metric (CPU) <br>
🎯 Scaling target: This is the heart of HPA ❤️ <br>
- What this means: <br>
-   HPA checks average CPU utilization
-   Across all pods
-   Compared to CPU request (not CPU limit!) <br>
📌 5 means:“Keep average CPU at 5% of requested CPU”<br>

📈 What students will observe:
| CPU Load   | Result               |
| ---------- | -------------------- |
| CPU < 5%   | 1 pod                |
| CPU > 5%   | 2 → 3 → 4 → 5 pods   |
| Load stops | Pods scale back to 1 |

### Example-HPA

`Deployment.yml`

```
apiVersion: apps/v1
kind: Deployment
metadata:
 name: hpa-demo-deployment
spec:
 selector:
   matchLabels:
     run: hpa-demo-deployment
 replicas: 1
 template:
   metadata:
     labels:
       run: hpa-demo-deployment
   spec:
     containers:
     - name: hpa-demo-deployment
       image: k8s.gcr.io/hpa-example
       ports:
       - containerPort: 80
       resources:
         limits:
           cpu: 500m
         requests:
           cpu: 200m
```

`HPA.yml`:
```
apiVersion: autoscaling/v1
kind: HorizontalPodAutoscaler
metadata:
 name: hpa-demo-deployment
spec:
 scaleTargetRef:
   apiVersion: apps/v1
   kind: Deployment
   name: hpa-demo-deployment
 minReplicas: 1
 maxReplicas: 10
 targetCPUUtilizationPercentage: 50
```

`Service.yml`
```
apiVersion: v1
kind: Service
metadata:
 name: hpa-demo-deployment
 labels:
   run: hpa-demo-deployment
spec:
 ports:
 - port: 80
 selector:
   run: hpa-demo-deployment
```

<b>Load Generator </b>
```
kubectl run load-generator \
  --rm -it \
  --image=busybox \
  --restart=Never \
  -- /bin/sh -c "while true; do wget -q -O- http://apache-service.apache.svc.cluster.local; done"
```
