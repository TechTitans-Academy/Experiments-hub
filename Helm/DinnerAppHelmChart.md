### 📦 1. Chart Structure (Scaffold)
- Run this in your project root:

```
helm create dinner-menu
```
That will generate:

```
dinner-menu/
├── Chart.yaml
├── values.yaml
├── templates/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ... other default templates ...
└── .helmignore
```
- This gives you the basic files needed to define a chart.

### 📝 2. Edit Chart.yaml
- Update metadata:
```
apiVersion: v2
name: dinner-menu
description: “Helm chart for Dinner Menu static site”
version: 0.1.0
appVersion: "1.0"
```


### 📌 3. Edit values.yaml
Since your app is a static site served by an NGINX Docker image, use something like:

```
replicaCount: 1

image:
  repository: your-dockerhub-username/dinner-menu
  pullPolicy: IfNotPresent
  tag: "latest"

service:
  type: ClusterIP
  port: 80

ingress:
  enabled: false
```
<b>✅Tip</b>: Build and push your Docker image first (e.g., docker build -t your-dockerhub-username/dinner-menu . and docker push your-dockerhub-username/dinner-menu). The Helm chart will pull this image.

### 📄 4. Update templates/deployment.yaml

Replace the auto-generated deployment spec with something tailored for your static site:
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include "dinner-menu.fullname" . }}
  labels:
    app: {{ include "dinner-menu.name" . }}
spec:
  replicas: {{ .Values.replicaCount }}
  selector:
    matchLabels:
      app: {{ include "dinner-menu.name" . }}
  template:
    metadata:
      labels:
        app: {{ include "dinner-menu.name" . }}
    spec:
      containers:
      - name: dinner-menu
        image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
        ports:
        - containerPort: 80
        imagePullPolicy: {{ .Values.image.pullPolicy }}
```


### 📡 5. Update templates/service.yaml
Simple service to expose your site:
```
apiVersion: v1
kind: Service
metadata:
  name: {{ include "dinner-menu.fullname" . }}
spec:
  type: {{ .Values.service.type }}
  ports:
    - port: {{ .Values.service.port }}
      targetPort: 80
  selector:
    app: {{ include "dinner-menu.name" . }}
```
### 🚀 6. Install the Chart
From the helm chart folder:
```
helm install dinner-release ./dinner-menu
```
✔ The chart will deploy your static site as a Kubernetes Deployment + Service.

### 🔁 7. Modify Values & Upgrade
Change values.yaml or override values on the command line:
```
helm upgrade dinner-release ./dinner-menu \
  --set replicaCount=2
```

### ⛔ 8. Uninstall
`helm uninstall dinner-release`

### 🧠 Teaching Points (Good Examples)
##### 📌 Why Helm?
- Helm makes Kubernetes deployments repeatable and parameterized.
- You define a template and values separately.
- Helm manages install/upgrade/rollback.

##### 📌 What did we do?
- Scaffold chart (helm create)
- Defined image & ports (values.yaml)
- Wrote Kubernetes objects as templates (templates/)
- Deployed using helm install

### 📘 Optional Enhancements (if time permits)

- 💡 Add Ingress support to expose website externally
- 💡 Add ConfigMap for custom NGINX config
- 💡 Package the chart as .tgz with helm package
- 💡 Publish chart to GitHub Pages as a chart repo
