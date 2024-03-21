import groovy.json.JsonSlurper

def call(String env) {
    String defStr = libraryResource 'default.json'
    String envStr = libraryResource "${env}.json"
    def jsonSlurper = new JsonSlurper()
    def config = jsonSlurper.parseText(defStr) as Map
    def envCfg = jsonSlurper.parseText(envStr) as Map
    config.putAll(envCfg)

    pipeline {
        agent any
        options {
            timestamps()
            buildDiscarder(logRotator(numToKeepStr: '3'))
        }
        parameters {
            choice(
                    name: 'REGION',
                    choices: config['regions'],
                    description: 'Target BAW/ODM region'
            )
        }
        stages {
            stage('Build') {
                steps {
                    script {
                        print(env)
                        print("${params.REGION}")
                        print(config['account'])
                        print(config['regionMap']["${params.REGION}"])
                    }
                }
            }
        }
    }
}