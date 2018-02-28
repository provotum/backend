package org.provotum.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class EvaluationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${evaluation.output}")
    private String evaluationOutput;


    public File getOutputFile() throws IOException {
        File outputFile = new File(this.evaluationOutput + "-" + Instant.now().getEpochSecond() + ".log");

        if (outputFile.isDirectory()) {
            throw new IllegalArgumentException("Evaluation timer file is a directory " + outputFile.getAbsolutePath());
        }

        if (! outputFile.exists()) {
            outputFile.createNewFile();
        } else {
            outputFile.delete();
            outputFile.createNewFile();
        }

        return outputFile;
    }
}
