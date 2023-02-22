package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		Hibernate5Module hibernate5Module = new Hibernate5Module(); // hibernate5Module 를 사용하여 Lazy 로딩을 호출해서 정상적으로 프록시가 초기화 된, 데이터가 로딩된 애들만 반 ㄱ
		//hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true); // 사용하면 안되는 설정
		return new Hibernate5Module();
	}

}
