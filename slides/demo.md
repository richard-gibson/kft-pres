###DynamoDb
https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-resource-dynamodb-table.html

start with a table, red line so a resource is needed
manditory

!!! Whats a Value<String> vs a normal String!!!

add tableName, Show that each resource also has there own Builder 
ONLY Table.Builder methods 
try  instanceType("t2.micro") it won't work

<!-- Run empty template -->

val dynamoDb2 = KloudFormationTemplate.create {
  table(keySchema = listOf(KeySchema(+"principalId", +"HASH"))) {
    tableName("ddb-table")
    attributeDefinitions(listOf(AttributeDefinition(+"principalId", +"S")))
  }
}


###EC2 with Security Group

https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-properties-ec2-instance.html

import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import io.kloudformation.toYaml
import io.kloudformation.unaryPlus

val env = parameter<String>("env")
  val ec2SecurityGroup = securityGroup(groupDescription = +"sec-group-1" + env.ref(), logicalName = "ec2SecurityGroup")
  instance {
    instanceType("t2.micro")
    imageId("ami-01234")
    securityGroups(listOf(ec2SecurityGroup.ref()))
  }

1. create Security group
-- show that group can be referenced

###http lambda 
  serverless("kt-everywhere-service", "demo", +"hexlabs-deployments") {
    serverlessFunction("myFunction", +"js", +"index.handle", +"nodejs8.10") {
      http { path("ping") {
        Method.GET() }
      }
    }
  }


  add ddb access

    val dynamoTable =     table(keySchema = listOf(KeySchema(+"principalId", +"HASH"))) {
      tableName("ddb-table")
      attributeDefinitions(listOf(AttributeDefinition(+"principalId", +"S")))
    }

   val dynamoAccess: Role.Builder.(Serverless.Parts.RoleProps) -> Unit = {
            policies(this.policies.orEmpty() + listOf( Policy( policyName = +"table-access",
                    policyDocument = policyDocument( id = "table-access-policy", version = IamPolicyVersion.V2.version ) {
                        statement( actions("dynamodb:PutItem", "dynamodb:GetItem", "dynamodb:DeleteItem"), resource = resource(dynamoTable.Arn()) )
                    }
            )))
        }


          val dynamoTable = table(keySchema = listOf(KeySchema(+"principalId", +"HASH"))) {
    tableName("ddb-table")
    attributeDefinitions(listOf(AttributeDefinition(+"principalId", +"S")))
  }

  val dynamoAccess: Role.Builder.(Serverless.Parts.RoleProps) -> Unit = {
    policies(this.policies.orEmpty() + listOf(Policy(policyName = +"table-access",
      policyDocument = policyDocument(id = "table-access-policy", version = IamPolicyVersion.V2.version) {
        statement(actions("dynamodb:PutItem", "dynamodb:GetItem", "dynamodb:DeleteItem"), resource = resource(dynamoTable.Arn()))
      }
    )))
  }

  serverless("kt-everywhere-service", "demo", +"hexlabs-deployments") {
    globalRole(dynamoAccess)
    serverlessFunction("myFunction", +"js", +"index.handle", +"nodejs8.10") {
      http {
        path("ping") {
          Method.GET()
        }
      }
    }

  }