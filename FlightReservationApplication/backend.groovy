pipeline {
    agent any
    stages {
        stage('Code-pull'){
            steps {
                git branch: 'main',
                    url: 'https://github.com/mishrankit30/flight-reservation-app.git'
            }
        }
        stage('Code-build'){
            steps{
                sh '''
                cd FlightReservationApplication
                mvn clean package
                '''
             }
        }     
        stage('QA-TEST'){
            steps{
                withSonarQubeEnv(installationName: 'sonarr', credentialsId: 'Sonar-token') {
                    sh'''
                        cd FlightReservationApplicatio
                        mvn sonar:sonar -Dsonar.projectKey=flight-reservation
                    '''
                }
            }
        }
        stage('Docker-build'){
            steps{
                sh '''
                    cd FlightreservationApplication
                    docker build . -t mishrankit30/flightreservation-new:latest
                    docker push mishrankit30/flightreservation-new:latest
                    docker rmi mishrankit30/flightreservation-new:latest
                '''
            }
        }
        stage('Deplyo'){
            steps{
                sh '''
                    cd FlightReservationApplication
                    kubectl apply -f k8s/   
                '''
            }
        }
    }
}

