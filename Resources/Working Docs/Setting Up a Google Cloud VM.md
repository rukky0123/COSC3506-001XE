# Setting Up a Google Cloud VM for JavaFX Car Rental System

## Table of Contents

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Creating a Google Cloud VM Instance](#creating-a-google-cloud-vm-instance)
4. [Configuring the VM Instance](#configuring-the-vm-instance)
5. [Setting Up MySQL with Remote Access](#setting-up-mysql-with-remote-access)
6. [Deploying the JavaFX Car Rental System](#deploying-the-javafx-car-rental-system)
7. [Accessing the Application](#accessing-the-application)
8. [Cleanup](#cleanup)
9. [Additional Resources](#additional-resources)

## Introduction

This guide provides step-by-step instructions to set up a Virtual Machine (VM) on Google Cloud Platform (GCP) to deploy and run the JavaFX-based Car Rental System.

## Prerequisites

- Google Cloud account with billing enabled.
- Basic knowledge of Linux and command-line tools.

## Creating a Google Cloud VM Instance

Follow the steps to create a VM from the Compute Engine dashboard:
- Use Ubuntu 20.04 LTS.
- Select machine type: `e2-medium`.
- Allow HTTP and HTTPS traffic.
- Boot disk: 10+ GB standard persistent disk.

## Configuring the VM Instance

SSH into your instance and install the required tools:

```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y openjdk-21-jdk maven git unzip mysql-server
```

Install JavaFX:

```bash
wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip
unzip openjfx-21_linux-x64_bin-sdk.zip
sudo mv javafx-sdk-21 /opt/javafx-sdk-21
```

Set environment variables:

```bash
echo 'export PATH_TO_FX=/opt/javafx-sdk-21/lib' >> ~/.bashrc
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc
```

## Setting Up MySQL with Remote Access

### Step 1: Secure MySQL and Set Root Password

```bash
sudo mysql_secure_installation
```

### Step 2: Create Database and User

```bash
sudo mysql -u root -p
```

Then run:

```sql
CREATE DATABASE carrental;
CREATE USER 'app_user'@'%' IDENTIFIED BY 'algoma123';
GRANT ALL PRIVILEGES ON carrental.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
EXIT;
```

### Step 3: Enable Remote Access in MySQL Configuration

Edit the config file:

```bash
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
```

Find the line:

```
bind-address = 127.0.0.1
```

Change it to:

```
bind-address = 0.0.0.0
```

Save and restart MySQL:

```bash
sudo systemctl restart mysql
```

### Step 4: Allow MySQL Port Through Google Cloud Firewall

1. Go to **VPC Network > Firewall** in the Cloud Console.
2. Click **Create Firewall Rule**:
   - **Name:** allow-mysql
   - **Targets:** All instances in the network
   - **Source IP Ranges:** 0.0.0.0/0 (or specific IP for security)
   - **Protocols and Ports:** Select **Specified protocols and ports** → **tcp:3306**
   - Click **Create**

## Deploying the JavaFX Car Rental System

```bash
git clone https://github.com/yourusername/carrental-javafx.git
cd carrental-javafx
mvn clean package
```

Run the application:

```bash
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -jar target/carrental-javafx.jar
```

## Accessing the Application

- Use your external IP address to access any web service the app may expose.
- JavaFX apps are typically GUI-based; consider deploying as a web application if remote access is required.

## Cleanup

To prevent charges:

```bash
gcloud compute instances delete carrental-javafx-vm --zone=<your-zone>
```

Or delete from the Cloud Console.

## Additional Resources

- [JavaFX Documentation](https://openjfx.io)
- [MySQL Remote Access Guide](https://dev.mysql.com/doc/)
- [Google Cloud VM Docs](https://cloud.google.com/compute/docs/instances)

---
## To Verify Google VM 
- [Google VM](https://console.cloud.google.com/compute/instancesDetail/zones/northamerica-northeast2-c/instances/carrental-javafx-vm?project=cosc3506-carrental-project&invt=Abtt4w)
