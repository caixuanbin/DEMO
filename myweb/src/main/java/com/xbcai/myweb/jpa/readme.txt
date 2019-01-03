本例子实现了Spring Data JPA操作数据库的的简单增删改查，这里使用了mysql数据库，其他的数据库只需要把驱动更改为对应的数据库就可以
1、在pom.xml引入spring-boot-starter-data-jpa、mysql-connector-java（这里引入了5.**版本的，高版本会报错，有可能是我本地的msql数据库安装的是5.**版本的缘故）
2、编写实体映射类com.xbcai.myweb.model.JpaUser让其继承AbstractPersistable实现了主键自动生成自动维护
3、编写操作数据库表的接口com.xbcai.myweb.jpa.SimpleUserRepository，让其继承CrudRepository类实现简单的增删改查，可以结合业务继承对应的jpa封装好的接口
4、在配置文件配置数据源application.properties，例如这里是mysql数据库
spring.datasource.url=jdbc:mysql://localhost:3306/springboot?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.jpa.database=mysql
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
5、在controller层类com.xbcai.myweb.controller.JpaController，将在第二步编写的接口注入进来，就可以操作数据库的信息了

注：在test下面也准备了一些测试类可供调试用com.xbcai.myweb.jpa.SimpleUserRepositoyTsts
