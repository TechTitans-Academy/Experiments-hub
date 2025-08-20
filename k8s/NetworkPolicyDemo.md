# Setting up the NetworkPolicy for pods/Deployments

#### Creating three nginx pods along with services:

`#kubectl run nginx1 --image nginx` <br>
`#kubectl expose pod nginx1 --port 80 --target-port 80`

`#kubectl run nginx2 --image nginx` <br>
`#kubectl expose pod nginx2 --port 80 --target-port 80`


`#kubectl run nginx2 --image nginx` <br>
`#kubectl expose pod nginx3 --port 80 --target-port 80`

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
