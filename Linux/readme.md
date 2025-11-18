# Linux Services Installation & Configuration Guide

This document contains installation and configuration steps for essential Linux services on **RHEL/CentOS/Rocky/AlmaLinux**.

Services included:

* NFS
* Samba
* SSH
* VSFTPD
* FTP Client
* Firewalld
* Cron
* MySQL (MariaDB)

---

## 1. NFS (Network File System)

NFS is a service that allows you to share directories over a network so that client machines can mount them as if they were local folders.
Used in Linux-to-Linux file sharing. <br>

<b>Use case:</b> Centralized storage for multiple Linux servers.

### Install NFS

```bash
sudo dnf install nfs-utils -y
```

### Enable & Start the Service

```bash
sudo systemctl enable --now nfs-server
```

### Create a Shared Directory

```bash
sudo mkdir /shared
sudo chown -R nfsnobody:nfsnobody /shared
```

### Configure NFS Exports

Edit:

```bash
sudo nano /etc/exports
```

Add:

```
/shared *(rw,sync,no_root_squash)
```

### Apply Export Rules

```bash
sudo exportfs -rav
```

---

## 2. Samba

Samba is a service that allows Linux and Windows systems to share files and printers with each other. <br>

<b>Use case:</b> Sharing a Linux directory with Windows clients.

### Install Samba

```bash
sudo dnf install samba -y
```

### Create Share Directory

```bash
sudo mkdir /sambashare
sudo chmod 777 /sambashare
```

### Configure Samba

Edit:

```bash
sudo nano /etc/samba/smb.conf
```

Add:

```
[sambashare]
   path = /sambashare
   browsable = yes
   writable = yes
   guest ok = yes
```

### Enable & Start Services

```bash
sudo systemctl enable --now smb
sudo systemctl enable --now nmb
```

---

## 3. SSH (Secure Shell)

SSH is a protocol used to securely connect to remote servers and execute commands.
It uses encryption to protect login credentials and data.
<br>

<b>Use case:</b> Remotely managing Linux servers.

### Install SSH Server

```bash
sudo dnf install openssh-server -y
```

### Start & Enable SSH

```bash
sudo systemctl enable --now sshd
```

### Check Status

```bash
systemctl status sshd
```

---

## 4. VSFTPD (FTP Server)

VSFTPD is a Linux FTP server that provides a secure and fast FTP service.
It’s known for being lightweight and stable.
<br>

<b>Use case:</b> Hosting a secure FTP server to upload/download files.

### Install VSFTPD

```bash
sudo dnf install vsftpd -y
```

### Start & Enable

```bash
sudo systemctl enable --now vsftpd
```

### Optional: Enable Anonymous FTP

Edit:

```bash
sudo nano /etc/vsftpd/vsftpd.conf
```

Change:

```
anonymous_enable=YES
```

Restart service:

```bash
sudo systemctl restart vsftpd
```

---

## 5. FTP Client

FTP is a standard network protocol for transferring files between systems.
Not encrypted by default; usually replaced by SFTP/FTPS.
<br>

<b>Use case:</b> Simple file transfer between servers.

### Install FTP Client

```bash
sudo dnf install ftp -y
```

### Connect to FTP Server

```bash
ftp <server-ip>
```

---

## 6. Firewalld

Firewalld is a firewall management tool in Linux that controls network traffic using zones and rules.
<br>

<b>Use case:</b> Allow or block ports/services (e.g., open port 80 for HTTP).

### Install Firewalld

```bash
sudo dnf install firewalld -y
```

### Start & Enable

```bash
sudo systemctl enable --now firewalld
```

### Allow a Port

```bash
sudo firewall-cmd --add-port=80/tcp --permanent
sudo firewall-cmd --reload
```

### Allow a Service

```bash
sudo firewall-cmd --add-service=http --permanent
sudo firewall-cmd --reload
```

---

## 7. Cron (Task Scheduler)

Cron is a scheduling service in Linux that runs tasks automatically at specified intervals.
<br>

<b>Use case:</b> Automating backups, scripts, cleanup jobs.

### Start & Enable Cron

```bash
sudo systemctl enable --now crond
```

### Edit Cron Jobs

```bash
crontab -e
```

### Example Cron Job

Run every 5 minutes:

```
*/5 * * * * /home/user/backup.sh
```

Explaination:
```
*    *    *    *    *   command
│    │    │    │    │
│    │    │    │    └─ Day of Week (0-7)
│    │    │    └────── Month (1-12)
│    │    └─────────── Day of Month (1-31)
│    └──────────────── Hour (0-23)
└───────────────────── Minute (0-59)
```

---

## 8. MySQL (MariaDB) Database

### Install MariaDB Server

```bash
sudo dnf install mariadb-server -y
```

### Start & Enable MariaDB

```bash
sudo systemctl enable --now mariadb
```

### Secure MySQL Installation

```bash
sudo mysql_secure_installation
```

### Login to MySQL

```bash
mysql -u root -p
```
---
