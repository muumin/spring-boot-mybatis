package example.web

import example.BaseSpecification
import spock.lang.Unroll
import wslite.rest.RESTClient

class UserControllerTest extends BaseSpecification {

    def "create()をパラメーター無し"() {
        setup:
        def messages = ['mailは必須です', 'loginIdは必須です', 'nameは必須です', 'passwordは必須です', 'userTypeは必須です']

        when:
        def response = new RESTClient("http://localhost:8080").post(path: "/users/", query: [], {})

        then:
        response.json.status == "ERROR"
        response.json.messages.size() == messages.size()
        response.json.messages.each {
            assert messages.contains(it)
        }
    }


    @Unroll
    def "create() #comment"() {
        setup:
        def query = [loginId: loginId, mail: mail, name: name, password: password, userType: userType]

        when:
        def response = new RESTClient("http://localhost:8080").post(path: "/users/", query: query, {})

        then:
        response.json.status == "ERROR"
        response.json.messages.size() == 1
        response.json.messages.get(0) == message

        where:
        // @formatter:off
        comment               | message                          | loginId        | mail               | name        | password | userType
        'loginIdが空文字'     | 'loginIdは1以上100以下です'      | ''             | 'test@example.com' | '山田浩二'  | 'TEST3'  | '8'
        'loginIdが101文字'    | 'loginIdは1以上100以下です'      | 'a' * 101      | 'test@example.com' | '山田浩二'  | 'TEST3'  | '8'
        'loginIdが英数字以外' | 'loginIdは英数字です'            | 'a_a101'       | 'test@example.com' | '山田浩二'  | 'TEST3'  | '8'
        // @formatter:on
    }
}
