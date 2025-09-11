
# Handler Demo

### Start with the simple playbook file:
```
- name: Install and Start Apache on Ubuntu
  hosts: linode
  become: yes   # Run with sudo privileges
  tasks:
    - name: Update apt package index
      apt:
        update_cache: yes

    - name: Install Apache (apache2)
      apt:
        name: apache2
        state: present

    - name: Start and enable Apache service
      service:
        name: apache2
        state: started
        enabled: true
```

### Start adding the Port number configurations changes in task and then add the handler.

```
---
- name: Install and Start Apache on Ubuntu
  hosts: linode
  become: yes   # Run with sudo privileges
  tasks:
    - name: Update apt package index
      apt:
        update_cache: yes

    - name: Install Apache (apache2)
      apt:
        name: apache2
        state: present

    - name: Start and enable Apache service
      service:
        name: apache2
        state: started
        enabled: true

    - name: Change Apache listen port
      ansible.builtin.lineinfile:
        path: /etc/apache2/ports.conf
        regexp: '^Listen\s+'
        line: 'Listen 84'
        state: present
        backup: yes
      notify: restart apache2
  
  handlers:
    - name: restart apache2
      service:
        name: apache2
        state: restarted
```
