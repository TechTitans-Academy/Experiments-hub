# Setting up the NetworkPolicy for pods/Deployments

#### Creating three nginx pods along with services:

`kubectl run nginx1 --image nginx` <br>
`kubectl expose pod nginx1 --port 80 --target-port 80`

`kubectl run nginx2 --image nginx` <br>
`kubectl expose pod nginx2 --port 80 --target-port 80`


`kubectl run nginx3 --image nginx` <br>
`kubectl expose pod nginx3 --port 80 --target-port 80`

#### Adding the NetworkPolicy that will restrice the access to nginx1 and allow only nginx2 to access it.

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
spec:
  podSelector:
    matchLabels:
      run: nginx1
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          run: nginx2
    ports:
    - protocol: TCP
      port: 80
```

<b>Testing:</b> 
- This will fail: `kubectl exec -it nginx3 -- curl nginx1:80`
- Works! `kubectl exec -it nginx2 -- curl nginx1:80`
- Fail! `kubectl exec -it nginx3 -- curl nginx1:80`




#### 🔒 Deny-All Ingress NetworkPolicy.

```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: deny-all-ingress
spec:
  podSelector:
    matchLabels:
      run: nginx1
  policyTypes:
  - Ingress
```

#### NetworkPolicies work at the Pod level, not at the Service level.

<b>✅ How NetworkPolicy actually works</b>

- NetworkPolicy selects Pods using labels
- Traffic is allowed or denied to Pods
- Services are not enforced by NetworkPolicy

<b>🧩 What happens with Services then?</b>

- A Service is just a virtual IP + load-balancing abstraction.
- When traffic goes to a Service:

```
Client → Service (ClusterIP / NodePort / LB) → Backend Pods
```

👉 NetworkPolicy is enforced when traffic reaches the target Pod, not on the Service itself.

📌 Key Points: 
- ❌ You cannot apply a NetworkPolicy directly to a Service
- ✅ You apply NetworkPolicy to Pods behind the Service
- ✅ If Pods are denied by NetworkPolicy, the Service will also stop working

