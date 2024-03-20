def call(String env) {
    pipeline {
        agent any
        options {
            timestamps()
        }
        environment {

        }
        parameters {
            choice(
                    name: 'TARGET_REGION',
                    choices: ['eu-west-1'],
                    description: 'Target BAW/ODM region'
            )
        }
        stages {
            stage('Build') {
                steps {
                    script {
                        print(env)
                        print(TARGET_REGION)
                    }
                }
            }
        }
    }
}