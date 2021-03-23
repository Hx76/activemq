package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Comsumer {
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

        //5.创建消费者
        final MessageConsumer consumer = session.createConsumer(queue);

        //6.获取消息
        while (true){
            TextMessage textMessage = (TextMessage) consumer.receive(10);
            if (textMessage!=null){
                System.out.println("接收到的消息："+textMessage.getText());
            }else {
                break;
            }
        }

        //7.一顿关闭
        consumer.close();
        session.close();
        connection.close();


    }
}
