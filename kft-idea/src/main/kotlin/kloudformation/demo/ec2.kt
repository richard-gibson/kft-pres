package kloudformation.demo

import io.kloudformation.function.plus
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import io.kloudformation.toYaml
import io.kloudformation.unaryPlus

val ec2InstanceTpl = KloudFormationTemplate.create {
  val sgName = parameter<String>("Name")
  val ec2SecurityGroup = securityGroup(groupDescription = +"sec-group-1" + sgName.ref(),
    logicalName = "ec2SecurityGroup")
  instance {
    instanceType("t2.micro")
    imageId("ami-012344")
    securityGroups(listOf(ec2SecurityGroup.ref()))
  }
}
fun main() {

  println(ec2InstanceTpl.toYaml())
}
