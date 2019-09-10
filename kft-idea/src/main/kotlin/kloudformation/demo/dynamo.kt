package kloudformation.demo

import io.hexlabs.kloudformation.module.serverless.Method
import io.hexlabs.kloudformation.module.serverless.Serverless
import io.hexlabs.kloudformation.module.serverless.serverless
import io.kloudformation.Inverter
import io.kloudformation.function.plus
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.model.iam.IamPolicyVersion
import io.kloudformation.model.iam.actions
import io.kloudformation.model.iam.policyDocument
import io.kloudformation.model.iam.resource
import io.kloudformation.property.aws.dynamodb.table.AttributeDefinition
import io.kloudformation.property.aws.dynamodb.table.KeySchema
import io.kloudformation.resource.aws.dynamodb.table
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import io.kloudformation.property.aws.iam.role.Policy
import io.kloudformation.resource.aws.iam.Role
import io.kloudformation.toJson
import io.kloudformation.toYaml
import io.kloudformation.unaryPlus

val template = KloudFormationTemplate.create {
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
}

fun main() {
  val cft = template.toYaml()
  println(cft)
}
