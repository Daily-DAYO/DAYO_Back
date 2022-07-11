//package com.seoultech.dayo.batch;
//
//import com.seoultech.dayo.post.Post;
//import javax.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//
//  private final JobBuilderFactory jobBuilderFactory;
//  private final StepBuilderFactory stepBuilderFactory;
//  private final EntityManagerFactory entityManagerFactory;
//
////  @Bean
////  public Job ExampleJob() throws Exception {
////    return jobBuilderFactory.get("dayoPickJob")
////        .start(step())
////        .build();
////  }
////
////  @Bean
////  @JobScope
////  public Step step() throws Exception {
////    return stepBuilderFactory.get("step")
////        .<Post>chunk(10)
////        .reader(reader())
////        .processor(processor())
////        .writer(writer())
////        .build();
////  }
////
////  @Bean
////  @StepScope
////  public JpaPagingItemReader<Post> reader() throws Exception {
////
////    Map<String, Object> parameterValues = new HashMap<>();
////    parameterValues.put("amount", 10000);
////
////    return new JpaPagingItemReaderBuilder<Member>()
////        .pageSize(10)
////        .parameterValues(parameterValues)
////        .queryString("SELECT p FROM Member p WHERE p.amount >= :amount ORDER BY id ASC")
////        .entityManagerFactory(entityManagerFactory)
////        .name("JpaPagingItemReader")
////        .build();
////  }
////
////  @Bean
////  @StepScope
////  public ItemProcessor<Member, Member> processor() {
////
////    return new ItemProcessor<Member, Member>() {
////      @Override
////      public Member process(Member member) throws Exception {
////
////        //1000원 추가 적립
////        member.setAmount(member.getAmount() + 1000);
////
////        return member;
////      }
////    };
////  }
////
////  @Bean
////  @StepScope
////  public JpaItemWriter<Member> writer() {
////    return new JpaItemWriterBuilder<Member>()
////        .entityManagerFactory(entityManagerFactory)
////        .build();
////  }
//
//
//}
