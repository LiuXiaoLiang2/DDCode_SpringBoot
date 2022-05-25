package com.ddcode.rocketmq.trans;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务消息监听器
 */
public class TransactionListenerImpl implements TransactionListener {

    /**
     * 事务回调
     * @param message
     * @param param
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object param) {
        System.out.println("事务回调 msg : " + message + ", param ： " + param);
        String msg = new String(message.getBody());
        if("tag1".equals(message.getTags())){
            System.out.println("事务回调 msg : " + msg + ", 直接提交成功");
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if("tag2".equals(message.getTags())){
            System.out.println("事务回调 msg : " + msg + ", 直接回滚");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }  else if("tag3".equals(message.getTags())){
            System.out.println("事务回调 msg : " + msg + ", 直接回复未知");
            return LocalTransactionState.UNKNOW;
        }
        return null;
    }

    /**
     * 事务回查
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("事务回查 msg : " + messageExt);
        String msg = new String(messageExt.getBody());
        System.out.println("事务回查 msg : " + msg + ", 直接提交成功");
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
