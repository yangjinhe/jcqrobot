package com.yjh.cqa.command;

import com.sobte.cqp.jcq.entity.CoolQ;
import com.sobte.cqp.jcq.message.CQCode;
import com.yjh.cqa.App;

/**
 * Created by yangjh on 2019/7/20.
 */
public abstract class BaseCommand {

    String JAVA_EXEC = App.JAVA_EXEC;
    String JAVA_HOME = App.JAVA_HOME;
    CoolQ CQ = App.CQ;
    CQCode CC = App.CC;

    public abstract void exec(long fromGroup, long fromQQ, String msg);
}
