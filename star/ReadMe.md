1.Spring Boot中集成Swagger UI的几个步骤
	a.添加依赖
	b.编写config(参考SwaggerConfig.java)
	c.增加接口和参数的自定义说明(参考UserController.java)
	d.访问: http://localhost:8080/swagger-ui.html
	
2.如何使用定时器
	a.启动类添加注解@EnableScheduling
	b.需要定时执行的方法上添加注解@Scheduled，并且配置定时器执行的频率