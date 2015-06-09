package example

import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import wslite.rest.RESTClient

@WebIntegrationTest("server.port:7777")
@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
// @ActiveProfilesでtest/resources/application-integration.ymlの差分をapplication.ymlの値に上書きして稼働する
// DIでも@Profile("integration")とすることで判別可能
@ActiveProfiles("integration")
abstract class BaseSpecification extends Specification {
    def getRestClient() {
        def client = new RESTClient("http://localhost:7777")
        return client
    }
}
