package example.web.result

import example.LombokCoverage
import spock.lang.Specification

class BasicResultTest extends Specification {
    def "Status.valueOf()"() {
        expect:
        BasicResult.Status.SUCCESS == BasicResult.Status.valueOf("SUCCESS")
        BasicResult.Status.WARNING == BasicResult.Status.valueOf("WARNING")
        BasicResult.Status.ERROR == BasicResult.Status.valueOf("ERROR")
    }

    def "Lombok code coverage"() {
        setup:
        def spyMock = GroovySpy(BasicResult, global: false) {
            canEqual(_) >> false
        }

        expect:
        new LombokCoverage<BasicResult>([messages: ["test"], status: BasicResult.Status.SUCCESS])
                .coverage(BasicResult, spyMock)
    }
}
