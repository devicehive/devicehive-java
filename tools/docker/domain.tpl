<domain log-root="${com.sun.aas.instanceRoot}/logs" application-root="${com.sun.aas.instanceRoot}/applications" version="13">
  <security-configurations>
    <authentication-service default="true" name="adminAuth" use-password-credential="true">
      <security-provider name="spcrealm" type="LoginModule" provider-name="adminSpc">
        <login-module-config name="adminSpecialLM" control-flag="sufficient" module-class="com.sun.enterprise.admin.util.AdminLoginModule">
          <property name="config" value="server-config"></property>
          <property name="auth-realm" value="admin-realm"></property>
        </login-module-config>
      </security-provider>
      <security-provider name="filerealm" type="LoginModule" provider-name="adminFile">
        <login-module-config name="adminFileLM" control-flag="sufficient" module-class="com.sun.enterprise.security.auth.login.FileLoginModule">
          <property name="config" value="server-config"></property>
          <property name="auth-realm" value="admin-realm"></property>
        </login-module-config>
      </security-provider>
    </authentication-service>
    <authorization-service default="true" name="authorizationService">
      <security-provider name="simpleAuthorization" type="Simple" provider-name="simpleAuthorizationProvider">
        <authorization-provider-config support-policy-deploy="false" name="simpleAuthorizationProviderConfig"></authorization-provider-config>
      </security-provider>
    </authorization-service>
  </security-configurations>
  <managed-job-config></managed-job-config>
  <system-applications>
    <application context-root="" location="${com.sun.aas.installRootURI}/lib/install/applications/__admingui" name="__admingui" directory-deployed="true" object-type="system-admin">
      <module name="__admingui">
        <engine sniffer="web"></engine>
        <engine sniffer="security"></engine>
      </module>
    </application>
  </system-applications>
  <resources>
    <jdbc-resource pool-name="__TimerPool" jndi-name="jdbc/__TimerPool" object-type="system-admin"></jdbc-resource>
    <jdbc-resource pool-name="DerbyPool" jndi-name="jdbc/__default" object-type="system-all"></jdbc-resource>
    <jdbc-connection-pool datasource-classname="org.apache.derby.jdbc.EmbeddedXADataSource" res-type="javax.sql.XADataSource" name="__TimerPool">
      <property name="databaseName" value="${com.sun.aas.instanceRoot}/lib/databases/ejbtimer"></property>
      <property name="connectionAttributes" value=";create=true"></property>
    </jdbc-connection-pool>
    <jdbc-connection-pool is-isolation-level-guaranteed="false" datasource-classname="org.apache.derby.jdbc.ClientDataSource" res-type="javax.sql.DataSource" name="DerbyPool">
      <property name="PortNumber" value="1527"></property>
      <property name="Password" value="APP"></property>
      <property name="User" value="APP"></property>
      <property name="serverName" value="localhost"></property>
      <property name="DatabaseName" value="sun-appserv-samples"></property>
      <property name="connectionAttributes" value=";create=true"></property>
    </jdbc-connection-pool>
    <connector-connection-pool max-pool-size="250" steady-pool-size="1" name="jms/__defaultConnectionFactory-Connection-Pool" resource-adapter-name="jmsra" connection-definition-name="javax.jms.ConnectionFactory"></connector-connection-pool>
    <connector-resource pool-name="jms/__defaultConnectionFactory-Connection-Pool" jndi-name="jms/__defaultConnectionFactory" object-type="system-all-req"></connector-resource>
    <context-service jndi-name="concurrent/__defaultContextService" object-type="system-all"></context-service>
    <managed-executor-service jndi-name="concurrent/__defaultManagedExecutorService" object-type="system-all"></managed-executor-service>
    <managed-scheduled-executor-service jndi-name="concurrent/__defaultManagedScheduledExecutorService" object-type="system-all"></managed-scheduled-executor-service>
    <managed-thread-factory jndi-name="concurrent/__defaultManagedThreadFactory" object-type="system-all"></managed-thread-factory>
    <jdbc-connection-pool datasource-classname="org.postgresql.ds.PGConnectionPoolDataSource" res-type="javax.sql.ConnectionPoolDataSource" name="DeviceHivePool" transaction-isolation-level="read-committed">
      <property name="User" value="{PG_USER}"></property>
      <property name="DatabaseName" value="{PG_DATABASE}"></property>
      <property name="Password" value="{PG_PASSWORD}"></property>
      <property name="ServerName" value="{PG_HOST}"></property>
      <property name="Url" value="jdbc:postgresql://{PG_HOST}:{PG_PORT}/{PG_DATABASE}?loginTimeout=0&amp;socketTimeout=0&amp;prepareThreshold=5&amp;unknownLength=2147483647&amp;tcpKeepAlive=false&amp;binaryTransfer=true&amp;disableColumnSanitiser=false"></property>
      <property name="ReceiveBufferSize" value="-1"></property>
      <property name="TcpKeepAlive" value="false"></property>
      <property name="PortNumber" value="0"></property>
      <property name="LoginTimeout" value="0"></property>
      <property name="PrepareThreshold" value="5"></property>
      <property name="LogLevel" value="0"></property>
      <property name="BinaryTransfer" value="true"></property>
      <property name="Ssl" value="false"></property>
      <property name="ProtocolVersion" value="0"></property>
      <property name="SendBufferSize" value="-1"></property>
      <property name="SocketTimeout" value="0"></property>
      <property name="UnknownLength" value="2147483647"></property>
    </jdbc-connection-pool>
    <jdbc-resource pool-name="DeviceHivePool" jndi-name="jdbc/DeviceHiveDataSource"></jdbc-resource>
    <managed-executor-service jndi-name="concurrent/DeviceHiveWaitService"></managed-executor-service>
    <managed-executor-service jndi-name="concurrent/DeviceHiveMessageService"></managed-executor-service>
  </resources>
  <servers>
    <server name="server" config-ref="server-config">
      <application-ref ref="__admingui" virtual-servers="__asadmin"></application-ref>
      <resource-ref ref="jdbc/__TimerPool"></resource-ref>
      <resource-ref ref="jdbc/__default"></resource-ref>
      <resource-ref ref="jms/__defaultConnectionFactory"></resource-ref>
      <resource-ref ref="concurrent/__defaultContextService"></resource-ref>
      <resource-ref ref="concurrent/__defaultManagedExecutorService"></resource-ref>
      <resource-ref ref="concurrent/__defaultManagedScheduledExecutorService"></resource-ref>
      <resource-ref ref="concurrent/__defaultManagedThreadFactory"></resource-ref>
      <resource-ref ref="jdbc/DeviceHiveDataSource"></resource-ref>
      <resource-ref ref="concurrent/DeviceHiveWaitService"></resource-ref>
      <resource-ref ref="concurrent/DeviceHiveMessageService"></resource-ref>
    </server>
  </servers>
  <nodes>
    <node node-host="localhost" name="localhost-domain1" type="CONFIG" install-dir="${com.sun.aas.productRoot}"></node>
  </nodes>
  <configs>
    <config name="server-config">
      <system-property description="Port Number that JMS Service will listen for remote clients connection." name="JMS_PROVIDER_PORT" value="7676"></system-property>
      <http-service>
        <access-log></access-log>
        <virtual-server id="server" network-listeners="http-listener-1,http-listener-2"></virtual-server>
        <virtual-server id="__asadmin" network-listeners="admin-listener"></virtual-server>
      </http-service>
      <iiop-service>
        <orb use-thread-pool-ids="thread-pool-1"></orb>
        <iiop-listener port="3700" id="orb-listener-1" address="0.0.0.0" lazy-init="true"></iiop-listener>
        <iiop-listener port="3820" id="SSL" address="0.0.0.0" security-enabled="true">
          <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as"></ssl>
        </iiop-listener>
        <iiop-listener port="3920" id="SSL_MUTUALAUTH" address="0.0.0.0" security-enabled="true">
          <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as" client-auth-enabled="true"></ssl>
        </iiop-listener>
      </iiop-service>
      <admin-service system-jmx-connector-name="system" type="das-and-server">
        <jmx-connector port="8686" address="0.0.0.0" auth-realm-name="admin-realm" name="system">
          <ssl client-auth="want" classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as"></ssl>
        </jmx-connector>
        <property name="adminConsoleContextRoot" value="/admin"></property>
        <property name="adminConsoleDownloadLocation" value="${com.sun.aas.installRoot}/lib/install/applications/admingui.war"></property>
        <property name="ipsRoot" value="${com.sun.aas.installRoot}/.."></property>
        <das-config></das-config>
      </admin-service>
      <connector-service></connector-service>
      <transaction-service tx-log-dir="${com.sun.aas.instanceRoot}/logs"></transaction-service>
      <batch-runtime-configuration></batch-runtime-configuration>
      <jms-service default-jms-host="default_JMS_host" type="EMBEDDED">
        <jms-host port="${JMS_PROVIDER_PORT}" host="localhost" name="default_JMS_host"></jms-host>
      </jms-service>
      <rest-config></rest-config>
      <web-container>
        <session-config>
          <session-manager>
            <manager-properties></manager-properties>
            <store-properties></store-properties>
          </session-manager>
          <session-properties></session-properties>
        </session-config>
      </web-container>
      <ejb-container>
        <ejb-timer-service></ejb-timer-service>
      </ejb-container>
      <diagnostic-service></diagnostic-service>
      <security-service>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.file.FileRealm" name="admin-realm">
          <property name="file" value="${com.sun.aas.instanceRoot}/config/admin-keyfile"></property>
          <property name="jaas-context" value="fileRealm"></property>
        </auth-realm>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.file.FileRealm" name="file">
          <property name="file" value="${com.sun.aas.instanceRoot}/config/keyfile"></property>
          <property name="jaas-context" value="fileRealm"></property>
        </auth-realm>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.certificate.CertificateRealm" name="certificate"></auth-realm>
        <jacc-provider policy-provider="com.sun.enterprise.security.provider.PolicyWrapper" name="default" policy-configuration-factory-provider="com.sun.enterprise.security.provider.PolicyConfigurationFactoryImpl">
          <property name="repository" value="${com.sun.aas.instanceRoot}/generated/policy"></property>
        </jacc-provider>
        <jacc-provider policy-provider="com.sun.enterprise.security.jacc.provider.SimplePolicyProvider" name="simple" policy-configuration-factory-provider="com.sun.enterprise.security.jacc.provider.SimplePolicyConfigurationFactory"></jacc-provider>
        <audit-module classname="com.sun.enterprise.security.ee.Audit" name="default">
          <property name="auditOn" value="false"></property>
        </audit-module>
        <message-security-config auth-layer="SOAP">
          <provider-config provider-type="client" provider-id="XWS_ClientProvider" class-name="com.sun.xml.wss.provider.ClientSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="dynamic.username.password" value="false"></property>
            <property name="debug" value="false"></property>
          </provider-config>
          <provider-config provider-type="client" provider-id="ClientProvider" class-name="com.sun.xml.wss.provider.ClientSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="dynamic.username.password" value="false"></property>
            <property name="debug" value="false"></property>
            <property name="security.config" value="${com.sun.aas.instanceRoot}/config/wss-server-config-1.0.xml"></property>
          </provider-config>
          <provider-config provider-type="server" provider-id="XWS_ServerProvider" class-name="com.sun.xml.wss.provider.ServerSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="debug" value="false"></property>
          </provider-config>
          <provider-config provider-type="server" provider-id="ServerProvider" class-name="com.sun.xml.wss.provider.ServerSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="debug" value="false"></property>
            <property name="security.config" value="${com.sun.aas.instanceRoot}/config/wss-server-config-1.0.xml"></property>
          </provider-config>
        </message-security-config>
        <message-security-config auth-layer="HttpServlet">
          <provider-config provider-type="server" provider-id="GFConsoleAuthModule" class-name="org.glassfish.admingui.common.security.AdminConsoleAuthModule">
            <request-policy auth-source="sender"></request-policy>
            <response-policy></response-policy>
            <property name="loginPage" value="/login.jsf"></property>
            <property name="loginErrorPage" value="/loginError.jsf"></property>
          </provider-config>
        </message-security-config>
        <property name="default-digest-algorithm" value="SHA-256"></property>
      </security-service>
      <java-config debug-options="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009" system-classpath="" classpath-suffix="">
        <jvm-options>-XX:MaxPermSize=192m</jvm-options>
        <jvm-options>-client</jvm-options>
        <jvm-options>-Djava.awt.headless=true</jvm-options>
        <jvm-options>-Djdk.corba.allowOutputStreamSubclass=true</jvm-options>
        <jvm-options>-Djavax.xml.accessExternalSchema=all</jvm-options>
        <jvm-options>-Djavax.management.builder.initial=com.sun.enterprise.v3.admin.AppServerMBeanServerBuilder</jvm-options>
        <jvm-options>-XX:+UnlockDiagnosticVMOptions</jvm-options>
        <jvm-options>-Djava.endorsed.dirs=${com.sun.aas.installRoot}/modules/endorsed${path.separator}${com.sun.aas.installRoot}/lib/endorsed</jvm-options>
        <jvm-options>-Djava.security.policy=${com.sun.aas.instanceRoot}/config/server.policy</jvm-options>
        <jvm-options>-Djava.security.auth.login.config=${com.sun.aas.instanceRoot}/config/login.conf</jvm-options>
        <jvm-options>-Dcom.sun.enterprise.security.httpsOutboundKeyAlias=s1as</jvm-options>
        <jvm-options>-Xmx512m</jvm-options>
        <jvm-options>-Djavax.net.ssl.keyStore=${com.sun.aas.instanceRoot}/config/keystore.jks</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStore=${com.sun.aas.instanceRoot}/config/cacerts.jks</jvm-options>
        <jvm-options>-Djava.ext.dirs=${com.sun.aas.javaRoot}/lib/ext${path.separator}${com.sun.aas.javaRoot}/jre/lib/ext${path.separator}${com.sun.aas.instanceRoot}/lib/ext</jvm-options>
        <jvm-options>-Djdbc.drivers=org.apache.derby.jdbc.ClientDriver</jvm-options>
        <jvm-options>-DANTLR_USE_DIRECT_CLASS_LOADING=true</jvm-options>
        <jvm-options>-Dcom.sun.enterprise.config.config_environment_factory_class=com.sun.enterprise.config.serverbeans.AppserverConfigEnvironmentFactory</jvm-options>
        <jvm-options>-Dorg.glassfish.additionalOSGiBundlesToStart=org.apache.felix.shell,org.apache.felix.gogo.runtime,org.apache.felix.gogo.shell,org.apache.felix.gogo.command,org.apache.felix.shell.remote,org.apache.felix.fileinstall</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.port=6666</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.maxconn=1</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.ip=127.0.0.1</jvm-options>
        <jvm-options>-Dgosh.args=--nointeractive</jvm-options>
        <jvm-options>-Dfelix.fileinstall.dir=${com.sun.aas.installRoot}/modules/autostart/</jvm-options>
        <jvm-options>-Dfelix.fileinstall.poll=5000</jvm-options>
        <jvm-options>-Dfelix.fileinstall.log.level=2</jvm-options>
        <jvm-options>-Dfelix.fileinstall.bundles.new.start=true</jvm-options>
        <jvm-options>-Dfelix.fileinstall.bundles.startTransient=true</jvm-options>
        <jvm-options>-Dfelix.fileinstall.disableConfigSave=false</jvm-options>
        <jvm-options>-XX:NewRatio=2</jvm-options>
        <jvm-options>-Dcom.ctc.wstx.returnNullForDefaultNamespace=true</jvm-options>
      </java-config>
      <network-config>
        <protocols>
          <protocol name="http-listener-1">
            <http default-virtual-server="server" max-connections="250">
              <file-cache></file-cache>
            </http>
          </protocol>
          <protocol security-enabled="true" name="http-listener-2">
            <http default-virtual-server="server" max-connections="250">
              <file-cache></file-cache>
            </http>
            <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" ssl3-enabled="false" cert-nickname="s1as"></ssl>
          </protocol>
          <protocol name="admin-listener">
            <http default-virtual-server="__asadmin" max-connections="250" encoded-slash-enabled="true">
              <file-cache></file-cache>
            </http>
          </protocol>
          <protocol security-enabled="true" name="sec-admin-listener">
            <http default-virtual-server="__asadmin" encoded-slash-enabled="true">
              <file-cache></file-cache>
            </http>
            <ssl client-auth="want" classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as"></ssl>
          </protocol>
          <protocol name="admin-http-redirect">
            <http-redirect secure="true"></http-redirect>
          </protocol>
          <protocol name="pu-protocol">
            <port-unification>
              <protocol-finder protocol="sec-admin-listener" name="http-finder" classname="org.glassfish.grizzly.config.portunif.HttpProtocolFinder"></protocol-finder>
              <protocol-finder protocol="admin-http-redirect" name="admin-http-redirect" classname="org.glassfish.grizzly.config.portunif.HttpProtocolFinder"></protocol-finder>
            </port-unification>
          </protocol>
        </protocols>
        <network-listeners>
          <network-listener port="8080" protocol="http-listener-1" transport="tcp" name="http-listener-1" thread-pool="http-thread-pool"></network-listener>
          <network-listener port="8181" protocol="http-listener-2" transport="tcp" name="http-listener-2" thread-pool="http-thread-pool"></network-listener>
          <network-listener port="4848" protocol="pu-protocol" transport="tcp" name="admin-listener" thread-pool="admin-thread-pool"></network-listener>
        </network-listeners>
        <transports>
          <transport name="tcp"></transport>
        </transports>
      </network-config>
      <thread-pools>
        <thread-pool name="admin-thread-pool" max-thread-pool-size="50" max-queue-size="256"></thread-pool>
        <thread-pool name="http-thread-pool"></thread-pool>
        <thread-pool name="thread-pool-1" max-thread-pool-size="200"></thread-pool>
      </thread-pools>
      <monitoring-service>
        <module-monitoring-levels></module-monitoring-levels>
      </monitoring-service>
      <group-management-service>
        <failure-detection></failure-detection>
      </group-management-service>
      <availability-service></availability-service>
    </config>
    <config name="default-config">
      <http-service>
        <access-log></access-log>
        <virtual-server id="server" network-listeners="http-listener-1, http-listener-2">
          <property name="default-web-xml" value="${com.sun.aas.instanceRoot}/config/default-web.xml"></property>
        </virtual-server>
        <virtual-server id="__asadmin" network-listeners="admin-listener"></virtual-server>
      </http-service>
      <iiop-service>
        <orb use-thread-pool-ids="thread-pool-1"></orb>
        <iiop-listener port="${IIOP_LISTENER_PORT}" id="orb-listener-1" address="0.0.0.0"></iiop-listener>
        <iiop-listener port="${IIOP_SSL_LISTENER_PORT}" id="SSL" address="0.0.0.0" security-enabled="true">
          <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as"></ssl>
        </iiop-listener>
        <iiop-listener port="${IIOP_SSL_MUTUALAUTH_PORT}" id="SSL_MUTUALAUTH" address="0.0.0.0" security-enabled="true">
          <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="s1as" client-auth-enabled="true"></ssl>
        </iiop-listener>
      </iiop-service>
      <admin-service system-jmx-connector-name="system">
        <jmx-connector port="${JMX_SYSTEM_CONNECTOR_PORT}" address="0.0.0.0" auth-realm-name="admin-realm" name="system">
          <ssl client-auth="want" classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="glassfish-instance"></ssl>
        </jmx-connector>
        <property name="adminConsoleDownloadLocation" value="${com.sun.aas.installRoot}/lib/install/applications/admingui.war"></property>
        <das-config></das-config>
      </admin-service>
      <web-container>
        <session-config>
          <session-manager>
            <manager-properties></manager-properties>
            <store-properties></store-properties>
          </session-manager>
          <session-properties></session-properties>
        </session-config>
      </web-container>
      <ejb-container>
        <ejb-timer-service></ejb-timer-service>
      </ejb-container>
      <mdb-container></mdb-container>
      <jms-service addresslist-behavior="priority" default-jms-host="default_JMS_host" type="EMBEDDED">
        <jms-host port="${JMS_PROVIDER_PORT}" host="localhost" name="default_JMS_host"></jms-host>
      </jms-service>
      <log-service log-rotation-limit-in-bytes="2000000" file="${com.sun.aas.instanceRoot}/logs/server.log">
        <module-log-levels></module-log-levels>
      </log-service>
      <security-service>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.file.FileRealm" name="admin-realm">
          <property name="file" value="${com.sun.aas.instanceRoot}/config/admin-keyfile"></property>
          <property name="jaas-context" value="fileRealm"></property>
        </auth-realm>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.file.FileRealm" name="file">
          <property name="file" value="${com.sun.aas.instanceRoot}/config/keyfile"></property>
          <property name="jaas-context" value="fileRealm"></property>
        </auth-realm>
        <auth-realm classname="com.sun.enterprise.security.auth.realm.certificate.CertificateRealm" name="certificate"></auth-realm>
        <jacc-provider policy-provider="com.sun.enterprise.security.provider.PolicyWrapper" name="default" policy-configuration-factory-provider="com.sun.enterprise.security.provider.PolicyConfigurationFactoryImpl">
          <property name="repository" value="${com.sun.aas.instanceRoot}/generated/policy"></property>
        </jacc-provider>
        <jacc-provider policy-provider="com.sun.enterprise.security.jacc.provider.SimplePolicyProvider" name="simple" policy-configuration-factory-provider="com.sun.enterprise.security.jacc.provider.SimplePolicyConfigurationFactory"></jacc-provider>
        <audit-module classname="com.sun.enterprise.security.ee.Audit" name="default">
          <property name="auditOn" value="false"></property>
        </audit-module>
        <message-security-config auth-layer="SOAP">
          <provider-config provider-type="client" provider-id="XWS_ClientProvider" class-name="com.sun.xml.wss.provider.ClientSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="dynamic.username.password" value="false"></property>
            <property name="debug" value="false"></property>
          </provider-config>
          <provider-config provider-type="client" provider-id="ClientProvider" class-name="com.sun.xml.wss.provider.ClientSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="dynamic.username.password" value="false"></property>
            <property name="debug" value="false"></property>
            <property name="security.config" value="${com.sun.aas.instanceRoot}/config/wss-server-config-1.0.xml"></property>
          </provider-config>
          <provider-config provider-type="server" provider-id="XWS_ServerProvider" class-name="com.sun.xml.wss.provider.ServerSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="debug" value="false"></property>
          </provider-config>
          <provider-config provider-type="server" provider-id="ServerProvider" class-name="com.sun.xml.wss.provider.ServerSecurityAuthModule">
            <request-policy auth-source="content"></request-policy>
            <response-policy auth-source="content"></response-policy>
            <property name="encryption.key.alias" value="s1as"></property>
            <property name="signature.key.alias" value="s1as"></property>
            <property name="debug" value="false"></property>
            <property name="security.config" value="${com.sun.aas.instanceRoot}/config/wss-server-config-1.0.xml"></property>
          </provider-config>
        </message-security-config>
      </security-service>
      <transaction-service tx-log-dir="${com.sun.aas.instanceRoot}/logs" automatic-recovery="true"></transaction-service>
      <diagnostic-service></diagnostic-service>
      <java-config debug-options="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=${JAVA_DEBUGGER_PORT}" system-classpath="" classpath-suffix="">
        <jvm-options>-XX:MaxPermSize=192m</jvm-options>
        <jvm-options>-server</jvm-options>
        <jvm-options>-Djava.awt.headless=true</jvm-options>
        <jvm-options>-Djdk.corba.allowOutputStreamSubclass=true</jvm-options>
        <jvm-options>-XX:+UnlockDiagnosticVMOptions</jvm-options>
        <jvm-options>-Djava.endorsed.dirs=${com.sun.aas.installRoot}/modules/endorsed${path.separator}${com.sun.aas.installRoot}/lib/endorsed</jvm-options>
        <jvm-options>-Djava.security.policy=${com.sun.aas.instanceRoot}/config/server.policy</jvm-options>
        <jvm-options>-Djava.security.auth.login.config=${com.sun.aas.instanceRoot}/config/login.conf</jvm-options>
        <jvm-options>-Dcom.sun.enterprise.security.httpsOutboundKeyAlias=s1as</jvm-options>
        <jvm-options>-Djavax.net.ssl.keyStore=${com.sun.aas.instanceRoot}/config/keystore.jks</jvm-options>
        <jvm-options>-Djavax.net.ssl.trustStore=${com.sun.aas.instanceRoot}/config/cacerts.jks</jvm-options>
        <jvm-options>-Djava.ext.dirs=${com.sun.aas.javaRoot}/lib/ext${path.separator}${com.sun.aas.javaRoot}/jre/lib/ext${path.separator}${com.sun.aas.instanceRoot}/lib/ext</jvm-options>
        <jvm-options>-Djdbc.drivers=org.apache.derby.jdbc.ClientDriver</jvm-options>
        <jvm-options>-DANTLR_USE_DIRECT_CLASS_LOADING=true</jvm-options>
        <jvm-options>-Dcom.sun.enterprise.config.config_environment_factory_class=com.sun.enterprise.config.serverbeans.AppserverConfigEnvironmentFactory</jvm-options>
        <jvm-options>-XX:NewRatio=2</jvm-options>
        <jvm-options>-Xmx512m</jvm-options>
        <jvm-options>-Dorg.glassfish.additionalOSGiBundlesToStart=org.apache.felix.shell,org.apache.felix.gogo.runtime,org.apache.felix.gogo.shell,org.apache.felix.gogo.command,org.apache.felix.fileinstall</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.port=${OSGI_SHELL_TELNET_PORT}</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.maxconn=1</jvm-options>
        <jvm-options>-Dosgi.shell.telnet.ip=127.0.0.1</jvm-options>
        <jvm-options>-Dgosh.args=--noshutdown -c noop=true</jvm-options>
        <jvm-options>-Dfelix.fileinstall.dir=${com.sun.aas.installRoot}/modules/autostart/</jvm-options>
        <jvm-options>-Dfelix.fileinstall.poll=5000</jvm-options>
        <jvm-options>-Dfelix.fileinstall.log.level=3</jvm-options>
        <jvm-options>-Dfelix.fileinstall.bundles.new.start=true</jvm-options>
        <jvm-options>-Dfelix.fileinstall.bundles.startTransient=true</jvm-options>
        <jvm-options>-Dfelix.fileinstall.disableConfigSave=false</jvm-options>
      </java-config>
      <availability-service>
        <web-container-availability></web-container-availability>
        <ejb-container-availability sfsb-store-pool-name="jdbc/hastore"></ejb-container-availability>
        <jms-availability></jms-availability>
      </availability-service>
      <network-config>
        <protocols>
          <protocol name="http-listener-1">
            <http default-virtual-server="server">
              <file-cache></file-cache>
            </http>
          </protocol>
          <protocol security-enabled="true" name="http-listener-2">
            <http default-virtual-server="server">
              <file-cache></file-cache>
            </http>
            <ssl classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" ssl3-enabled="false" cert-nickname="s1as"></ssl>
          </protocol>
          <protocol name="admin-listener">
            <http default-virtual-server="__asadmin" max-connections="250">
              <file-cache></file-cache>
            </http>
          </protocol>
          <protocol security-enabled="true" name="sec-admin-listener">
            <http default-virtual-server="__asadmin" encoded-slash-enabled="true">
              <file-cache></file-cache>
            </http>
            <ssl client-auth="want" classname="com.sun.enterprise.security.ssl.GlassfishSSLImpl" cert-nickname="glassfish-instance"></ssl>
          </protocol>
          <protocol name="admin-http-redirect">
            <http-redirect secure="true"></http-redirect>
          </protocol>
          <protocol name="pu-protocol">
            <port-unification>
              <protocol-finder protocol="sec-admin-listener" name="http-finder" classname="org.glassfish.grizzly.config.portunif.HttpProtocolFinder"></protocol-finder>
              <protocol-finder protocol="admin-http-redirect" name="admin-http-redirect" classname="org.glassfish.grizzly.config.portunif.HttpProtocolFinder"></protocol-finder>
            </port-unification>
          </protocol>
        </protocols>
        <network-listeners>
          <network-listener port="${HTTP_LISTENER_PORT}" protocol="http-listener-1" transport="tcp" name="http-listener-1" thread-pool="http-thread-pool"></network-listener>
          <network-listener port="${HTTP_SSL_LISTENER_PORT}" protocol="http-listener-2" transport="tcp" name="http-listener-2" thread-pool="http-thread-pool"></network-listener>
          <network-listener port="${ASADMIN_LISTENER_PORT}" protocol="pu-protocol" transport="tcp" name="admin-listener" thread-pool="http-thread-pool"></network-listener>
        </network-listeners>
        <transports>
          <transport name="tcp"></transport>
        </transports>
      </network-config>
      <thread-pools>
        <thread-pool name="http-thread-pool"></thread-pool>
        <thread-pool max-thread-pool-size="200" name="thread-pool-1"></thread-pool>
        <thread-pool name="admin-thread-pool" max-thread-pool-size="50" max-queue-size="256"></thread-pool>
      </thread-pools>
      <group-management-service>
        <failure-detection></failure-detection>
      </group-management-service>
      <system-property description="Port Number that JMS Service will listen for remote clients connection." name="JMS_PROVIDER_PORT" value="27676"></system-property>
      <system-property name="ASADMIN_LISTENER_PORT" value="24848"></system-property>
      <system-property name="HTTP_LISTENER_PORT" value="28080"></system-property>
      <system-property name="HTTP_SSL_LISTENER_PORT" value="28181"></system-property>
      <system-property name="IIOP_LISTENER_PORT" value="23700"></system-property>
      <system-property name="IIOP_SSL_LISTENER_PORT" value="23820"></system-property>
      <system-property name="IIOP_SSL_MUTUALAUTH_PORT" value="23920"></system-property>
      <system-property name="JMX_SYSTEM_CONNECTOR_PORT" value="28686"></system-property>
      <system-property name="OSGI_SHELL_TELNET_PORT" value="26666"></system-property>
      <system-property name="JAVA_DEBUGGER_PORT" value="29009"></system-property>
      <monitoring-service>
        <module-monitoring-levels></module-monitoring-levels>
      </monitoring-service>
    </config>
  </configs>
  <property name="administrative.domain.name" value="domain1"></property>
  <secure-admin enabled="true" special-admin-indicator="718fe3ff-df18-49f8-84a0-3aeedb3250db">
    <secure-admin-principal dn="CN=localhost,OU=GlassFish,O=Oracle Corporation,L=Santa Clara,ST=California,C=US"></secure-admin-principal>
    <secure-admin-principal dn="CN=localhost-instance,OU=GlassFish,O=Oracle Corporation,L=Santa Clara,ST=California,C=US"></secure-admin-principal>
  </secure-admin>
  <clusters></clusters>
  <applications></applications>
</domain>
