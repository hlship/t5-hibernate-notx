package specs

import com.howardlewisship.notx.modules.NoTxModule
import org.apache.tapestry5.hibernate.HibernateCoreModule
import org.apache.tapestry5.hibernate.HibernateSessionManager
import org.apache.tapestry5.ioc.Registry
import org.apache.tapestry5.ioc.RegistryBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll


class NoTxtHibernateSessionManagerSpec extends Specification {

    @Shared
    Registry registry

    def setupSpec() {
        registry = RegistryBuilder.buildAndStartupRegistry(HibernateCoreModule, NoTxModule)
    }

    def cleanupSpec() {
        registry.shutdown()
        registry = null
    }

    def cleanup() {
        registry.cleanupThread()
    }

    @Unroll
    def "#methodNameStr will throw IllegalStateException"() {

        when:

        registry.getObject(HibernateSessionManager, null)."$methodName"()

        then:

        IllegalStateException e = thrown()

        e.message.contains methodNameStr
        e.message.contains "should not be invoked"

        where:

        methodName << ["abort", "commit"]
        methodNameStr = "${methodName}()"
    }
}
