pipeline {

    agent any
    tools {
        maven 'maven_3_6_0'
    }

    stages {

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
        stage('Code inspection & quality gate') {
            steps {
                withSonarQubeEnv('sergei-sonar') {
                    echo '-=- run code inspection & check quality gate -=-'
                    sh 'mvn sonar:sonar ' +
                            '-Dsonar.host.url=http://79.135.149.36:9000 ' +
                            '-Dsonar.login=b18abeedf7353813275264a410d0acbc771219cd'
                }
            }
        }
    }
}