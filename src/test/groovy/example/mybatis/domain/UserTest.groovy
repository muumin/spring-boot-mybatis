package example.mybatis.domain

import example.LombokCoverage
import example.mybatis.enums.UserType
import spock.lang.Specification

class UserTest extends Specification {
    def "Lombok code coverage"() {
        setup:
        def spyMock = GroovySpy(User, global: false) {
            canEqual(_) >> false
        }

        expect:
        new LombokCoverage<User>([userType: UserType.ADMIN])
                .coverage(User, spyMock)
    }
}
