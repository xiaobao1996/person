package cn.hy.infoReport.common.rabbit;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.rabbit.enums.OperationType;
import cn.hy.infoReport.common.rabbit.vo.MsgVo;
import cn.hy.infoReport.common.service.PmsClassService;
import cn.hy.infoReport.common.service.PmsStaffService;
import cn.hy.infoReport.common.service.PmsStudentService;
import cn.hy.infoReport.common.task.ClassSyncTask;
import cn.hy.infoReport.common.task.StaffSyncTask;
import cn.hy.infoReport.common.task.StudentSyncTask;
import cn.hy.infoReport.common.utils.HttpClientUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * rabbitmq消费者
 */
@Component
@Slf4j
public class RabbitMqConsumer{

    @Autowired
    private PmsStaffService pmsStaffService;
    @Autowired
    private PmsStudentService pmsStudentService;
    @Autowired
    private PmsClassService pmsClassService;
    @Autowired
    private RabbitMqConsumerProperties rabbitMqConsumerProperties;
    @Autowired
    private ClassSyncTask classSyncTask;

    /**
     * 处理Pms教师，学生信息的增删改
     * @param message
     * @param channel
     */
    @RabbitListeners(
            value  = {
                    @RabbitListener(
                            bindings = @QueueBinding(
                                    value = @Queue(value = "${rabbitmq.consumer.pms-queue-name}", durable = "true"),
                                    exchange = @Exchange(value = "${rabbitmq.consumer.pms-exchange-name}", type = "topic"),
                                    key = "${rabbitmq.consumer.pms-routing-key}"
                            )
                    )
            }
    )
    @RabbitHandler
    public void handlePmsMsg(Message message, Channel channel) {
        if (CollectionUtils.isEmpty(ProjectConstant.pmsSchoolIdSet)) {
            return;
        }
        String content = new String(message.getBody());
        List<MsgVo> msgVoList = MsgVo.from(content);
        if (CollectionUtils.isEmpty(msgVoList)) {
            return;
        }

        log.warn("收到消息，{}", content);

        try {
            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            if (routingKey.startsWith(rabbitMqConsumerProperties.getStaffRoutingKeyPrefix())) {
                //处理教职工
                try {
                    StaffSyncTask.lock.lock();
                    pmsStaffService.syncByMsgList(msgVoList);
                } finally {
                    StaffSyncTask.lock.unlock();
                }

            } else if (routingKey.startsWith(rabbitMqConsumerProperties.getStudentRoutingKeyPrefix())) {
                //处理学生
                try {
                    StudentSyncTask.lock.lock();
                    pmsStudentService.syncByMsgList(msgVoList);
                } finally {
                    StudentSyncTask.lock.unlock();
                }
            } else if (routingKey.startsWith(rabbitMqConsumerProperties.getClassRoutingKeyPrefix())) {
                if (routingKey.endsWith(OperationType.UPDATE_ALL.code)) {
                    classSyncTask.sync();
                } else {
                    //处理班级
                    try {
                        ClassSyncTask.lock.lock();
                        pmsClassService.syncByMsgList(msgVoList);
                    } finally {
                        ClassSyncTask.lock.unlock();
                    }
                }

            }
        } catch (Exception e) {
            log.error("{}", e.toString());
        }

    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("Date", "2020-03-18");
        params.put("ClassID", "909");

        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "Hm_lpvt_b7059a211382ce34646ff542c97a028c=1584512573; Hm_lvt_b7059a211382ce34646ff542c97a028c=1584496467; .SCHOOLADMINAUTH=0B61A6364245BBCABB8467156F73849C483075BB3161904BF5043A3724FECB863756BA2A8D13B2EFBCEB101E1128D8EE1A0AE81DE9AE194627E25FF5E6D2C619B33D0F8D3065371F7D5D831FD75F4191A1A850F12D375EE3B59F88EF9E59D5553D1D36E3FF597ACC2B2D36B0BB1B0655848880FB432D9FFEE4C1ACDBE59BD18313FDEA021DDF0007DC609C478BCBA69C293F8B3416961D28A1ABA97884789DCBB33463092880F54B8F1880129EE70DD3; PageStyle=LH; areaID=16; schID=3334; schProperty=school; teacherAPPID=; teacherID=772; teacherType=4; userName=%e6%b5%8b%e8%af%95%e5%ad%a6%e6%a0%a1; ASP.NET_SessionId=jayeffdgqnhwa3vilfppxyno");

        String result = HttpClientUtils.postWithHeaders("http://cdc.lovicoco.com/schooladmin/Report/DataAshx/TmpStuList.ashx", params, header, "UTF-8");
        System.out.println(result);
    }
}
