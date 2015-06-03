package example.web

import example.BaseSpecification

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
