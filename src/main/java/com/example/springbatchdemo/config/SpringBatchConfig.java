package com.example.springbatchdemo.config;

import com.example.springbatchdemo.domain.Employee;
import com.example.springbatchdemo.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.Locale;


@EnableBatchProcessing
@Configuration
@Log4j2
@AllArgsConstructor
public class SpringBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobExecutionListener;
    private final EmpService empService;
    private DataSource dataSource;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("saveData")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    Step step() {
        return stepBuilderFactory.get("saveEmployees")
                .<Employee, Employee>chunk(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriterJdbc())
                .build();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<Employee> itemReader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("EmployeItemReader")
                .resource(new ClassPathResource("/data/emp.csv"))
                .strict(false)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("id", "first_name", "last_name", "email", "gender", "ip_address")
                .targetType(Employee.class)
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Employee> itemProcessor() {
        return employee -> {
            employee.setFirstName(employee.getFirstName().toUpperCase(Locale.ROOT));
            return employee;
        };
    }

    @Bean("repoWriter")
    public ItemWriter<Employee> itemWriter() {
        return list -> this.empService.saveListOfEmployees((Iterable<Employee>) list);
    }

    @Bean("jdbcWriter")
    public JdbcBatchItemWriter itemWriterJdbc() {
        return new JdbcBatchItemWriterBuilder<>()
                .dataSource(dataSource)
                .sql("insert into employee_table (email, first_name, gender, ip_address, last_name, id) values (:email, :firstName, :gender, :ipAddress, :lastName, :id)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

}
