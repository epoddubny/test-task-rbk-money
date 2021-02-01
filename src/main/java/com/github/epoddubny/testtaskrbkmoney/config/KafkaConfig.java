package com.github.epoddubny.testtaskrbkmoney.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true")
@Configuration
@Import(KafkaAutoConfiguration.class)
public class KafkaConfig {
    @Value("${kafka.topics.transactions.name}")
    private String transactionsTopicName;
    @Value("${kafka.topics.transactions.partitions}")
    private Integer transactionsTopicPartitions;
    @Value("${kafka.topics.transactions.replication-factor}")
    private Short transactionsTopicReplicationFactor;

    @Value("${kafka.topics.reports.name}")
    private String reportsTopicName;
    @Value("${kafka.topics.reports.partitions}")
    private Integer reportsTopicPartitions;
    @Value("${kafka.topics.reports.replication-factor}")
    private Short reportsTopicReplicationFactor;

    @Bean
    public NewTopic transactionsTopic() {
        return new NewTopic(transactionsTopicName, transactionsTopicPartitions, transactionsTopicReplicationFactor);
    }

    @Bean
    public NewTopic reportsTopic() {
        return new NewTopic(reportsTopicName, reportsTopicPartitions, reportsTopicReplicationFactor);
    }
}
