import com.github.spy.sea.core.script.groovy.GroovyContext
import com.github.spy.sea.core.script.groovy.GroovyResult
import com.github.spy.sea.core.script.groovy.GroovyRule

class Hello implements GroovyRule {

    @Override
    GroovyResult run(GroovyContext context) {

        GroovyResult result = new GroovyResult()

        result.setSuccess(true)
        println(context.name);

        return result
    }
}