pipeline {
    agent any
    
    environment {
        DOCKER_HUB_USERNAME = 'maharshi369'
    }
    
    stages {
        
        stage('clean workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Pull Git Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/Maharshi-Project/SPE_Mario.git'
            }
        }
        
        stage('Making Port Available') {
            steps {
                script {
                    // Stop all containers
                    sh 'docker stop $(docker ps -aq) || true'
                }
            }
        }
        
        stage('Maven Build') {
            steps {
                dir('mario-back') {
                script{
                    sh 'mvn clean install'
                }
                }
            }
        }
        
        stage('Docker Build and Run') {
            steps {
                script {
                   
                    sh 'docker-compose pull'
                    sh 'docker-compose build'
                }
            }
        }
        
        stage('List Docker Images') {
            steps {
                script {
                    sh 'docker images'
                }
            }
        }
        
        stage('Tag and Push Docker Images') {
            steps {
                script {
                    sh "docker tag mario_backend ${DOCKER_HUB_USERNAME}/mario_backend"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'userName', passwordVariable: 'Password')]) {
                        sh "docker push ${DOCKER_HUB_USERNAME}/mario_backend"
                    }

                    // Tag and push the frontend image
                  sh "docker tag mario_frontend ${DOCKER_HUB_USERNAME}/mario_frontend"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'userName', passwordVariable: 'Password')]) {
                        sh "docker push ${DOCKER_HUB_USERNAME}/mario_frontend"
                    }

                    // Tag and push the mysql image
                  sh "docker tag mysql ${DOCKER_HUB_USERNAME}/mysql"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'userName', passwordVariable: 'Password')]) {
                        sh "docker push ${DOCKER_HUB_USERNAME}/mysql"
                    }
                }
            }
        }
        
        stage('Start Minikube') {
            steps {
                script {
                    // sh 'minikube delete'
                    sh 'minikube start'
                    sh 'kubectl cluster-info'
                    sh 'minikube status'
                }
            }
        }
        
        stage('Minikube Deployment') {
            steps {
                
                dir('deployment/secrets') {
                    script {
                        sh 'kubectl apply -f marioSecret.yaml'
                    }
                }
                
                dir('deployment/frontend') {
                    script {
                        sh 'kubectl apply -f frontend-deployment.yaml'
                    }
                }
                
                
                dir('deployment/backend') {
                    script {
                        sh 'kubectl apply -f backend-deployment.yaml'
                    }
                }
                
                
                dir('deployment/mysql') {
                    script {
                        sh 'kubectl apply -f mysql-pvc.yaml'
                        sh 'kubectl apply -f mysql-deployment.yaml'
                    }
                }
            }
        }
        
        stage('Getting IPs') {
            steps {
                // Expose services for external access
                script {
                    sh 'minikube status'
                    sh 'kubectl get nodes'
                    sh 'minikube service mario-frontend --url'
                    sh 'minikube service mario-backend --url'
                }
            }
        }
        
        stage('ELK Stack') {
            steps {
                
                dir('Elk') {
                    script {
                        sh 'kubectl apply -f elasticSearch.yaml'
                    }
                }
                
                dir('Elk') {
                    script {
                        sh 'kubectl apply -f logstash.yaml'
                    }
                }
                
                
                dir('Elk') {
                    script {
                        sh 'kubectl apply -f kibana.yaml'
                    }
                }
                
                dir('Elk') {
                    script {
                        sh 'kubectl apply -f filebeat.yaml'
                    }
                }
            }
        }
        
        stage('Expose Kibana IP') {
            steps {
                // Expose services for external access
                script {
                    sh 'minikube service kibana --url'
                }
            }
        }
        
    }
}

