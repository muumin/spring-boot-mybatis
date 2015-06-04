package example.web

import example.BaseSpecification
import spock.lang.Stepwise

@Stepwise
class UserControllerTest extends BaseSpecification {

    def "read() のエラーテスト"() {
        when:
        def response = getRestClient().get(path: "/users/USER1")

        then:
        response.json.status == "ERROR"
    }

    def "create()をパラメーター無し"() {
        setup:
        def messages = ['mailは必須です', 'loginIdは必須です', 'nameは必須です', 'passwordは必須です', 'userTypeは必須です']

        when:
        def response = getRestClient().post(path: "/users/", query: [], {})

        then:
        response.json.status == "ERROR"
        response.json.messages.size() == messages.size()
        response.json.messages.each {
            assert messages.contains(it)
        }
    }

    def "create() のテスト"() {
        setup:
        def query = [mail: 'test@example.com', loginId: 'USER1', name: '山田健治', password: 'USER1', userType: '8']

        when:
        def response = getRestClient().post(path: "/users/", query: query, {})

        then:
        response.json.status == "SUCCESS"
        response.json.data.id != null
        response.json.data.name == '山田健治'
    }

    def "read() のテスト"() {
        when:
        def response = getRestClient().get(path: "/users/USER1")

        then:
        response.json.status == "SUCCESS"
        response.json.data.name == "山田健治"
    }

    def "update() のテスト"() {
        setup:
        def query = [name: '鈴木健治']

        when:
        def response = getRestClient().put(path: "/users/USER1", query: query, {})

        then:
        response.json.status == "SUCCESS"

        and:
        response.json.data.name == '鈴木健治'
        response.json.data.version == 1

        and:
        response.json.data.mailAddress == 'test@example.com'
        response.json.data.loginId == 'USER1'
        response.json.data.userType == 'ADMIN'
    }

    def "delete() のテスト"() {
        when:
        def response = getRestClient().delete(path: "/users/USER1")

        then:
        response.json.status == "SUCCESS"

        when:
        def response2 = getRestClient().get(path: "/users/USER1")

        then:
        response2.json.status == "ERROR"
    }
}
