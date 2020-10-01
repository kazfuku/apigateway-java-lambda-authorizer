import * as cdk from '@aws-cdk/core';
import * as apigateway from '@aws-cdk/aws-apigateway';
import * as lambda from '@aws-cdk/aws-lambda';
import * as logs from '@aws-cdk/aws-logs';

export class ApigatewayJavaLambdaAuthorizerStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const accessLogGroup = new logs.LogGroup(this, 'AccessLog');
    const api = new apigateway.RestApi(this, 'Api', {
      deployOptions: {
        loggingLevel: apigateway.MethodLoggingLevel.INFO,
        dataTraceEnabled: true,
        accessLogDestination: new apigateway.LogGroupLogDestination(accessLogGroup),
        accessLogFormat: apigateway.AccessLogFormat.clf()
      }
    });

    const authorizerFunction = new lambda.Function(this, 'AuthorizerFunction', {
      runtime: lambda.Runtime.JAVA_8,
      handler: 'com.kazfuku.aws.MapLambdaAuthorizer::handleRequest',
      // handler: 'com.kazfuku.aws.PojoLambdaAuthorizer::handleRequest',
      code: lambda.Code.fromAsset('lambda/authorizer/target/apigateway-java-lambda-authorizer-1.0.jar')
    });
    const lambdaAuthorizer = new apigateway.RequestAuthorizer(this, 'Authorizer', {
      handler: authorizerFunction,
      identitySources: [apigateway.IdentitySource.header('Authorization')]
    });

    const mockIntegration = new apigateway.MockIntegration({
      requestTemplates: {
        'application/json': '{"statusCode": 200}'
      },
      integrationResponses: [
        {
          statusCode: '200',
          responseTemplates: {
            'application/json': '{ "message": "mock", "now": "$context.authorizer.now" }'
          }
        }
      ]
    });
    const method = api.root.addMethod('GET', mockIntegration, {
      methodResponses: [
        {
          statusCode: '200'
        }
      ],
      authorizer: lambdaAuthorizer
    });
  }
}
