# common-xss
req通过getParameter、getParameterValues获取值，json形式body，转成对象时，xss过滤

### 原理
使用StringEscapeUtils中的escapeHtml4来转换html的特殊字符，防止xss攻击

### 使用
~~~xml
    <dependency>
        <groupId>kim.hanjie.common</groupId>
        <artifactId>common-xss</artifactId>
        <version>1.0.0</version>
    </dependency>
~~~

### filter使用
~~~java
@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }
}
~~~


### XssStringJsonDeserializer使用
~~~java
@Configuration
public class JacksonConvertersConfiguration {
    @Bean
    @Primary
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        Jackson2ObjectMapperBuilder xmlMapper = builder.createXmlMapper(false);
        xmlMapper.serializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置 String的deserializer Type为XssStringJsonDeserializer
        builder.deserializerByType(String.class, new XssStringJsonDeserializer());
        return xmlMapper.build();
    }
}
~~~

### 例外
对于一些不需要xss处理的文本，如果富文本内容，则可以使用StringEscapeUtils.unescapeHtml4转回来  
对于向富文本这种，可以通过Jsoup.clean()方法来获取安全的富文本内容
~~~xml
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.13.1</version>
    </dependency>
~~~