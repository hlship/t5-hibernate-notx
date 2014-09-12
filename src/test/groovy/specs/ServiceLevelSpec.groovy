package specs

import app.services.TweetDAO
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

    @Shared
    TweetDAO dao

    def setupSpec() {
        Logger logger = LoggerFactory.getLogger(ServiceLevelSpec)
        TapestryAppInitializer init = new TapestryAppInitializer(logger, "app", "app")

        init.addModules(HibernateCoreModule, HibernateModule, NoTxModule)

        registry = init.createRegistry()

        dao = registry.getService TweetDAO
    }

    def cleanupSpec() {
        registry.shutdown()
        registry = null
        dao = null
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

    def "can perform non-transactional operations"() {

        when:

        def tweets = dao.all

        then:

        noExceptionThrown()

        tweets != null
    }

    def "can perform transactional operations"() {

        setup:

        dao.deleteAll()

        when:

        dao.addNew "hlship", "This works fine!"

        registry.cleanupThread()

        def tweets = dao.all

        then:

        tweets.size() == 1
        tweets[0].message == "This works fine!"
    }
}
