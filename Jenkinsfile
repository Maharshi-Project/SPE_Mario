pipeline {
    agent any
    environment {
        // Define Docker Hub username
        DOCKER_HUB_USERNAME = 'maharshi369'
        SCANNER_HOME=tool 'sonar-scanner'
    }
    
    tools
    {
    jdk 'jdk17'
    nodejs 'node21'
    maven 'maven'
    }
    
    stages{
        stage('clean workspace'){
            steps{
                cleanWs()
            }
        }
        
        stage('Pull Git Repo'){
            steps{
                git 'https://github.com/Maharshi-Project/SPE_Mario.git'
            }
        }
        stage('Making Port Avaiable') {
            steps {
                script {
                    // Stop all containers
                    sh 'docker stop $(docker ps -aq)'
                }
            }
        }
        // stage("Sonarqube Analysis "){
        //     steps{
        //         withSonarQubeEnv('sonar-server') {
        //             sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=Mario \
        //             -Dsonar.projectKey=Mario '''
        //         }
        //     }
        // }
//         stage("Sonarqube Analysis") {
//     steps {
//         withSonarQubeEnv('sonar-server') {
//             sh ''' 
//             $SCANNER_HOME/bin/sonar-scanner 
//             -Dsonar.projectName=Mario \
//             -Dsonar.projectKey=Mario \
//             -Dsonar.exclusions=**/*.java
//             '''
//         }
//     }
// }

        // stage("quality gate"){
        //    steps {
        //         script {
        //             waitForQualityGate abortPipeline: false, credentialsId: 'Sonar-token' 
        //         }
        //     } 
        // }
        
        stage('Maven Build') {
            steps {
                dir('mario-back') {
                script{
                    sh 'mvn clean install'
                }
                }
            }
          }
          
         stage('Test'){
            steps{
              dir('mario-back') {
                script{
                    sh 'mvn test'
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
        stage('TRIVY FS SCAN') {
            steps {
                sh "trivy fs . > trivyfs.txt"
            }
        }
        
        stage('List Docker Images') {
            steps {
                script {
                    // Use Docker CLI to list images
                    sh 'docker images'
                }
            }
        }
        stage('Tag and Push Docker Images') {
            steps {
                script {
                    sh "docker tag mario_backend ${DOCKER_HUB_USERNAME}/mario_backend"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'maharshi369', passwordVariable: 'Dasnadas@1')]) {
                        sh "docker login -u 'maharshi369' -p 'Dasnadas@1' "
                        sh "docker push ${DOCKER_HUB_USERNAME}/mario_backend"
                    }

                    // Tag and push the frontend image
                  sh "docker tag mario_frontend ${DOCKER_HUB_USERNAME}/mario_frontend"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'maharshi369', passwordVariable: 'Dasnadas@1')]) {
                        sh "docker login -u 'maharshi369' -p 'Dasnadas@1'"
                        sh "docker push ${DOCKER_HUB_USERNAME}/mario_frontend"
                    }

                    // Tag and push the mysql image
                   sh "docker tag mysql ${DOCKER_HUB_USERNAME}/mysql"
                    withCredentials([usernamePassword(credentialsId: 'docker-jenkins', usernameVariable: 'maharshi369', passwordVariable: 'Dasnadas@1')]) {
                        sh "docker login -u 'maharshi369' -p 'Dasnadas@1'"
                        sh "docker push ${DOCKER_HUB_USERNAME}/mysql"
                    }
                }
            }
        }
        
        stage('OWASP FS SCAN') {
            steps {
                dependencyCheck additionalArguments: '--scan ./ --disableYarnAudit --disableNodeAudit', odcInstallation: 'DP-Check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        
        stage('Pull Docker Image of Nodes') {
            steps {
                ansiblePlaybook becomeUser: null, colorized: true, disableHostKeyChecking: true, inventory: './inventory',
                playbook: './docker-deploy.yml', sudoUser: null, vaultTmpPath: ''
            }
        }
        
    }
     post {
     always {
        emailext attachLog: true,
            subject: "'${currentBuild.result}'",
            body: "Project: ${env.JOB_NAME}<br/>" +
                "Build Number: ${env.BUILD_NUMBER}<br/>" +
                "URL: ${env.BUILD_URL}<br/>",
            to: 'maharshipatel200@gmail.com',
            attachmentsPattern: 'trivyfs.txt,trivyimage.txt'
        }
    }
}
