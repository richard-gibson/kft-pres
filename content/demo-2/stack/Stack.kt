import io.kloudformation.KloudFormation
import io.kloudformation.StackBuilder
import io.kloudformation.json
import io.kloudformation.model.iam.PrincipalType
import io.kloudformation.model.iam.action
import io.kloudformation.model.iam.policyDocument
import io.kloudformation.property.aws.lambda.function.Code
import io.kloudformation.resource.aws.iam.role
import io.kloudformation.resource.aws.lambda.function
import io.kloudformation.unaryPlus

class Stack: StackBuilder {
    override fun KloudFormation.create(args: List<String>) {
        val role = role(policyDocument {
            statement(action("sts:AssumeRole")) {
                principal(PrincipalType.SERVICE, listOf(+"lambda.amazonaws.com"))
            }
        }) {
            path("/")
            managedPolicyArns(listOf(+"arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"))
        }
        val code = Code(zipFile = +"""
            exports.handle = (event, context, callback) => { console.log("hello world!"); callback(null); }
        """.trimIndent())
        function(code, +"index.handle", role.Arn(), +"nodejs8.10") {
            environment { variables(json(mapOf("ABC" to +"def")))}
        }
    }
}
