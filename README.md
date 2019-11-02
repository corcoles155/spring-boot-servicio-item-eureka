# spring-boot-servicio-item-eureka
Servicio con Spring Boot 2.1.5 + Feign + Eureka + Hystrix + Config Server

Intervienen los repos:
- spring-boot-zuul-server
- spring-boot-servicio-productos-eureka
- spring-boot-servicio-item-eureka
- spring-boot-eureka-server
- spring-boot-servicio-config-server

Para actualizar componentes de Spring hay que marcarlos con la anotación @RefreshScope, añadir en el YAML la property management.endpoints.web.exposure. include: "*" y ejecutar curl -X POST http://localhost:8085/actuator/refresh
