package example.web

import example.BaseSpecification
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class UserControllerTest extends BaseSpecification {

    def "read() のエラーテスト"() {
        when:
        def response = getRestClient().get(path: "/users/USER1")

        then:
        response.json.status == "WARNING"
        response.json.messages == ["対象データがありませんでした"]
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

    @Unroll
    def "create() のnull値エラーテスト"() {
        when:
        def response = getRestClient().post(path: "/users/", query: query, {})

        then:
        response.json.status == "ERROR"

        where:
        query << [
                [loginId: 'USER1', name: '山田健治', password: 'USER1', userType: 'ADMIN'],
                [mail: 'test@example.com', name: '山田健治', password: 'USER1', userType: 'ADMIN'],
                [mail: 'test@example.com', loginId: 'USER1', password: 'USER1', userType: 'ADMIN'],
                [mail: 'test@example.com', loginId: 'USER1', name: '山田健治', userType: 'ADMIN'],
                [mail: 'test@example.com', loginId: 'USER1', name: '山田健治', password: 'USER1'],
                [mail: 'test@example.com', loginId: 'USER1', name: '山田健治', password: 'USER1', userType: 'TEST']
        ]
    }

    def "create() のテスト"() {
        setup:
        def query = [mail: 'test@example.com', loginId: 'USER1', name: '山田健治', password: 'USER1', userType: 'ADMIN']

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

    def "update() の入力エラーテスト"() {
        when:
        def response = getRestClient().put(path: "/users/USER1", query: [name: '鈴木健治', mail: 'xx'], {})

        then:
        response.json.status == "ERROR"
    }

    def "update() の楽観的ロックエラーテスト"() {
        setup:
        def query = [name: '鈴木健治', version: "0"]

        when:
        def response = getRestClient().put(path: "/users/USER1", query: query, {})

        then:
        response.json.status == "ERROR"
        response.json.messages == ["対象データが更新済みです"]
    }

    def "delete() のテスト"() {
        when:
        def response = getRestClient().delete(path: "/users/USER1")

        then:
        response.json.status == "SUCCESS"

        when:
        def response2 = getRestClient().get(path: "/users/USER1")

        then:
        response2.json.status == "WARNING"
        response2.json.messages == ["対象データがありませんでした"]
    }
}
