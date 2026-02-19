package dev.sujeet.splitwise;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SplitwiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);
        System.out.println("hellow world");
    }

}
