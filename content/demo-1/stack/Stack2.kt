import io.kloudformation.StackBuilder
import io.kloudformation.Value
import io.kloudformation.function.Reference
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.property.aws.ec2.securitygroup.ingress
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import io.kloudformation.unaryPlus
import kotlin.String
import kotlin.collections.List

class Stack2 : StackBuilder {
    override fun KloudFormationTemplate.Builder.create(args: List<String>) {
        val instanceSecurityGroup = securityGroup(logicalName = "InstanceSecurityGroup",
                groupDescription = +"Enable SSH access via port 22"){
            securityGroupIngress(listOf(
                ingress(ipProtocol = +"tcp"){
                    cidrIp(Reference("SSHLocation"))
                    fromPort(Value.Of(22))
                    toPort(Value.Of(22))
                }
            ))
        }
        instance(logicalName = "EC2Instance"){
            imageId(+"imageId")
            instanceType(+"t2.micro")
            keyName(+"myKey")
            securityGroups(+listOf(instanceSecurityGroup.ref()))
        }
    }
}
