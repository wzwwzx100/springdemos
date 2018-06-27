https://blog.csdn.net/lingshi210/article/details/52439050
https://blog.csdn.net/ywb201314/article/details/72830132
https://www.wang1314.com/doc/topic-1259214-1.html
https://www.ibm.com/support/knowledgecenter/zh/SSFKSJ_7.0.1/com.ibm.mq.amqtat.doc/tt60319_.htm
http://houjixin.blog.163.com/blog/static/3562841020155110107215/
https://blog.csdn.net/yezis/article/details/43644117



[error] Server access Error: sun.security.validator.ValidatorException:
PKIX path building failed:sun.security.provider.certpath.SunCertPathBuilderException:unable to find valid certification path to requested target

该错误是因为没有可信任的证书，解决方案：
导入ca证书到信任库：
keytool -importcert -keystore "D:\Java\jdk1.8.0_131\jre\lib\security\cacerts" -storepass changeit -alias mqttca -file d:/opt/ssl/ca.crt
sudo keytool -importcert -keystore /etc/pki/java/cacerts -storepass changeit -alias mqttca -file /opt/ssl/ca.crt

mosquitto-client1和mosquitto-client2配合使用，模拟mqtt的两个客户端，一个发布消息，一个订阅消息。
采用tls1.2加密通信。













