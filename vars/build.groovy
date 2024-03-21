import groovy.json.JsonSlurper

void call(String env) {
    Map config = loadResource(env)
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
                        print(REGION)
                        print(config['account'])
                        print(config['regionMap'][REGION])
                    }
                }
            }
        }
    }
}

Map loadResource(String env) {
    String defStr = libraryResource 'default.json'
    String envStr = libraryResource "${env}.json"
    JsonSlurper jsonSlurper = new JsonSlurper()
    Map defCfg = jsonSlurper.parseText(defStr) as Map
    Map envCfg = jsonSlurper.parseText(envStr) as Map
    defCfg.putAll(envCfg)
    return defCfg
}