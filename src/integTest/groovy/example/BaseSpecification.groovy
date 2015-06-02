package example

import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@WebIntegrationTest
@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader.class)
abstract class BaseSpecification  extends Specification {
}
