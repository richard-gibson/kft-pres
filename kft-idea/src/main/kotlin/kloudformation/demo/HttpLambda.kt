package kloudformation.demo

import io.hexlabs.kloudformation.module.serverless.Method
import io.hexlabs.kloudformation.module.serverless.serverless
import io.kloudformation.model.KloudFormationTemplate
import io.kloudformation.toYaml
import io.kloudformation.unaryPlus

val serverlessHttp = KloudFormationTemplate.create {
  serverless("kug-example-service2", "demo", +"hexlabs-deployments") {
    serverlessFunction("myFunction2", +"js", +"index.handle", +"nodejs8.10") {
      http { path("ping") {
        Method.GET() }
      }
    }
  }
}

fun main() {
  println(serverlessHttp.toYaml())
}
