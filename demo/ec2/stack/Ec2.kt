import io.kloudformation.StackBuilder
import io.kloudformation.Value
import io.kloudformation.function.Att
import io.kloudformation.function.FindInMap
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.model.KloudFormationTemplate.Builder.Companion.awsRegion
import io.kloudformation.model.Output
import io.kloudformation.property.aws.ec2.securitygroup.ingress
import io.kloudformation.resource.aws.ec2.instance
import io.kloudformation.resource.aws.ec2.securityGroup
import kotlin.String

val ec2 = fun KloudFormationTemplate.Builder.create() {
        val keyName = parameter<String>(logicalName = "KeyName", type =
                "AWS::EC2::KeyPair::KeyName", constraintDescription =
                "must be the name of an existing EC2 KeyPair.", description =
                "Name of an existing EC2 KeyPair to enable SSH access to the instance")
        val instanceType = parameter<String>(logicalName = "InstanceType", allowedValues =
                listOf("t1.micro", "t2.nano", "t2.micro", "t2.small", "t2.medium", "t2.large",
                "m1.small", "m1.medium", "m1.large", "m1.xlarge", "m2.xlarge", "m2.2xlarge",
                "m2.4xlarge", "m3.medium", "m3.large", "m3.xlarge", "m3.2xlarge", "m4.large",
                "m4.xlarge", "m4.2xlarge", "m4.4xlarge", "m4.10xlarge", "c1.medium", "c1.xlarge",
                "c3.large", "c3.xlarge", "c3.2xlarge", "c3.4xlarge", "c3.8xlarge", "c4.large",
                "c4.xlarge", "c4.2xlarge", "c4.4xlarge", "c4.8xlarge", "g2.2xlarge", "g2.8xlarge",
                "r3.large", "r3.xlarge", "r3.2xlarge", "r3.4xlarge", "r3.8xlarge", "i2.xlarge",
                "i2.2xlarge", "i2.4xlarge", "i2.8xlarge", "d2.xlarge", "d2.2xlarge", "d2.4xlarge",
                "d2.8xlarge", "hi1.4xlarge", "hs1.8xlarge", "cr1.8xlarge", "cc2.8xlarge",
                "cg1.4xlarge"), constraintDescription = "must be a valid EC2 instance type.",
                default = "t2.small", description = "WebServer EC2 instance type")
        val sSHLocation = parameter<String>(logicalName = "SSHLocation", allowedPattern =
                "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,2})",
                constraintDescription = "must be a valid IP CIDR range of the form x.x.x.x/x.",
                default = "0.0.0.0/0", description =
                "The IP address range that can be used to SSH to the EC2 instances", maxLength =
                "18", minLength = "9")
        mappings(
            "AWSInstanceType2Arch" to mapOf(
                "t1.micro" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "t2.nano" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "t2.micro" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "t2.small" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "t2.medium" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "t2.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m1.small" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m1.medium" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m1.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m1.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m2.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m2.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m2.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m3.medium" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m3.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m3.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m3.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m4.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m4.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m4.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m4.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "m4.10xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c1.medium" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c1.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c3.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c3.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c3.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c3.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c3.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c4.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c4.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c4.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c4.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "c4.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "g2.2xlarge" to mapOf(
                    "Arch" to +"HVMG2"
                ),
                "g2.8xlarge" to mapOf(
                    "Arch" to +"HVMG2"
                ),
                "r3.large" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "r3.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "r3.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "r3.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "r3.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "i2.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "i2.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "i2.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "i2.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "d2.xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "d2.2xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "d2.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "d2.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "hi1.4xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "hs1.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "cr1.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                ),
                "cc2.8xlarge" to mapOf(
                    "Arch" to +"HVM64"
                )
            ),
            "AWSInstanceType2NATArch" to mapOf(
                "t1.micro" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "t2.nano" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "t2.micro" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "t2.small" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "t2.medium" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "t2.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m1.small" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m1.medium" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m1.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m1.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m2.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m2.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m2.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m3.medium" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m3.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m3.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m3.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m4.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m4.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m4.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m4.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "m4.10xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c1.medium" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c1.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c3.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c3.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c3.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c3.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c3.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c4.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c4.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c4.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c4.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "c4.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "g2.2xlarge" to mapOf(
                    "Arch" to +"NATHVMG2"
                ),
                "g2.8xlarge" to mapOf(
                    "Arch" to +"NATHVMG2"
                ),
                "r3.large" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "r3.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "r3.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "r3.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "r3.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "i2.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "i2.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "i2.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "i2.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "d2.xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "d2.2xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "d2.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "d2.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "hi1.4xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "hs1.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "cr1.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                ),
                "cc2.8xlarge" to mapOf(
                    "Arch" to +"NATHVM64"
                )
            ),
            "AWSRegionArch2AMI" to mapOf(
                "us-east-1" to mapOf(
                    "HVM64" to +"ami-0080e4c5bc078760e",
                    "HVMG2" to +"ami-0aeb704d503081ea6"
                ),
                "us-west-2" to mapOf(
                    "HVM64" to +"ami-01e24be29428c15b2",
                    "HVMG2" to +"ami-0fe84a5b4563d8f27"
                ),
                "us-west-1" to mapOf(
                    "HVM64" to +"ami-0ec6517f6edbf8044",
                    "HVMG2" to +"ami-0a7fc72dc0e51aa77"
                ),
                "eu-west-1" to mapOf(
                    "HVM64" to +"ami-08935252a36e25f85",
                    "HVMG2" to +"ami-0d5299b1c6112c3c7"
                ),
                "eu-west-2" to mapOf(
                    "HVM64" to +"ami-01419b804382064e4",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "eu-west-3" to mapOf(
                    "HVM64" to +"ami-0dd7e7ed60da8fb83",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "eu-central-1" to mapOf(
                    "HVM64" to +"ami-0cfbf4f6db41068ac",
                    "HVMG2" to +"ami-0aa1822e3eb913a11"
                ),
                "eu-north-1" to mapOf(
                    "HVM64" to +"ami-86fe70f8",
                    "HVMG2" to +"ami-32d55b4c"
                ),
                "ap-northeast-1" to mapOf(
                    "HVM64" to +"ami-00a5245b4816c38e6",
                    "HVMG2" to +"ami-09d0e0e099ecabba2"
                ),
                "ap-northeast-2" to mapOf(
                    "HVM64" to +"ami-00dc207f8ba6dc919",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "ap-northeast-3" to mapOf(
                    "HVM64" to +"ami-0b65f69a5c11f3522",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "ap-southeast-1" to mapOf(
                    "HVM64" to +"ami-05b3bcf7f311194b3",
                    "HVMG2" to +"ami-0e46ce0d6a87dc979"
                ),
                "ap-southeast-2" to mapOf(
                    "HVM64" to +"ami-02fd0b06f06d93dfc",
                    "HVMG2" to +"ami-0c0ab057a101d8ff2"
                ),
                "ap-south-1" to mapOf(
                    "HVM64" to +"ami-0ad42f4f66f6c1cc9",
                    "HVMG2" to +"ami-0244c1d42815af84a"
                ),
                "us-east-2" to mapOf(
                    "HVM64" to +"ami-0cd3dfa4e37921605",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "ca-central-1" to mapOf(
                    "HVM64" to +"ami-07423fb63ea0a0930",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "sa-east-1" to mapOf(
                    "HVM64" to +"ami-05145e0b28ad8e0b2",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "cn-north-1" to mapOf(
                    "HVM64" to +"ami-053617c9d818c1189",
                    "HVMG2" to +"NOT_SUPPORTED"
                ),
                "cn-northwest-1" to mapOf(
                    "HVM64" to +"ami-0f7937761741dc640",
                    "HVMG2" to +"NOT_SUPPORTED"
                )
            )
        )
        val instanceSecurityGroup = securityGroup(logicalName = "InstanceSecurityGroup",
                groupDescription = +"Enable SSH access via port 22"){
            securityGroupIngress(listOf(
                ingress(ipProtocol = +"tcp"){
                    cidrIp(sSHLocation.ref())
                    fromPort(Value.Of(22))
                    toPort(Value.Of(22))
                }
            ))
        }

    val eC2Instance = instance(logicalName = "EC2Instance"){
        imageId(FindInMap(+"AWSRegionArch2AMI", awsRegion, FindInMap(+"AWSInstanceType2Arch",
                instanceType.ref(), +"Arch")))
        instanceType(instanceType.ref())
        keyName(keyName.ref())
        securityGroups(+listOf(instanceSecurityGroup.ref()))
    }
        outputs(
            "InstanceId" to Output(value = eC2Instance.ref(), description =
                    "InstanceId of the newly created EC2 instance"), "AZ" to Output(value =
                    Att<kotlin.String>(eC2Instance.logicalName, +"AvailabilityZone"), description =
                    "Availability Zone of the newly created EC2 instance"), "PublicDNS" to
                    Output(value = Att<kotlin.String>(eC2Instance.logicalName, +"PublicDnsName"),
                    description = "Public DNSName of the newly created EC2 instance"), "PublicIP" to
                    Output(value = Att<kotlin.String>(eC2Instance.logicalName, +"PublicIp"),
                    description = "Public IP address of the newly created EC2 instance")
        )
    }
}
