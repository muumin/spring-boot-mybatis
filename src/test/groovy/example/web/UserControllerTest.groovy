package example.web

import example.BaseSpecification
import spock.lang.Unroll

class UserControllerTest extends BaseSpecification {

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
    def "create() のvalidationテスト(#comment)"() {
        setup:
        def query = [loginId: loginId, mail: mail, name: name, password: password, userType: userType]

        when:
        def response = getRestClient().post(path: "/users/", query: query, {})

        then:
        response.json.status == "ERROR"
        response.json.messages.size() == 1
        response.json.messages.get(0) == message

        where:
        // @formatter:off
        comment               | message                          | loginId        | mail               | name        | password      | userType
        'loginIdが空文字'     | 'loginIdは1以上100以下です'      | ''             | 'test@example.com' | '山田浩二'  | 'TESTERROR1'  | '8'
        'loginIdが101文字'    | 'loginIdは1以上100以下です'      | 'a' * 101      | 'test@example.com' | '山田浩二'  | 'TESTERROR2'  | '8'
        'loginIdが英数字以外' | 'loginIdは英数字です'            | 'a_a101'       | 'test@example.com' | '山田浩二'  | 'TESTERROR3'  | '8'
        // @formatter:on
    }

    def "read() のテスト"() {
        when:
        def response = getRestClient().get(path: "/users/TEST1")

        then:
        response.json.status == "SUCCESS"
        response.json.data.name == "山田太郎"
    }

    def "read() のエラーテスト"() {
        when:
        def response = getRestClient().get(path: "/users/XXXXXX")

        then:
        response.json.status == "ERROR"
    }
}
