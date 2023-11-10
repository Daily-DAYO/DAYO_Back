package com.seoultech.dayo.config;

import com.fasterxml.classmate.TypeResolver;
import com.seoultech.dayo.exception.dto.BadRequestFailResponse;
import com.seoultech.dayo.exception.dto.ForbiddenFailResponse;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.exception.dto.UnauthorizedFailResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api(TypeResolver typeResolver) {
    return new Docket(DocumentationType.OAS_30)
        .useDefaultResponseMessages(false)
        .additionalModels(
            typeResolver.resolve(NotFoundFailResponse.class),
            typeResolver.resolve(BadRequestFailResponse.class),
            typeResolver.resolve(ForbiddenFailResponse.class),
            typeResolver.resolve(UnauthorizedFailResponse.class)
        )
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.seoultech.dayo"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("DAYO Swagger")
        .description("DAYO swagger")
        .version("1.0")
        .build();
  }

}
