pipeline {
    agent any

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
        stage('Build Dockerfile'){
            steps {
                sh "/usr/local/bin/docker build . -t dinnerapp"
            }
        }
        stage('Run Application'){
            steps{
                sh "/usr/local/bin/docker run -p 8083:8080 -d dinnerapp"
            }
        }
         stage('upload to dockerhub') {
            steps {
                withCredentials([usernamePassword(credentialsId:"e66e4c8f-cd46-4f72-9cb7-2210927b3f40", passwordVariable: "dockerHubPass", usernameVariable: "dockerHubUser")]) { 
                sh "/usr/local/bin/docker tag dinnerapp techtitansacademy/dinnerapp:latest"
                sh "/usr/local/bin/docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                sh "/usr/local/bin/docker push techtitansacademy/dinnerapp:latest"
                }
            }
        }
    }
}
