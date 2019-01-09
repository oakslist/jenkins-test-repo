#!groovy

properties([
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
        disableConcurrentBuilds(),
        parameters(parameterDefinitions: [string(defaultValue: '', description: '', name: 'MVN_RELEASE_VERSION')])
])

def mvnReleaseVersion = ''
try {
    mvnReleaseVersion = MVN_RELEASE_VERSION
} catch (errorOnUnboundVariable) {
}
def isReleaseVersion = mvnReleaseVersion != ''

try {
    node {
        currentBuild.result = 'SUCCESS'
        try {
            if (isReleaseVersion) {
                stage('Set version') {
                    setVersion(mvnReleaseVersion)
                }
            }
            stage('Build') {
                    echo 'in Build'
                //bat 'mvn clean install'
                //bat 'mvn -Pintegration-test clean ${env.BRANCH_NAME == \'master\' ? \'deploy\' : \'verify\'}'
                //execMaven("-Pintegration-test clean ${env.BRANCH_NAME == 'master' ? 'deploy' : 'verify'}")
                archiveJUnitResults()
            }
            if (isBuildOK() && isReleaseVersion) {
                stage('Tag version') {
                    tagVersion(env.BRANCH_NAME, mvnReleaseVersion)
                    updateToNextDevelopmentVersion(env.BRANCH_NAME, mvnReleaseVersion)
                }
            }
        } catch (buildError) {
            currentBuild.result = 'FAILURE'
            if (isReleaseVersion) {
                undoTagVersion(mvnReleaseVersion)
            }
            throw buildError
        }
    }
} finally {
}
def isBuildOK() {
    currentBuild.result == 'SUCCESS'
}
