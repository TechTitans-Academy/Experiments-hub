# What is Node Affinity in Kubernetes?

Node Affinity is a scheduling rule that tells Kubernetes which nodes a pod is allowed or preferred to run on, based on node labels.

👉 It is used to control pod placement.


### Why do we need Node Affinity?

Without node affinity:
- Kubernetes can schedule a pod on any available node

With node affinity:

- “Run this pod only on SSD nodes”
- “Prefer running on nodes in a specific zone”
- “Avoid spot/preemptible nodes”

### Types of Node Affinity

`requiredDuringSchedulingIgnoredDuringExecution`
- Must be satisfied
- If no node matches → Pod stays Pending

`preferredDuringSchedulingIgnoredDuringExecution`
- Best-effort
- Pod still runs if rule can’t be satisfied

<b>1️⃣ Required Node Affinity (Hard rule)</b>
```
apiVersion: v1
kind: Pod
metadata:
  name: nginx-ssd
spec:
  containers:
  - name: nginx
    image: nginx
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
          - key: disktype
            operator: In
            values:
            - ssd
```

<b>2️⃣ Preferred Node Affinity (Soft rule)</b>
```
apiVersion: v1
kind: Pod
metadata:
  name: nginx-preferred-affinity
spec:
  affinity:
    nodeAffinity:
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 100
        preference:
          matchExpressions:
          - key: class
            operator: In
            values:
            - devops1
  containers:
  - name: nginx
    image: nginx

```
