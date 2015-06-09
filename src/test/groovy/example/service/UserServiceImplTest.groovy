package example.service

import example.BaseSpecification
import example.mybatis.domain.User
import example.web.result.BasicDataResult
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

class UserServiceImplTest extends BaseSpecification {
    @Autowired
    UserService service

    @Unroll
    def "getUser() loginId = #loginId"() {
        when:
        User user = service.getUser(loginId)

        then:
        user.name == name

        where:
        // @formatter:off
        loginId | name
        "TEST1" | "山田太郎"
        "TEST2" | "山田花子"
        // @formatter:on
    }
}
