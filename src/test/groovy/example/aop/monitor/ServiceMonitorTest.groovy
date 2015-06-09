package example.aop.monitor

import example.LombokCoverage
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.slf4j.Logger
import spock.lang.Specification

class ServiceMonitorTest extends Specification {
    void "logServiceAccess(ProceedingJoinPoint)"() {
        setup:
        def logger = Mock(Logger) {
            1 * isDebugEnabled() >> true
            1 * debug("Completed: {}.{} : {}ms", 'example.aop.monitor.ServiceMonitor', 'Test', _)
        }

        def monitor = new ServiceMonitor()
        new LombokCoverage<ServiceMonitor>().setMockLogger(monitor, logger)

        def point = Mock(ProceedingJoinPoint) {
            proceed() >> "OK"
            getTarget() >> monitor
            getSignature() >> Mock(Signature) { getName() >> "Test" }
        }

        expect:
        monitor.logServiceAccess(point) == "OK"
    }
}
