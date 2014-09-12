package specs

import com.howardlewisship.notx.modules.NoTxModule
import org.apache.tapestry5.hibernate.HibernateCoreModule
import org.apache.tapestry5.hibernate.HibernateModule
import org.apache.tapestry5.hibernate.HibernateSessionManager
import org.apache.tapestry5.internal.TapestryAppInitializer
import org.apache.tapestry5.ioc.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ServiceLevelSpec extends Specification {

    @Shared
    Registry registry

    def setupSpec() {
        Logger logger = LoggerFactory.getLogger(ServiceLevelSpec)
        TapestryAppInitializer init = new TapestryAppInitializer(logger, "app", "app")

        init.addModules(HibernateCoreModule, HibernateModule, NoTxModule)

        registry = init.createRegistry()
    }

    def cleanupSpec() {
        registry.shutdown()
        registry = null
    }

    def cleanup() {
        registry.cleanupThread()
    }

    @Unroll
    def "HibernateSessionManager.#methodNameStr will throw IllegalStateException"() {

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
