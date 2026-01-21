1️⃣ Multiple conditions using AND

All conditions must be true.

```
- name: Install apache on Ubuntu servers
  ansible.builtin.apt:
    name: apache2
    state: present
  when:
    - ansible_os_family == "Debian"
    - ansible_distribution_version == "20.04"

```

2️⃣ Multiple conditions using OR

At least one condition must be true.

```
- name: Install web server on Ubuntu or CentOS
  ansible.builtin.package:
    name: httpd
    state: present
  when: ansible_os_family == "RedHat" or ansible_os_family == "Debian"

```

3️⃣ AND + OR combined

```
- name: Start apache on prod Linux servers
  ansible.builtin.service:
    name: apache2
    state: started
  when: >
    (ansible_os_family == "Debian" or ansible_os_family == "RedHat")
    and env == "prod"
```

4️⃣ NOT condition
To exclude a condition.
```
- name: Run task only if not Ubuntu
  ansible.builtin.debug:
    msg: "This is NOT Ubuntu"
  when: ansible_distribution != "Ubuntu"
```
5️⃣ Check variable existence

Very common in real projects.

```
- name: Run only if variable is defined
  ansible.builtin.debug:
    msg: "Variable exists"
  when: my_var is defined
- name: Run only if variable is NOT defined
  ansible.builtin.debug:
    msg: "Variable missing"
  when: my_var is not defined

```


8️⃣ Multiple conditions in loops

You can also apply when inside loops.
```
- name: Install packages conditionally
  ansible.builtin.apt:
    name: "{{ item }}"
    state: present
  loop:
    - git
    - curl
    - nginx
  when: item != "nginx"
```
