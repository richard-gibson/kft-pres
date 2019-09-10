import io.kloudformation.KloudFormation
import io.kloudformation.StackBuilder
import io.kloudformation.function.plus
import io.kloudformation.property.aws.ec2.securitygroup.Ingress
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import io.kloudformation.unaryPlus

class Stack: StackBuilder {
    override fun KloudFormation.create(args: List<String>) {
        val sg = securityGroup(+"Ebabladkdsaflksdjflk") {
            securityGroupIngress(listOf(Ingress(
                    ipProtocol = +"tcp"
            )))
        }
        instance {
            securityGroups(listOf(sg.ref() + "afds"))
        }
    }
}
