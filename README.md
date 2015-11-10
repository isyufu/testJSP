# testJSP

Предполагается что имеется заполненная БД либо ее создание/заполнение осуществляется другим приложением.
Проверялось на томкат  7.64 x64 и базе данных PostgreSQL 9.3

##Инструкция по развертыванию приложения на примере PostgreSQL.

1. (необязательный)В tomcat должно быть создан источник данных к БД.

  1.1. В conf/server.xml создать ресурс подключения к БД в ```<GlobalNamingResources>....</GlobalNamingResources>```.
  
  ```xml
  <Resource name="jdbc/postgres" global="jdbc/postgres" auth="Container"
    type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
    url="jdbc:postgresql://127.0.0.1:5432/postgres"
    username="postgres" password="postgres" maxActive="10" maxTotal="20" maxIdle="10" maxWaitMillis="10000"/>
  ```
  
  1.2. В conf/context.xml в разделе ```<Context> ... </Context>``` создать ссылку на глобальный ресурс.
  
  ```xml
  <ResourceLink name="jdbc/postgres"
    global="jdbc/postgres"
    auth="Container"
    type="javax.sql.DataSource" />
  ```
    
2. Создать 2 параметрa в conf/context.xml в разделе ```<Context> ... </Context>``` используемый диалект, и имя источника данных.
  
  ```xml
  <Environment name="webDialect" value="org.hibernate.dialect.PostgreSQLDialect" type="java.lang.String"/>
  <Environment name="webDataSource" value="java:comp/env/jdbc/postgres" type="java.lang.String"/>
  ```
  
3. Развернуть приложение на tomcat. Например скопировав web.war в webapps/.
