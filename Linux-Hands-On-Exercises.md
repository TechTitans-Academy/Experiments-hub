# 🔥 Practical Linux Hands-On Exercises (All-in-One)

### Pre-requisites:

1. Ensure you save a personal copy of the scenario answers along with all relevant commands.
2. For each scenario, capture and document the output of commands before and after performing the solution steps.

## Section 1: Linux Permissions — Practical Tasks


### 🔹 Exercise 1: Create a Project Directory with Proper Access

<b>Task:</b>

1. Create a folder named `projectA`
2. Inside it, create a file `app.log` and Set permissions so:
   - Owner → read, write
   - Group → read
   - Others → no access

3. Verify the permissions.

### 🔹 Exercise 2: Give Execute Permission to a Script

<b>Task:</b>

1. Create a script `deploy.sh`
2. Add echo commands inside that should display "Hello - How are you doing Today!"
3. Give only the owner execute permission
4. Run the script.

### 🔹 Exercise 3: Remove Access for Others

<b>Task:</b>
- Make a file secret.txt accessible only to the owner.


### 🔹 Exercise 4: Change Ownership

<b>Task:</b>

1. Create user `dev1`
2. Create file `devfile.txt`
3. Change ownership of the file to `dev1`
4. Verify

## Section 2: User & Group Management — Practical Tasks

### 🔹 Exercise 1: Create Users & Groups for a Team

<b>Task:</b>

1. Create group `developers`
2. Add users `alice` and `bob`
3. Add both users to the `developers` group
4. How do I verify the details.


### 🔹 Exercise 2: Create a Shared Directory for a Team
<b>Task:</b>

1. Create directory `/shared/dev`
2. Group owner → `developers`
3. Permissions → Owner (rwx), Group (rwx), Others (no access)


## Section 3: Linux Package Management — Practical Tasks


### 🔹 Exercise 1: Install a Web Server

<b>Task:</b>

1. Update the package manager
2. Install `apache2` or `httpd`
3. Start the service
4. Enable service on boot
5. Verify with browser or `curl`



###  🔹 Exercise 3: Install a Specific Version of a Package

<b>Task:</b>

1. Install a specific version of nginx or httpd.




## Section 4: Networking — Practical Tasks

### 🔹 Exercise 1: Check IP Details

<b>Task:</b>

Identify the following with commands:

- Interface name
- IP address
- MAC address

### 🔹 Exercise 2: Test Server Connectivity

<b>Task:</b>

- Check if you are able to connect to the Techtitansacademy.com
- Check if you can connect to google.com

### 🔹 Exercise 3: Download a File Using CLI

<b>Task:</b>

- Download the file from below URL on the terminal. Explore multiple commands to do it.
- https://github.com/prust/wikipedia-movie-data/blob/master/movies-1900s.json

Hint: curl can download the file. Find the options.
