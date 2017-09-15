# sso-shiro-cas
spring��ʹ��shiro+cas���õ����¼�����ϵͳ֮��ķ��ʣ�ÿ��ֻ��Ҫ��¼һ��

## ϵͳģ��˵��
1.  cas��  �����¼ģ�飬����ֱ���õ���cas����Ŀ���˵���ʽ����
2.  doc��   �ĵ�Ŀ¼�����������ݿ�������䣬���õ���MySQL5.0�����ݿ���Ϊdb_test  
3.  spring-node-1��   Ӧ��1
4.  spring-node-2��   Ӧ��2

  ����node1��node2���ǲ���spring + springMVC + mybatis ��ܣ�ʹ��maven����Ŀ����

## cas����˵��
1.���Ȳ��õ��ǲ����ݿ�ķ�ʽ��У���û���ݵģ���cas/WEB-INF/deployerConfigContext.xml�е�135�й������������
``` xml
<!-- ��������ļ��ܷ�ʽ������ʹ�õ���MD5���� -->
	<bean id="passwordEncoder"
      class="org.jasig.cas.authentication.handler.DefaultPasswordEncoder"
      c:encodingAlgorithm="MD5"
      p:characterEncoding="UTF-8" />

  <!-- ͨ�����ݿ���֤��ݣ�������Լ�ȥʵ�� -->
	<bean id="primaryAuthenticationHandler"
      class="com.distinct.cas.jdbc.QueryDatabaseAuthenticationHandler"
      p:dataSource-ref="dataSource"
      p:passwordEncoder-ref="passwordEncoder"
      p:sql="select password from t_user where account=? and status = 'active'" />
      
  <!-- ��������Դ -->
	 <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		  <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		  <property name="url" value="jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&amp;characterEncoding=utf8"></property>
		  <property name="username" value="root"></property>
		  <property name="password" value="123456"></property>  
  </bean>
```
  ����QueryDatabaseAuthenticationHandler��������Զ��幹���ģ���cas/WEB-INF/lib/cas-jdbc-1.0.0.jar���棬����Ȥ��ͬѧ���Է����뿴�£����ڼ������Ե�˵��
  1.  dataSource��    ����Դ������MySQL��������Ϣ
  2.  passwordEncoder��   ���ܷ�ʽ�������õ���MD5
  3.  sql��   sql��ѯ��䣬��������Ǹ����û�������˺Ų�ѯ������

#### ���Ͼ��ǵ����¼�������Ҫ����

## Ӧ��ϵͳ������node1
1. Ӧ��ϵͳ����shiro��Ȩ�޿��ƣ����Ҹ�cas����
2. ��/spring-node-1/src/main/resources/conf/shiro.properties �ļ���
``` properties
shiro.loginUrl=http://127.0.0.1:8080/cas/login?service=http://127.0.0.1:8081/node1/shiro-cas

shiro.logoutUrl=http://127.0.0.1:8080/cas/logout?service=http://127.0.0.1:8081/node1/shiro-cas

shiro.cas.serverUrlPrefix=http://127.0.0.1:8080/cas

shiro.cas.service=http://127.0.0.1:8081/node1/shiro-cas

shiro.failureUrl=/users/loginSuccess

shiro.successUrl=/users/loginSuccess

```
����shiro.loginUrl �� shiro.logoutUrl��ǰ����cas��֤�ĵ�ַ�������������Ӧ��ϵͳ�ĵ�ַ���������õķ�ʽ��Ϊ���ڷ������ǵ�Ӧ��ϵͳ��ʱ���ȵ�cas������֤�������֤�ɹ��ˣ�cas���ض���shiro.successUrl ����ʾ�ĵ�ַ

3.��/spring-node-1/src/main/resources/conf/shiro.xml �ļ���
``` xml
<!-- Shiro Filter -->
	<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
		<property name="securityManager" ref="securityManager" />
		<!-- �趨�û��ĵ�¼���ӣ�����Ϊcas��¼ҳ������ӵ�ַ�����ûص���ַ -->
		<property name="loginUrl" value="${shiro.loginUrl}" />
		<property name="filters">
			<map>
				<!-- ���casFilter��shiroFilter -->
				<entry key="casFilter" value-ref="casFilter" />
				<entry key="logoutFilter" value-ref="logoutFilter" />
			</map>
		</property>
		<property name="filterChainDefinitions">
			<value>
				/shiro-cas = casFilter
				/logout = logoutFilter
				/users/** = user
			</value>
		</property>
	</bean>

	<bean id="casFilter" class="org.apache.shiro.cas.CasFilter">
		<!-- ������֤����ʱ��ʧ��ҳ�� -->
		<property name="failureUrl" value="${shiro.failureUrl}" />
		<property name="successUrl" value="${shiro.successUrl}" />
	</bean>

	<bean id="logoutFilter" class="org.apache.shiro.web.filter.authc.LogoutFilter">
		<!-- ������֤����ʱ��ʧ��ҳ�� -->
		<property name="redirectUrl" value="${shiro.logoutUrl}" />
	</bean>

	<bean id="casRealm" class="com.spring.mybatis.realm.UserRealm">
		<!-- ��֤ͨ�����Ĭ�Ͻ�ɫ -->
		<property name="defaultRoles" value="ROLE_USER" />
		<!-- cas����˵�ַǰ׺ -->
		<property name="casServerUrlPrefix" value="${shiro.cas.serverUrlPrefix}" />
		<!-- Ӧ�÷����ַ����������cas�����Ʊ�� -->
		<property name="casService" value="${shiro.cas.service}" />
	</bean>

	<!-- Shiro's main business-tier object for web-enabled applications -->
	<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
		<property name="subjectFactory" ref="casSubjectFactory"></property>
		<property name="realm" ref="casRealm" />
	</bean>

	<bean id="casSubjectFactory" class="org.apache.shiro.cas.CasSubjectFactory"></bean>

	<bean
		class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
		<property name="securityManager" ref="securityManager" />
	</bean>

	<bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"></bean>
	<bean
		class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
		<property name="staticMethod"
			value="org.apache.shiro.SecurityUtils.setSecurityManager"></property>
		<property name="arguments" ref="securityManager"></property>
	</bean>
```
> ����shiroFilter�������Ҫ������Ҫ���ص�url������Ҫע����������shiro�����أ����ǻ���Ҫ����cas�Ĺ�������casFilter

> casRealm���������Ҫ�����Լ�ʵ�ֵģ���Ҫ����shiro��Ȩ����֤�����������˵������

1.  defaultRoles�� Ĭ�ϵĽ�ɫ
2.  casServerUrlPrefix��  cas��ַ
3.  casService��  ϵͳӦ�õ�ַ

������ǻ���Ҫ��/spring-node-1/src/main/webapp/WEB-INF/web.xml �ļ���������صĹ���������ȫ������
``` xml
<filter>
		<filter-name>shiroFilter</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>shiroFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
	
##  ϵͳ����
1.  �˿�˵����cas��8080��node1��8081��node2:8082����ҿ��Բ���maven�ṩ��tomcat7������������£�
``` xml
<plugin>
			<groupId>org.apache.tomcat.maven</groupId>
			<artifactId>tomcat7-maven-plugin</artifactId>
			<version>2.1</version>
			<configuration>
				<port>8081</port>
				<uriEncoding>UTF-8</uriEncoding>
				<server>tomcat7</server>
				<path>/node1</path>
			</configuration>
		</plugin>
```
	���������ã���������������Ҫ����tomcat�������ˣ��������ַ�ʽ
	
2.����ģ��ķ��ʵ�ַ
> cas��http://127.0.0.1:8080/cas

> node1��http://127.0.0.1:8081/node1

> node2��http://127.0.0.1:8082/node2

3.����ϵͳ
> ����  http://127.0.0.1:8081/node1/shiro-cas ������cas��֤

> �����û���  admin������ admin@2015����֤�ɹ��󽫻��ض���http://127.0.0.1:8081/node1//users/loginSuccess ��Ҳ����node1ϵͳ����ҳ������Ľڵ�2�������node2ϵͳ����ҳ����ᷢ�����ǲ���Ҫ��¼��node2ϵͳ���ܷ������е�ϵͳ��




