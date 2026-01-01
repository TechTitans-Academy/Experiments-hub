# ResourceQuota in Kubernetes.

A ResourceQuota in Kubernetes is used to limit and control how many resources a namespace can consume.
It helps prevent one team or application from using all cluster resources.

👉 ResourceQuota works at the namespace level.


## 🔹 Why do we need ResourceQuota?

<b>Without quotas:</b>

- One namespace can consume all CPU, Memory, Pods
- ther applications may fail to run
- No fairness between teams

<b>With quotas:</b>

- Fair resource sharing
- Better cluster stability
- Cost and capacity control

## 🔹 What can ResourceQuota limit?

A ResourceQuota can limit:

<b>🧠 Compute Resources</b>
- requests.cpu
- requests.memory
- limits.cpu
- limits.memory

<b>📦 Object Counts</b>
- Pods
- Services
- ConfigMaps
- Secrets
- PersistentVolumeClaims

<b>🌐 Storage</b>
- Total requested storage
- Storage per StorageClass


## 🔹 Simple ResourceQuota Example
```
apiVersion: v1
kind: ResourceQuota
metadata:
  name: dev-quota
  namespace: dev
spec:
  hard:
    pods: "5"
    requests.cpu: "1"
    requests.memory: 1Gi
    limits.cpu: "2"
    limits.memory: 2Gi

```

✅ What this means

In namespace dev:

- Max 5 pods
- Total CPU requests ≤ 1 core
- Total memory requests ≤ 1Gi
- CPU limits ≤ 2 cores
- Memory limits ≤ 2Gi


## 🔹 What happens if quota is exceeded?

Example error:
`Error from server (Forbidden): exceeded quota: dev-quota`

## 🔹 This is how replicate with ResourceQuota

```
apiVersion: v1
kind: Pod
metadata:
  name: quota-fail-pod
  namespace: dev
spec:
  containers:
  - name: nginx
    image: nginx
    resources:
      requests:
        memory: "2Gi"
        cpu: "500m"
      limits:
        memory: "2Gi"
        cpu: "500m"
```

Common scenarios:
- Creating more than allowed pods ❌
- Pod without CPU/Memory requests ❌ (if quota enforces requests)
- PVC exceeding storage limit ❌
