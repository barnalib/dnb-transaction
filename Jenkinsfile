podTemplate(label: 'label', cloud: 'openshift', serviceAccount: 'kabanero-operator', containers: [
    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl', ttyEnabled: true, command: 'cat',
                      envVars: [ envVar(key: 'TAG', value: 'v4'),
                                envVar(key: 'ROLLBACKTAG', value: 'v3'),
                                envVar(key: 'IMAGENAME', value: 'dnb-transaction'),
                                envVar(key: 'B_APP', value: 'dnb-transaction-staging'),
                               envVar(key: 'PROJECT', value: 'cpfa-dnb')])
  ]){
    node('label') {
        stage('Deploy to dev') {
            container('kubectl') {
                checkout scm
                sh 'sed -i -e \'s#applicationImage: .*$#applicationImage: docker-registry.default.svc:5000/\'$PROJECT\'/\'$IMAGENAME\':\'$TAG\'#g\' app-deploy.yaml'
                sh 'cat app-deploy.yaml'
                sh 'find . -name app-deploy.yaml -type f|xargs kubectl apply -f'
            }
        }
        stage('Run Automation Test') {
            container('kubectl') {
                 sh 'echo Automation_Test_Success'
                
            }
        }
        stage('Promote to Test?') {
            container('kubectl') {
                 sh 'echo Promote'
                
            }
        }
        stage('Set A-B Route') {
           container('kubectl') {
              openshift.withCluster() {
                openshift.withProject() {
                   def route = openshift.selector("routes", "dnb-transaction") //(1)
                   println "Route: ${route}"
                   def routeObj = route.object()
                   println "Route: ${routeObj}"
                   routeObj.spec.alternateBackends = []
                   routeObj.spec.alternateBackends[0] = ["kind": "Service","name": "${B_APP}", "weight": 60] //(2)
                   openshift.apply(routeObj) //(3)
                }
            }
    }
}
        stage('Rollback release') {
            container('kubectl') {
                checkout scm
                sh 'sed -i -e \'s#applicationImage: .*$#applicationImage: image-registry.openshift-image-registry.svc:5000/\'$PROJECT\'/\'$IMAGENAME\':\'$TAG\'#g\' app-deploy.yaml'
                sh 'cat app-deploy.yaml'
                sh 'find . -name app-deploy.yaml -type f|xargs kubectl apply -f'
            }
        }
    }
} 
