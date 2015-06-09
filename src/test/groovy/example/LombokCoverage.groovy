package example

import org.codehaus.groovy.reflection.CachedClass
import org.slf4j.Logger

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.time.LocalDate
import java.time.LocalDateTime

class LombokCoverage<T> {
    static final DEFAULT_VALUE = [
            "java.lang.String"       : "test",
            "java.lang.Integer"      : 1,
            "java.lang.Boolean"      : true,
            "java.time.LocalDate"    : LocalDate.of(2015, 6, 9),
            "java.time.LocalDateTime": LocalDateTime.of(2015, 6, 9, 0, 0, 0, 0),
    ]

    static final BUILDER_IGNORE_METHOD = ['equals', 'getClass', 'hashCode', 'notify',
                                          'notifyAll', 'toString', 'wait', 'wait',
                                          'wait', 'build', 'toString'
    ]

    Map<String, Object> paramMap

    LombokCoverage(Map<String, Object> paramMap = null) {
        this.paramMap = paramMap
    }

    /**
     * private static final Logger log をMock化する.
     */
    def setMockLogger(T target, Logger logger) {
        def field = target.getClass().getDeclaredField("log")
        field.setAccessible(true)
        def modifiers = Field.getDeclaredField("modifiers")
        modifiers.setAccessible(true)
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL)
        field.set(target, logger)
    }

    /**
     * Lombokの自動生成メソッドもカバレッジ対象なので目障りなのでそれをグリーンにする.<br />
     * 自動生成メソッドなので結果のチェックは行わずカバレッジを通すだけです。
     */
    def coverage(Class<T> clazz, T spyMock) {
        def clazz1 = clazz.newInstance()

        clazz1.toString()
        clazz1.hashCode()

        clazz1.equals(clazz1)
        clazz1.equals(null)
        clazz1.equals(spyMock)

        clazz1.metaClass.methods.each {
            if (it.getName() ==~ /^set[A-Z].*/) {
                def setter = it.getName()
                def value = getParameter(it.getParameterTypes(), setter.replaceAll(/^(set)(.)/) {
                    it[2].toLowerCase()
                })

                def clazz2 = clazz.newInstance()
                def clazz3 = clazz.newInstance()

                clazz2.invokeMethod(setter, null)
                clazz3.invokeMethod(setter, null)
                clazz2.equals(clazz3)
                clazz2.hashCode()

                clazz2.invokeMethod(setter, value)
                clazz2.equals(clazz3)
                clazz3.equals(clazz2)
                clazz2.hashCode()

                clazz3.invokeMethod(setter, value)
                clazz2.equals(clazz3)
            }

            if (it.getName() == "builder") {
                def builder = clazz1.builder()
                builder.toString()
                builder.metaClass.methods.each { it2 ->
                    if (!BUILDER_IGNORE_METHOD.contains(it2.getName())) {
                        def value = getParameter(it2.getParameterTypes(), it2.getName())
                        builder.invokeMethod(it2.getName(), value)
                    }
                }
                builder.build()
            }
        }
    }

    private Object getParameter(CachedClass[] parameterTypes, String paramName) {
        assert parameterTypes.size() == 1

        def name = parameterTypes[0].getName()
        def value = paramMap ? paramMap[paramName] : null
        return value ?: DEFAULT_VALUE[name]
    }
}