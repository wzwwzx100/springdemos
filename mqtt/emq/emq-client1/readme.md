https://blog.csdn.net/lingshi210/article/details/52439050
https://blog.csdn.net/ywb201314/article/details/72830132
https://www.wang1314.com/doc/topic-1259214-1.html
https://www.ibm.com/support/knowledgecenter/zh/SSFKSJ_7.0.1/com.ibm.mq.amqtat.doc/tt60319_.htm
http://houjixin.blog.163.com/blog/static/3562841020155110107215/
https://blog.csdn.net/yezis/article/details/43644117
https://blog.toright.com/posts/4024/haproxy-%E6%95%B4%E5%90%88-ssltls-client-certificate-%E6%95%99%E5%AD%B8.html
https://blog.csdn.net/chengfei112233/article/details/78983026
http://www.cnblogs.com/lexiaofei/p/8398065.html

[error] Server access Error: sun.security.validator.ValidatorException:
PKIX path building failed:sun.security.provider.certpath.SunCertPathBuilderException:unable to find valid certification path to requested target

该错误是因为没有可信任的证书，解决方案：
导入ca证书到信任库：
keytool -importcert -keystore "D:\Java\jdk1.8.0_131\jre\lib\security\cacerts" -storepass changeit -alias mqttca -file d:/opt/ssl/ca/ca.crt
sudo keytool -importcert -keystore /etc/pki/java/cacerts -storepass changeit -alias mqttca -file /opt/ssl/ca.crt













