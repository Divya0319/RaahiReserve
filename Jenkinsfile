pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        AWS_DEFAULT_REGION = 'ap-northeast-1'
        S3_BUCKET = 'bkt-sample-app-artifacts'
        S3_KEY = 'raahiReserveApp.jar'
        EC2_USER = 'ubuntu'
        EC2_HOST = 'ec2-52-194-236-162.ap-northeast-1.compute.amazonaws.com'
        OPENAI_AZURE_API_KEY = credentials('OPENAI_AZURE_API_KEY')  // ID from Jenkins credentials
        AZURE_GPT_ENDPOINT = credentials('AZURE_GPT_ENDPOINT')
        AZURE_DEP_NAME = credentials('AZURE_DEP_NAME')
    }

    stages {

        stage('Checkout') {
            steps {
                // Jenkins will automatically check out the source if using Pipeline from SCM
                echo "Source code checked out from GitHub branch: learning_jenkins"
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Package Info') {
            steps {
                echo "Listing target folder contents..."
                sh 'ls -lh target'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Upload to S3') {
            steps {
                echo 'Uploading JAR to S3...'
                sh 'aws s3 cp target/*.jar s3://${S3_BUCKET}/${S3_KEY}'
            }
        }

        stage('Provision EC2') {
            steps {

                sshagent (credentials: ['ubuntu-ec2-key']) {

                    // Java Installation
                    sh '''
                        ssh -o StrictHostKeyChecking=no -o ServerAliveInterval=60 -o ServerAliveCountMax=5 ${EC2_USER}@${EC2_HOST} "set -e; which java || sudo apt install openjdk-17-jdk openjdk-17-jre -y; java --version"
                    '''
                }
                
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent (credentials: ['ubuntu-ec2-key']) {
                    echo 'Deploying on EC2...'
                    sh '''
                        echo "Pulling latest JAR from S3..."
                        ssh -o StrictHostKeyChecking=no -o ServerAliveInterval=60 -o ServerAliveCountMax=5 ${EC2_USER}@${EC2_HOST} /bin/bash <<EOF
                            echo "Pulling latest JAR from S3..."
                            aws s3 cp s3://${S3_BUCKET}/${S3_KEY} /home/ubuntu/${S3_KEY}
EOF

                        # Deploy with better debugging
                        ssh -o StrictHostKeyChecking=no -o ServerAliveInterval=60 -o ServerAliveCountMax=5 ${EC2_USER}@${EC2_HOST} /bin/bash <<EOF
                            # Stop existing app
                            echo "=== Stopping existing application ==="
                            pgrep -f ${S3_KEY} && pkill -f ${S3_KEY}
                            sleep 3

                            # Check port usage
                            echo "=== Port 8092 Status ==="
                            sudo ss -tulnp | grep 8092 || echo "Port 8092 available"

                            # Start new instance with debug output
                            export AZURE_GPT_ENDPOINT=${AZURE_GPT_ENDPOINT} AZURE_DEP_NAME=${AZURE_DEP_NAME} OPENAI_AZURE_API_KEY=${OPENAI_AZURE_API_KEY}
                            echo "=== Starting Application ==="
                            nohup java -jar /home/ubuntu/${S3_KEY} > /home/ubuntu/raahiReserveApp.log 2>&1 &
                            sleep 5

                            # Verify
                            echo "=== Verification ==="
                            if pgrep -f ${S3_KEY} >/dev/null; then
                                echo "Application running with PID: $(pgrep -f ${S3_KEY})"
                                exit 0
                            else
                                echo "=== Application Logs ==="
                                cat /home/ubuntu/raahiReserveApp.log
                                echo "ERROR: Process failed to start"
                                exit 1
                            fi
EOF
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests succeeded!'
        }
        failure {
            echo '❌ Build failed. Check logs above.'
        }
    }
}
