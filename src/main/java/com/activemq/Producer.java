package com.activemq;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
    public static String URL="tcp://81.70.169.162:61616";
    public static String QUEUE_NAME="queue01";
    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂
        final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);

        //2.通过连接工厂获得connection并启动访问
        final Connection connection = factory.createConnection();
        connection.start();

        //3.创建会话 俩参数，第一个叫事务，第二个叫签收
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地，是队列还是主题
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息生产者
        final MessageProducer producer = session.createProducer(queue);

        //6.生产消息到队列里面
        for (int i=1;i<4;i++){

            //7.创建消息
            final TextMessage textMessage = session.createTextMessage("msg" + i);

            //8.通过消息生产者发布
            producer.send(textMessage);
        }

        //9.关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发布到mq完成");
    }
}
