package config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import entity.Customer;
import lombok.AllArgsConstructor;
import repository.CustomerRepo;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchconfig {
	
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	
	@Bean
	public FlatFileItemReader<Customer> reader(){
	
	FlatFileItemReader<Customer> itemReader= new FlatFileItemReader<>();
	 itemReader.setResource(new FileSystemResource("src/main/resources/customer.csv"));
	  itemReader.setName("CSV Reader");
	  itemReader.setLinesToSkip(1);
	  itemReader.setLineMapper(lineMapper());
	  
	  return itemReader;
}

	private LineMapper<Customer> lineMapper() {
		DefaultLineMapper<Customer> lineMapper= new DefaultLineMapper<>();
		
		DelimitedLineTokenizer delimitedlineTokenizer= new DelimitedLineTokenizer();
		delimitedlineTokenizer.setDelimiter(",");
		delimitedlineTokenizer.setStrict(false);
		delimitedlineTokenizer.setNames("id","firstName","lastName","email","gender","phoneNum","country","dob");
		
		BeanWrapperFieldSetMapper<Customer> fieldsetMapper= new BeanWrapperFieldSetMapper<>();
		fieldsetMapper.setTargetType(Customer.class);
		lineMapper.setFieldSetMapper(fieldsetMapper);
		lineMapper.setLineTokenizer(delimitedlineTokenizer);
		return lineMapper;
		
	}
	
	@Bean
	public CustomerItemProcessor processor() {
		return new CustomerItemProcessor();
	}
	
	@Bean
	public RepositoryItemWriter<Customer> writer(){
		
		RepositoryItemWriter<Customer> writer= new RepositoryItemWriter<>();
		writer.setRepository(customerRepo);
		writer.setMethodName("save");
		return writer;
		
	}
	
	@Bean
	public Step step() {
		return stepBuilderFactory.get("csv-step").<Customer,Customer>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
		
	}
	
	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("importCustomer")
				.flow(step()).end().build();
				
		
	}
	
	
	
	
}