package example.monitor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.slf4j.Logger
import spock.lang.Specification

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class ServiceMonitorTest extends Specification {
    void "logServiceAccess(ProceedingJoinPoint)"() {
        setup:
        def logger = Mock(Logger) {
            1 * isDebugEnabled() >> true
            1 * debug("Completed: {}.{} : {}ms", 'example.monitor.ServiceMonitor', 'Test', _)
        }

        def monitor = new ServiceMonitor()

        // private static finalを書き換え可能にする
        def field = ServiceMonitor.class.getDeclaredField("log");
        field.setAccessible(true);
        def modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(monitor, logger)

        def point = Mock(ProceedingJoinPoint) {
            proceed() >> "OK"
            getTarget() >> monitor
            getSignature() >> Mock(Signature) { getName() >> "Test" }
        }

        expect:
        monitor.logServiceAccess(point) == "OK"
    }
}
