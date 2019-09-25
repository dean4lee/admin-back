package cn.inslee.adminback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("cn.inslee.adminback.model.dao")
@SpringBootApplication
@Slf4j
public class AdminBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBackApplication.class, args);
        System.out.println("start success");
    }

}
