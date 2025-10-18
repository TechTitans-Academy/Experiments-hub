# Deploying Prometheus and Grafana on a Minikube Cluster.

## Prerequisites
Before we dive into the deployment process, ensure you have the following prerequisites:

- A running Kubernetes cluster (Minikube in this case).
- Kubectl installed and configured to interact with your Kubernetes cluster.
- Helm, the package manager for Kubernetes, installed.

#### Step 1: Install Helm Chart
`helm install [RELEASE_NAME] oci://ghcr.io/prometheus-community/charts/kube-prometheus-stack`

#### Step 2: Access Grafana

To expose the Grafana-service on Kubernetes we need to run this command

`kubectl expose service grafana --type=NodePort --target-port=3000 --name=grafana-ext -n monitoring`
<br>
`minikube service grafana-ext`

#### Step 3: Access the Grafana UI.

- Access the Grafana UI:
`minikube service  kube-prometheus-stack-grafana`

- Retrive the password from the secrets below command:

`kubectl --namespace default get secrets kube-prometheus-stack-grafana -o jsonpath="{.data.admin-password}" | base64 -d ; echo`

- The username is `admin`.

### Technology used in this demo:

- <b>Grafana:</b> A visualization tool that displays metrics and logs from data sources like Prometheus and Loki in interactive dashboards.
- <b>Prometheus:</b> A monitoring system that collects, stores, and queries time-series metrics from applications and infrastructure.
- <b>Loki:</b> A log aggregation system that stores and queries logs efficiently, designed to work seamlessly with Grafana.
- <b>Helm:</b> A package manager for Kubernetes that simplifies deploying, managing, and versioning applications using reusable configuration templates called charts. Consider this as "Package Manger like apt or Yum"

### Example:
- We use Grafana to visualize metrics such as CPU and memory utilization, which are sourced from Prometheus. Loki integrates with Grafana to provide log aggregation and analysis. Helm simplifies application installation by allowing installation with a single command, similar to package managers like APT or YUM.
