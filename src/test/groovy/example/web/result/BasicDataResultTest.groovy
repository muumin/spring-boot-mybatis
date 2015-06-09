package example.web.result

import example.LombokCoverage
import example.mybatis.domain.User
import spock.lang.Specification

class BasicDataResultTest extends Specification {
    def "Lombok code coverage"() {
        setup:
        def spyMock = GroovySpy(BasicDataResult, global: false) {
            canEqual(_) >> false
        }

        expect:
        new LombokCoverage<BasicDataResult>([data: new User(), messages: ["test"], status: BasicResult.Status.SUCCESS])
                .coverage(BasicDataResult, spyMock)
    }
}
