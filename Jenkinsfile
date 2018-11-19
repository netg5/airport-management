pipeline {

    agent any
    tools {
        maven 'maven_3_5_0'
    }

    stages {
        stage('Sonarqube operations') {
            steps {
                bat 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=10cfd6490bd968bebb9cf8515facef6fdec73e68'
            }
        }
        stage('Testing Stage') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Install Stage') {
            steps {
                bat 'mvn install'
            }
        }
        stage('Compile Stage') {
            steps {
                bat 'mvn compile'
            }
        }
        stage('Package Stage') {
            steps {
                bat 'mvn package'
            }
        }
    }
}