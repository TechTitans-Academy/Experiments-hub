# Deploying Prometheus and Grafana on a Minikube Cluster.

## Prerequisites
Before we dive into the deployment process, ensure you have the following prerequisites:

- A running Kubernetes cluster (Minikube in this case).
- Kubectl installed and configured to interact with your Kubernetes cluster.
- Helm, the package manager for Kubernetes, installed.

#### Step 1: Create a Namespace

`kubectl create namespace monitoring`

#### Step 2: Prometheus Configuration

Create a file named prometheus-config.yaml with the following content:

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: prometheus-server-conf
  namespace: monitoring
data:
  prometheus.yml: |
    global:
      scrape_interval: 15s
      evaluation_interval: 15s
    scrape_configs:
      - job_name: 'prometheus'
        static_configs:
          - targets: ['localhost:9090']
```
`kubectl apply -f prometheus-config.yaml -n monitoring`

#### Step 3: Prometheus Deployment

Create a file named prometheus-deployment.yaml:

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: prometheus-server
  namespace: monitoring
spec:
  replicas: 1
  selector:
    matchLabels:
      app: prometheus-server
  template:
    metadata:
      labels:
        app: prometheus-server
    spec:
      containers:
        - name: prometheus
          image: prom/prometheus
          ports:
            - containerPort: 9090
          volumeMounts:
            - name: config-volume
              mountPath: /etc/prometheus
      volumes:
        - name: config-volume
          configMap:
            name: prometheus-server-conf
            defaultMode: 420
```
`kubectl apply -f prometheus-deployment.yaml -n monitoring`

#### Step 4: Prometheus Deployment
To access Prometheus, we need to expose it as a service.

Create a file named prometheus-service.yaml:

```
apiVersion: v1
kind: Service
metadata:
  name: prometheus-service
  namespace: monitoring
spec:
  selector:
    app: prometheus-server
  ports:
    - protocol: TCP
      port: 80
      targetPort: 9090
  type: LoadBalancer
```

Apply the service:

`kubectl apply -f prometheus-service.yaml -n monitoring`

#### Step 5: Install Grafana

We’ll use Helm to install Grafana. First, add the Grafana Helm repository and update it.

`helm repo add grafana https://grafana.github.io/helm-charts` <br>
`helm repo update`

Install Grafana in the monitoring namespace:
'helm install grafana grafana/grafana --namespace monitoring'

Check the status of the pods to ensure Grafana is running:
`kubectl get pods -n monitoring`

#### Step 6: Access Grafana

To expose the Grafana-service on Kubernetes we need to run this command

`kubectl expose service grafana --type=NodePort --target-port=3000 --name=grafana-ext -n monitoring`
<br>
`minikube service grafana-ext`

#### Step 7: Access the Graphana UI.






