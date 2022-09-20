package site.metacoding.red.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import site.metacoding.red.handler.HelloInterceptor;
import site.metacoding.red.handler.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 어떤 주소일때 이 인터셉터가 동작하는지
		// **은 s/뒤의 모든 주소 ||| *은 s/boards(여기까지)/매칭안됨
		// + 패스는 여러개를 추가할 수 있다.
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/s/**");
		registry.addInterceptor(new HelloInterceptor()).addPathPatterns("/**");
		//.addPathPatterns("/admin/**").excludePathPatterns("/s/boards/**");
		//add 더하기
		//exclude 제외하기

		// 이주소로 오면 로그인 인터셉터의 메서드가 실행된다
	}

}
