package example.mybatis.handler

import org.apache.ibatis.type.JdbcType
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime

class LocalDateTimeTypeHandlerTest extends Specification {
    def handler = new LocalDateTimeTypeHandler()

    @Shared
    def dateTime = LocalDateTime.of(2015, 6, 7, 12, 0, 0, 0)
    @Shared
    def timestamp = Timestamp.valueOf(dateTime)

    void "setNonNullParameter(PreparedStatement, int, LocalDateTime, JdbcType)"() {
        setup:
        def ps = Mock(PreparedStatement)

        when:
        handler.setNonNullParameter(ps, 1, dateTime, JdbcType.DATE)

        then:
        1 * ps.setTimestamp(1, timestamp)
    }

    @Unroll
    void "getNullableResult(ResultSet, String)"() {
        setup:
        def resultSet = Mock(ResultSet) {
            getTimestamp("TEST_TIME") >> ret
        }

        expect:
        handler.getNullableResult(resultSet, "TEST_TIME") == testTime

        where:
        ret       || testTime
        null      || null
        timestamp || dateTime
    }

    @Unroll
    void "getNullableResult(ResultSet, int)"() {
        setup:
        def resultSet = Mock(ResultSet) {
            getTimestamp(1) >> ret
        }

        expect:
        handler.getNullableResult(resultSet, 1) == testTime

        where:
        ret       || testTime
        null      || null
        timestamp || dateTime
    }

    void "getNullableResult(CallableStatement, index)"() {
        setup:
        def cs = Mock(CallableStatement) {
            getTimestamp(1) >> ret
        }

        expect:
        handler.getNullableResult(cs, 1) == testTime

        where:
        ret       || testTime
        null      || null
        timestamp || dateTime
    }
}

