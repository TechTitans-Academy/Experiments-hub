# Kubernetes Volumes!

In Kubernetes, volumes are used to persist and share data between containers and across pod restarts. Below is a clear, DevOps-friendly classification of the main types of volumes in Kubernetes, with simple explanations and use cases.

## 🔍 How Kubernetes Thinks About Volumes

A Kubernetes Volume is:

- `Any mechanism that makes data available inside a container’s filesystem.`

That data can come from:

- Disk
- Network storage
- Node filesystem
- Kubernetes objects (ConfigMap, Secret)

`So volume ≠ only storage disk.`


### 1. HostPath Volume:

```
apiVersion: v1
kind: Pod
metadata:
  name: emptydir-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: temp-vol
      mountPath: /tmp/data
  volumes:
  - name: temp-vol
    emptyDir: {}
```

### 2. EmptyDir Volume:

```
apiVersion: v1
kind: Pod
metadata:
  name: emptydir-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: temp-vol
      mountPath: /tmp/data
  volumes:
  - name: temp-vol
    emptyDir: {}
```

### 🔑 Core Idea (One Line)

In Kubernetes, ConfigMaps and Secrets are considered volumes because they are ultimately mounted into Pods as files.

Kubernetes classifies anything that provides data to a container via the filesystem as a volume.


### 3. Config Map:

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  app.conf: |
    ENV=dev


apiVersion: v1
kind: Pod
metadata:
  name: configmap-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: config-vol
      mountPath: /etc/config
  volumes:
  - name: config-vol
    configMap:
      name: app-config
```

### 4. Secret Volume

```
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
data:
  password: cGFzc3dvcmQ=

apiVersion: v1
kind: Pod
metadata:
  name: secret-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: secret-vol
      mountPath: /etc/secret
  volumes:
  - name: secret-vol
    secret:
      secretName: db-secret
```

### Some more Volume Types:

1. <b>NSF Volumes :- </b> A volume that mounts storage from a remote NFS server into a Pod. [Multiple Pods accessing the same uploaded files]
3. <b>Cloud Volume (AWS EBS Example) :-</b> A cloud-managed block storage volume provided by AWS. [Pod must run on the same AZ as the EBS volume]
4. <b>CSI Volume :-</b> A standard plugin interface that allows Kubernetes to use any storage system. [Kubernetes core does not manage storage logic anymore — CSI drivers do.]
5. <b>DownwardAPI Volume :-</b> A volume that exposes Pod metadata to containers as files. [Application writes logs using Pod name as filename]
6. <b>Projected Volume :-</b> A volume that combines multiple sources into one directory. [/etc/app/ contains config, secrets, and pod metadata together]


| Volume Type | Purpose                | Persistent | Typical Use        |
| ----------- | ---------------------- | ---------- | ------------------ |
| NFS         | Shared network storage | ✅          | Shared files       |
| AWS EBS     | Cloud block storage    | ✅          | Databases          |
| CSI         | Storage abstraction    | ✅          | Production         |
| DownwardAPI | Pod metadata           | ❌          | Logging/monitoring |
| Projected   | Combine sources        | ❌          | Config management  |


| Volume Type   | Persistent | Typical Use        |
| ------------- | ---------- | ------------------ |
| emptyDir      | ❌          | Temp data          |
| hostPath      | ❌          | Node access        |
| ConfigMap     | ❌          | App config         |
| Secret        | ❌          | Sensitive data     |
| PV/PVC        | ✅          | Databases          |
| NFS           | ✅          | Shared storage     |
| Cloud Volumes | ✅          | Cloud apps         |
| CSI           | ✅          | Production storage |
| DownwardAPI   | ❌          | Pod metadata       |
| Projected     | ❌          | Multiple sources   |

