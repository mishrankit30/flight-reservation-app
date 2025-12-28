pipeline{
    agent any
    stages{
        stage{'Code-pull'}{
            steps{
                git branch: 'main', url: 'https://github.com/mishrankit30/flight-reservation-app.git'
            }
        }
    }
}