import com.github.spy.sea.core.script.groovy.GroovyContext
import com.github.spy.sea.core.script.groovy.GroovyResult
import com.github.spy.sea.core.script.groovy.GroovyRule

class decision implements GroovyRule {

    @Override
    GroovyResult run(GroovyContext context) {
        String ret = "通过";

        if (context.age < 18) {
            ret = "不通过";
        } else if (context.age > 18 && context.age < 30) {
            ret = "通过";
        } else {
            ret = "不能决策";
        }


        GroovyResult result = new GroovyResult()
        result.setSuccess(true)
        result.setData(ret);

        return result
    }
}