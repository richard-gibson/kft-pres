import io.hexlabs.kloudformation.module.serverless.Method
import io.hexlabs.kloudformation.module.serverless.serverless
import io.kloudformation.KloudFormation
import io.kloudformation.StackBuilder
import io.kloudformation.unaryPlus

class Stack: StackBuilder {
    override fun KloudFormation.create(args: List<String>) {
        val (code) = args
        serverless("kt-everywhere-service", "demo", +"lambda-cf-bucket2") {
            serverlessFunction("myFunction", +code, +"index.handle", +"nodejs8.10") {
                http { path("ping") {
                    Method.GET() }
                }
            }
        }
    }
}
