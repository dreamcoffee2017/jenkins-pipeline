def call(String env) {
    def config = readJSON text: (libraryResource 'default.json')
    def envCfg = readJSON text: (libraryResource "${env}.json")
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