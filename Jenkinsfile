pipeline {
    agent any  // 讓 Jenkins 在任意可用節點上執行

    environment {
        FRONTEND_IMAGE = 'leekuanju/frontend:latest'
        BACKEND_IMAGE = 'leekuanju/backend:latest'
        DOCKER_CREDENTIALS_ID = 'petfinder'    //已於在jenkins中設定可以登入dokcer-hub的帳密和使用id
        AZURE_VM = 'KuanJu@20.2.146.70'    //username@vm公開ip
    }

    stages {
        stage('拉取程式碼') {
            steps {
                script {
                    // Clone 後端專案
                    dir('backend') {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/main']],
                            userRemoteConfigs: [[
                                url: 'https://github.com/KuanJuLee/finalproj_backend_BACKUP.git',
                                credentialsId: 'e4813fab-9926-4981-8396-c634f8c15fdd'
                            ]],
                            extensions: [[$class: 'CloneOption', depth: 1]]
                        ])
                    }

                    // Clone 前端專案
                    dir('frontend') {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/main']],
                            userRemoteConfigs: [[
                                url: 'https://github.com/KuanJuLee/finalproj_frontend_BACKUP.git',  // 你的前端 Repository URL
                                credentialsId: 'e4813fab-9926-4981-8396-c634f8c15fdd'  // 你的前端 GitHub 憑證 ID
                            ]],
                            extensions: [[$class: 'CloneOption', shallow: true, depth: 0]]
                        ])
                    }
                }
                sh "ls -lah" // 確保代碼拉取成功
            }
        }

        stage('建構前端 Docker 映像檔') {
            steps {
                sh "docker build -t $FRONTEND_IMAGE ./frontend/vue-project"   
                //從jenkins容器中讀取dockerfile，並啟動一個暫時的 Build 容器（這個容器會在 docker build 過程中運行）
            }
        }

        stage('建構後端 Docker 映像檔') {
            steps {
                sh "docker build -t $BACKEND_IMAGE ./backend/projfinal-back"
            }
        }

      
         stage('登入並推送至 Docker Hub') {
            steps { 
                withDockerRegistry([credentialsId: 'petfinder', url: '']) {
                    sh "docker push $FRONTEND_IMAGE"
                    sh "docker push $BACKEND_IMAGE"
                }
            }
        }
        stage('部署到 Azure VM') { 
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'petFinder', keyFileVariable: 'SSH_KEY')]) {
                        sh """
                        ssh -i /var/jenkins_home/.ssh/jenkins_azure_key -o StrictHostKeyChecking=no $AZURE_VM <<EOF
                            docker pull $FRONTEND_IMAGE
                            docker pull $BACKEND_IMAGE
                            docker stop frontend || true
                            docker stop backend || true
                            docker rm frontend || true
                            docker rm backend || true
                            docker run -d -p 80:80 --name frontend $FRONTEND_IMAGE
                            docker run -d -p 3000:3000 --name backend $BACKEND_IMAGE
                        EOF
                        """
            }
        }
    }
}
    }
}
