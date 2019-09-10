import io.hexlabs.kloudformation.module.s3.s3Website
import io.kloudformation.KloudFormation
import io.kloudformation.StackBuilder

class Stack: StackBuilder {
    override fun KloudFormation.create(args: List<String>) {
        s3Website {}
    }
}
