package cn.hy.infoReport.common.rabbit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 消息队列配置
 */
@Component
@ConfigurationProperties(prefix = "rabbitmq.consumer")
public class RabbitMqConsumerProperties {

    /**
     * 生产者分支名
     */
    private String producerGitBranch;

    /**
     * 交换机名称
     */
    private String pmsExchangeName;

    /**
     * pms routingKey
     */
    private String pmsRoutingKey;

    /**
     * pms 队列名称
     */
    private String pmsQueueName;

    /**
     * 学生队列routingKey前缀
     */
    private String studentRoutingKeyPrefix;

    /**
     * 教职工队列routingKey前缀
     */
    private String staffRoutingKeyPrefix;

    /**
     * 班级队列routingKey前缀
     */
    private String classRoutingKeyPrefix;

    public String getProducerGitBranch() {
        return producerGitBranch;
    }

    public void setProducerGitBranch(String producerGitBranch) {
        this.producerGitBranch = producerGitBranch;
    }

    public String getPmsExchangeName() {
        return pmsExchangeName;
    }

    public void setPmsExchangeName(String pmsExchangeName) {
        this.pmsExchangeName = pmsExchangeName;
    }

    public String getStudentRoutingKeyPrefix() {
        return studentRoutingKeyPrefix;
    }

    public void setStudentRoutingKeyPrefix(String studentRoutingKeyPrefix) {
        this.studentRoutingKeyPrefix = studentRoutingKeyPrefix;
    }

    public String getStaffRoutingKeyPrefix() {
        return staffRoutingKeyPrefix;
    }

    public void setStaffRoutingKeyPrefix(String staffRoutingKeyPrefix) {
        this.staffRoutingKeyPrefix = staffRoutingKeyPrefix;
    }

    public String getClassRoutingKeyPrefix() {
        return classRoutingKeyPrefix;
    }

    public void setClassRoutingKeyPrefix(String classRoutingKeyPrefix) {
        this.classRoutingKeyPrefix = classRoutingKeyPrefix;
    }

    public String getPmsRoutingKey() {
        return pmsRoutingKey;
    }

    public void setPmsRoutingKey(String pmsRoutingKey) {
        this.pmsRoutingKey = pmsRoutingKey;
    }

    public String getPmsQueueName() {
        return pmsQueueName;
    }

    public void setPmsQueueName(String pmsQueueName) {
        this.pmsQueueName = pmsQueueName;
    }
}
