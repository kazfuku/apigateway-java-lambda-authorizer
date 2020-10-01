#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { ApigatewayJavaLambdaAuthorizerStack } from '../lib/apigateway-java-lambda-authorizer-stack';

const app = new cdk.App();
new ApigatewayJavaLambdaAuthorizerStack(app, 'ApigatewayJavaLambdaAuthorizerStack');
