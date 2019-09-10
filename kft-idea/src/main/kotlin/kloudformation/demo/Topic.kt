package kloudformation.demo

import io.kloudformation.KloudFormation
import io.kloudformation.function.Reference
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.model.Output
import io.kloudformation.property.aws.dynamodb.table.*
import io.kloudformation.resource.aws.dms.endpoint
import io.kloudformation.resource.aws.s3.bucket
import io.kloudformation.resource.aws.sns.topic

import io.kloudformation.resource.aws.dynamodb.table
import io.kloudformation.resource.aws.ec2.securityGroup as ec2SecurityGroup
import io.kloudformation.property.aws.sns.topic.Subscription
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.vPC
import io.kloudformation.resource.aws.sqs.queue

import io.kloudformation.toJson
import io.kloudformation.toYaml
import io.kloudformation.unaryPlus

val topicTpl = KloudFormationTemplate.create {

//  val snsTopicName = parameter<String>("SNSTopicName")

  val sqsQueue = queue(logicalName = "sqsLogicalQueueName") {
    queueName("sqsQueueName")
  }

  val snsTopic = topic {
    topicName("snsTopicName")
    displayName("snsLogicalName")
    subscription(
      listOf(Subscription(endpoint = sqsQueue.Arn(), protocol = +"sqs"))
    )
  }

}

val topicTpl2 = KloudFormationTemplate.create {

  //  val snsTopicName = parameter<String>("SNSTopicName")

  val sqsQueue = queue(logicalName = "sqsLogicalQueueName") {
    contentBasedDeduplication(true)
    delaySeconds(5)
    fifoQueue(+true)
    queueName("sqsQueueName")
  }

  val snsTopic = topic {
    subscription(
      listOf(Subscription(endpoint = sqsQueue.Arn(), protocol = +"sqs"))
    )
  }

}





  val dynamoDb =  KloudFormationTemplate.create {
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

    val dynamoDBTable = table(logicalName = "myDynamoDBTable", keySchema = listOf(
      keySchema(attributeName = hashKeyElementName.ref(), keyType = +"HASH")
    )){
      attributeDefinitions(listOf(
        attributeDefinition(attributeName = hashKeyElementName.ref(), attributeType =
        hashKeyElementType.ref())
      ))
      provisionedThroughput(readCapacityUnits = Reference(readCapacityUnits.logicalName),
        writeCapacityUnits = Reference(writeCapacityUnits.logicalName))

    }

    table(keySchema = listOf(KeySchema(+"principalId", +"HASH")))

    val dynamoTable = table(
      keySchema = listOf(KeySchema(+"principalId", +"HASH"))
    ) {
      tableName("klouds-sockets-live-table")
      billingMode("PAY_PER_REQUEST")
      attributeDefinitions(listOf(
        AttributeDefinition(+"principalId", +"S")
      ))
    }

    outputs(
      "TableName" to Output(value = dynamoDBTable.ref(), description =
      "Table name of the newly created DynamoDB table")
    )


  }





fun main() {
  //--topics
  println(topicTpl2.toYaml())
//  println(topicTpl.toJson())
  //--topics

  //--dynamo instance
//  println(dynamoDb2.toYaml())
  //--dynamo instance
}

