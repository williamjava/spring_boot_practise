## Spring Boot中集成Swagger UI
	1.添加依赖；
	2.编写config(参考SwaggerConfig.java)；
	3.增加接口和参数的自定义说明(参考UserController.java)；
	4.访问: http://localhost:8080/swagger-ui.html
	
## SpringBoot中如何使用定时器
	1.启动类添加注解@EnableScheduling；
	2.需要定时执行的方法上添加注解@Scheduled，并且配置定时器执行的频率。
	
## SpringBoot中如何使用事件
	1.定义事件，继承ApplicationEvent类；
	2.监听事件，使用两个注解：@Async和@EventListener。
