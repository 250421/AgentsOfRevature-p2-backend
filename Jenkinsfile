pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'agents-of-revature'
        DOCKER_TAG = "${BUILD_NUMBER}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'echo "Building the application..."'
                sh './mvnw clean package -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                sh 'echo "Running tests..."'
                sh './mvnw test'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                sh 'echo "Building Docker image..."'
                dir('ci') {
                    sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -f Dockerfile ..'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'echo "Deploying the application..."'
                dir('ci') {
                    sh 'docker-compose up -d'
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            echo 'Cleaning up...'
            sh 'docker system prune -f'
        }
    }
} 