## Adding SonarQube and DepCheck in the pipeline.

### 1. OWASP - DepCheck Plugins.

OWASP Dependency-Check is a Software Composition Analysis (SCA) tool that identifies known vulnerabilities in project dependencies. It achieves this by scanning project files and mapping dependencies to publicly disclosed vulnerabilities, primarily from the National Vulnerability Database (NVD). 

<b>Pre-requisite:</b>
- Install the plugin from the Jenkins UI >> Manage Jenkins >> Plugins.
- Setup the Plugin with from Tools settion and use any version to be install.
- Add following stage in the existing pipeline and it will start generating the report with .xml and HTML file.

```
pipeline {
    agent any

    stages {
        stage('Clone the Repo') {
            steps {
                git url: "https://github.com/TechTitans-Academy/TechTitans-Restaurant-App.git", branch: "main"
            }
        }
        stage('Compile Code!') {
            steps {
                sh "./mvnw compile"
            }
        }
        stage('Package Application') {
            steps {
                sh "./mvnw clean package -DskipTests"
            }
        }
        stage('Owasp Scan') {
            steps {
                dependencyCheck additionalArguments: '-scan ./ --format XML --format HTML', odcInstallation: 'DP'
                dependencyCheckPublisher(pattern: '**/dependency-check-report.xml')
                archiveArtifacts artifacts: 'dependency-check-report.html', fingerprint: true
            }
        }
    }
}
```


### 2. SonarQube Installation:

SonarQube Server is a powerful and popular static code analysis tool that helps developers and teams identify and eliminate bugs, vulnerabilities, and code smells in over thirty languages, including Java, Python, Go, JavaScript, TypeScript, C, C++, and C#

<b>Pre-requisite:</b>
- Install sonarqube on docker and login to the page with admin/admin. `docker run -d --name sonar -p 9000:9000 sonarqube:lts-community`
- Navigate to the Administrator >> Security >> Create token.
- Now, copy the token and add into the Jenkins credentials with Secret test as Kind.
- Navigate to the Manage Jenkins >> Tools and setup the SonarQube configuraiton.

```
pipeline {
    agent any
    environment{
        SCANNER_HOME= tool 'sonar-server'
    }
    stages {
        stage('Clone the Repo') {
            steps {
                git url: "https://github.com/TechTitans-Academy/TechTitans-Restaurant-App.git", branch: "main"
            }
        }
        stage('Compile Code!'){
            steps {
                sh "./mvnw compile"
            }
        }
        stage('Package Application'){
            steps {
                sh "./mvnw clean package -DskipTests"
            }
        }
        stage('Owasp Scan'){
            steps {
                dependencyCheck additionalArguments: '--nvdApiKey 9cb95186-729f-44fc-882b-cc3999c74456 -scan ./ --format HTML', odcInstallation: 'DP'
                dependencyCheckPublisher pattern '**/dependency-check-report.xml'
            }
        }
        stage('SonarQube Scan'){
            steps{
                withSonarQubeEnv('sonar-server'){
                    sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=dinner-app \
                    -Dsonar.java.binaries=. \
                    -Dsonar.projectKey=dinner-app '''
                }

            }
        }
    }
}
```


