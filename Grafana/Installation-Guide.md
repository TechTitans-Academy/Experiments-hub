# 📘Install Grafana, Prometheus & Loki on Minikube (MacBook)
### 🧩 Overview

This guide explains how to set up a complete monitoring stack on Minikube using Helm:

- Prometheus → Metrics collection
- Loki → Log aggregation
- Grafana → Visualization for metrics & logs
- Promtail → Log collector for Loki

### 🛠 Prerequisites

```
brew install kubectl
brew install minikube
brew install helm
```

### 📁 1. Add Helm Repositories
```
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
```

### 📡 2. Install Prometheus
```
helm install prometheus prometheus-community/prometheus
kubectl get pods
```

### 📦 3. Install Loki Stack (Loki + Promtail)
```
helm install loki grafana/loki-stack
```

### 📊 4. Install Grafana
```
helm install grafana grafana/grafana
```

### 🔐 5. Retrieve Grafana Admin Password
```
kubectl get secret grafana -o jsonpath="{.data.admin-password}" | base64 --decode
echo
```

### 🌐 6. Port Forward Grafana
```
kubectl port-forward svc/grafana 3000:80
```
Access Grafana in browser:
- 👉 http://localhost:3000
- Username: `admin`
- Password: (from previous command)

### 🧩 7. Add Prometheus Data Source in Grafana
Go to:
Grafana → Configuration → Data Sources → Add Data Source → Prometheus

Use this URL:
```
http://prometheus-server
```

### 📁 8. Add Loki Data Source
Grafana → Data Sources → Add → Loki
```
http://loki:3100
```

| Component      | Description                   | Access                                         |
| -------------- | ----------------------------- | ---------------------------------------------- |
| **Grafana**    | Dashboards for metrics & logs | [http://localhost:3000](http://localhost:3000) |
| **Prometheus** | Metrics storage               | Port-forward required                          |
| **Loki**       | Log aggregation               | Integrated automatically                       |
| **Promtail**   | Log collector                 | Runs on each node                              |
