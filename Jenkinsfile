pipeline {

    agent any
    tools {
        maven 'maven_3_6_0'
    }

    stages {
        /*stage('Sonarqube operations') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }*/
        stage('Compilation') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Testing') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Installation') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Packaging') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}