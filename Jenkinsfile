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
                echo '-=- compiling project -=-'
                sh 'mvn clean compile'
            }
        }
        stage('Testing') {
            steps {
                echo '-=- execute unit tests -=-'
                sh 'mvn test'
            }
        }
        stage('Installation') {
            steps {
                echo '-=- installing project -=-'
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Packaging') {
            steps {
                echo '-=- packaging project -=-'
                sh 'mvn package -DskipTests'
            }
        }
    }
}