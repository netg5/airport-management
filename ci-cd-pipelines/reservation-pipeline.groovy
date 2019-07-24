def  POM_LOCATION = '-f reservation/pom.xml'
pipeline {
    agent any
    tools {
        maven 'maven_3_6_1'
    }
    stages {
        stage('Checkout Git repository') {
            steps {
                git branch: 'master',
                        credentialsId: '0e7fa70a-ecc7-43e6-b55a-80c8c45673ab',
                        url: 'https://github.com/sergeivisotsky/flight-reservation.git'

                sh "ls -lat"
            }
        }
        stage('Compilation') {
            steps {
                echo '-=- compiling project -=-'
                sh "mvn ${POM_LOCATION} clean compile -DskipTests=true"
            }
        }
        stage('Testing') {
            steps {
                echo '-=- execute unit tests -=-'
                sh "mvn ${POM_LOCATION} test"
            }
        }
        stage('Installation') {
            steps {
                echo '-=- installing project -=-'
                sh "mvn ${POM_LOCATION} clean install -DskipTests=true"
            }
        }
        stage('Packaging') {
            steps {
                echo '-=- packaging project -=-'
                sh "mvn ${POM_LOCATION} package -DskipTests=true"
            }
        }
        stage('Code inspection & quality gate') {
            steps {
                withSonarQubeEnv('sergei-sonar') {
                    echo '-=- run code inspection & check quality gate -=-'
                    sh "mvn ${POM_LOCATION} sonar:sonar " +
                            "-Dsonar.host.url=http://79.135.149.36:9000 " +
                            "-Dsonar.login=b18abeedf7353813275264a410d0acbc771219cd"
                }
            }
        }
    }
}