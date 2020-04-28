podTemplate(label: 'label', cloud: 'openshift', serviceAccount: 'kabanero-operator', containers: [
    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl', ttyEnabled: true, command: 'cat',
                      envVars: [ envVar(key: 'TAG', value: 'v4'),
                                envVar(key: 'IMAGENAME', value: 'dnb-transaction'),
                               envVar(key: 'PROJECT', value: 'cpfa-dnb')])
  ]){
    node('label') {
        stage('Deploy') {
            container('kubectl') {
                checkout scm
                sh 'sed -i -e \'s#applicationImage: .*$#applicationImage: image-registry.openshift-image-registry.svc:5000/\'$PROJECT\'/\'$IMAGENAME\':\'$TAG\'#g\' app-deploy.yaml'
                sh 'cat app-deploy.yaml'
                sh 'find . -name app-deploy.yaml -type f|xargs kubectl apply -f'
            }
        }
    }
} 
