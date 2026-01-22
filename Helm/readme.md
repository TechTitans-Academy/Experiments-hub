### 1. Introduction to Helm (10 minutes)

- What is Helm?
- Helm is a package manager for Kubernete
- Helm packages are called Charts

#### Similar to:
- apt for Linux
- npm for Node.js
- yum for RHEL

#### Problems Helm Solves

- Too many YAML files
- Repeated configurations
- Versioning and rollbacks
- Environment-specific configs (dev/prod)

#### 📌 Key Terms

- Chart
- Release
- Repository
- Values

### 2. Helm Architecture & Components (10 minutes)

- Helm Components
- Helm CLI
- Chart
- Release
- Kubernetes Cluster

### 3. Install Helm (5 minutes)

- On Linux / Mac
```
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
````
- Verify
`helm version`

### 4. Helm Repository & First Chart (15 minutes)
- Add Helm Repository

```
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```
- Search Charts
`helm search repo nginx`

- Install NGINX
`helm install my-nginx bitnami/nginx`

- Verify
```
helm list
kubectl get pods
kubectl get svc
```

### 5. Helm Chart Structure (15 minutes)

- Create a Chart.
`helm create myapp`

- Folder Structure:

```
myapp/
├── Chart.yaml        # Chart metadata
├── values.yaml       # Default values
├── templates/        # Kubernetes YAMLs
│   ├── deployment.yaml
│   ├── service.yaml
└── charts/
```
- Explain:
=> values.yaml → user configuration
=> templates/ → dynamic YAML with Go templating

### 6. values.yaml Customization (15 minutes)
- Example: values.yaml
```
  replicaCount: 2

image:
  repository: nginx
  tag: latest

service:
  type: NodePort
  port: 80
```
- Install Using Values: `helm install myapp ./myapp`
- Override Values at Runtime: `helm install myapp ./myapp --set replicaCount=3`

### 7. Common Helm Commands (5 minutes)

```
helm list
helm install
helm upgrade
helm uninstall
helm status
helm history
helm rollback
```

### 8. Real-World Use Case (5 minutes)

Where Helm is Used
- CI/CD pipelines (Jenkins, GitHub Actions)
- Microservices deployment
- Multi-environment deployments
- Production rollbacks
```
values-dev.yaml
values-prod.yaml
```

Helm Workflow
helm install → chart → Kubernetes objects → running app
