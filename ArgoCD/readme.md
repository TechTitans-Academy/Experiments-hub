# 🚀 Installation of ArgoCD on Minikube

**Argo CD** is a declarative, GitOps continuous delivery tool for Kubernetes.  
It helps developers manage and deploy applications easily by continuously monitoring the Git repository (desired state) and comparing it with the live Kubernetes cluster (current state).  
If any differences are detected, Argo CD automatically syncs the changes or rolls back as needed.  
It also provides a **web-based UI** and a **CLI** for managing deployments.

## 🧰 Requirements

Before starting, ensure the following are installed:

- **Docker**
- **Minikube** (Mini Kubernetes Cluster)
- **kubectl** (Kubernetes CLI)

## ⚙️ Installation Steps

Start by setting up Minikube and installing Argo CD:

```
# Start Minikube using Docker as the driver
minikube start --driver=docker

# Create a namespace for Argo CD
kubectl create ns argocd

# Install Argo CD using the official manifest
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.5.8/manifests/install.yaml

# Verify the deployment
kubectl get all -n argocd

# Forward the Argo CD server port to access the UI locally
kubectl port-forward svc/argocd-server -n argocd 8080:443

# Retrieve the initial admin password
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
```
<br>
You can now open your browser and access the Argo CD UI at: 👉 https://localhost:8080 <br>
<b>Username:</b> admin <br>
<b>Password: </b>(Use the decoded value from the command above)


## 🚢 Deploying Your First Application with Argo CD
#### Step 1: Create a Git Repository

- Create a Git repository containing your Kubernetes manifests. This repository acts as the source of truth for Argo CD to deploy and manage your application.

#### Step 2: Create an Application in Argo CD (via UI)

- Open the Argo CD dashboard in your browser.
- Click "New App" and fill in the following details:
```
Application Name: my-first-app
Project: default
Sync Policy: Manual or Automatic (your choice)
Repository URL: <URL of your Git repository>
Path: <Path to your Kubernetes manifests in the repository>
Cluster URL: https://kubernetes.default.svc
Namespace: <Namespace to deploy the application>
```
- Click "Create" to add the application.
- Once created:

  - Click "Sync" to deploy manually, OR set the Sync Policy to Automatic for continuous deployment.

#### 🔄 Automatic Sync on GitHub Updates

Argo CD can automatically sync your Kubernetes cluster with your Git repository.
Whenever you push changes to your manifests in GitHub, Argo CD will detect the update and apply the new configuration to your cluster.

Please refer the screenshot from [here](https://medium.com/@muppedaanvesh/a-hands-on-guide-to-argocd-on-kubernetes-part-1-%EF%B8%8F-7a80c1b0ac98).
