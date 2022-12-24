package org.example;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableOpenApi
@Data
public class SwaggerConfiguration {

    @Bean
    public Docket webApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("⽤户端接⼝⽂档")
                .pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变ᰁ控制，线上关闭
                .enable(true)
                //配置api⽂档元信息
                .apiInfo(apiInfo())
                // 选择哪些接⼝作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example"))
                //正则匹配请求路径，并分配⾄当前分组
                .paths(PathSelectors.ant("/api/**"))
                  //正则匹配请求路径，并分配⾄当前分组，当前所有接⼝
                .paths(PathSelectors.any())
                .build()
                //新版swagger3.0配置
                .globalRequestParameters(getGlobalRequestParameters())
                .globalResponses(HttpMethod.POST,getGlobalResponseMessage());
    }


    /**
     * ⽣成全局通⽤参数, ⽀持配置多个响应参数
     * @return
     */
    private List<RequestParameter>
    getGlobalRequestParameters() {
        List<RequestParameter> parameters = new
                ArrayList<>();
        parameters.add(new
                RequestParameterBuilder()
                .name("token")
                .description("登录令牌")
                .in(ParameterType.HEADER)
                .query(q -> q.model(m ->
                        m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
                // parameters.add(new RequestParameterBuilder()
                // .name("version")
                // .description("版本号")
                // .required(true)
                // .in(ParameterType.HEADER)
                // .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                // .required(false)
                // .build());
        return parameters;
    }
    /**
     * ⽣成通⽤响应信息
     * @return
     */
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("4xx").description("请求错误，根据code和msg检查").build());
        return responseList;
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("javaLearning")
                .description("微服务接口文档")
                .contact(new Contact("yangLiu", "https://google.com", "leon846666@gmail.com"))
                .version("12")
                .build();
    }

    @Bean
    public Docket adminApiDoc(){
        return new
                Docket(DocumentationType.OAS_30)
                .groupName("管理端接⼝⽂档")
                .pathMapping("/")
        // 定义是否开启swagger，false为关 闭，可以通过变ᰁ控制，线上关闭
                .enable(true)
                //配置api⽂档元信息
                .apiInfo(apiInfo())
                // 选择哪些接⼝作为swagger的doc发布
                .select()

                .apis(RequestHandlerSelectors.basePackage("org.example"))
                                //正则匹配请求路径，并分配⾄当前分组
                                .paths(PathSelectors.ant("/admin/**"))
                                .build();
    }

}
