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
                git branch: 'main', url: 'https://github.com/KuanJuLee/finalproj_backend_BACKUP.git'
            }
        }
      
        stage('建構前端 Docker 映像檔') {
            steps {
                sh "docker build -t $FRONTEND_IMAGE ./vue-project"
            }
        }

        stage('建構後端 Docker 映像檔') {
            steps {
                sh "docker build -t $BACKEND_IMAGE ./projfinal-back"
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
                sshagent(['azure-vm-ssh']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no $AZURE_VM <<EOF
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
