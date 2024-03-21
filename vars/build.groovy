def call(String env) {
    def defStr = libraryResource 'default.json'
    def envStr = libraryResource "${env}.json"
    def config = readJSON text: defStr
    def envCfg = readJSON text: envStr
    config.putAll(envCfg)
    print(config)

    pipeline {
        agent any
        options {
            timestamps()
            buildDiscarder(logRotator(numToKeepStr: '3'))
        }
        parameters {
            choice(
                    name: 'REGION',
                    choices: config.regions,
                    description: 'Target BAW/ODM region'
            )
        }
        stages {
            stage('Build') {
                steps {
                    script {
                        print(env)
                        print("${params.REGION}")
                        print(config.account)
                        print(config.regionMap["${params.REGION}"])
                    }
                }
            }
        }
    }
}