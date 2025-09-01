pipeline {
    agent any
    stages {
        stage('Stage-1') {
            steps {
                sh "echo Hello !"
                }
            }
        stage('Stage-2') {
            steps {
                sh "sleep 10"
                }
            }
        stage('Stage-3') {
            steps {
                sh "echo Wake UP!" 
                }
            }
        }
          post {
        always {
            emailext(
                to: 'learnwithtechtitans@gmail.com',
                subject: "Jenkins Pipeline: ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
                body: """ The Pipeline has completed Successfuly! 🏆
                Job: ${env.JOB_NAME}
                Build Number: ${env.BUILD_NUMBER}
                Status: ${currentBuild.currentResult}
                Build URL: ${env.BUILD_URL}
                """)
        }
  }
}
