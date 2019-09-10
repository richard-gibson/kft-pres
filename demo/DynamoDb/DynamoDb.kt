import io.kloudformation.StackBuilder
import io.kloudformation.function.Reference
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.model.Output
import io.kloudformation.property.aws.dynamodb.table.attributeDefinition
import io.kloudformation.property.aws.dynamodb.table.keySchema
import io.kloudformation.property.aws.dynamodb.table.provisionedThroughput
import io.kloudformation.resource.aws.dynamodb.table
import kotlin.String

class DynamoDb : StackBuilder {
    override fun KloudFormationTemplate.Builder.create() {
        val hashKeyElementName = parameter<String>(logicalName = "HashKeyElementName",
                allowedPattern = "[a-zA-Z0-9]*", constraintDescription =
                "must contain only alphanumberic characters", description =
                "HashType PrimaryKey Name", maxLength = "2048", minLength = "1")
        val hashKeyElementType = parameter<String>(logicalName = "HashKeyElementType",
                allowedPattern = "[S|N]", constraintDescription = "must be either S or N", default =
                "S", description = "HashType PrimaryKey Type", maxLength = "1", minLength = "1")
        val readCapacityUnits = parameter<String>(logicalName = "ReadCapacityUnits", type =
                "Number", constraintDescription = "must be between 5 and 10000", default = "5",
                description = "Provisioned read throughput", maxValue = "10000", minValue = "5")
        val writeCapacityUnits = parameter<String>(logicalName = "WriteCapacityUnits", type =
                "Number", constraintDescription = "must be between 5 and 10000", default = "10",
                description = "Provisioned write throughput", maxValue = "10000", minValue = "5")
        val myDynamoDBTable = table(logicalName = "myDynamoDBTable", keySchema = listOf(
            keySchema(attributeName = hashKeyElementName.ref(), keyType = +"HASH")
        )){
            attributeDefinitions(listOf(
                attributeDefinition(attributeName = hashKeyElementName.ref(), attributeType =
                        hashKeyElementType.ref())
            ))
            provisionedThroughput(readCapacityUnits = Reference(readCapacityUnits.logicalName),
                    writeCapacityUnits = Reference(writeCapacityUnits.logicalName))

        }
        outputs(
            "TableName" to Output(value = myDynamoDBTable.ref(), description =
                    "Table name of the newly created DynamoDB table")
        )
    }
}
