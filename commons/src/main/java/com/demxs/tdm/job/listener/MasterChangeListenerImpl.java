package com.demxs.tdm.job.listener;

import com.github.ltsopensource.core.cluster.Node;
import com.github.ltsopensource.core.listener.MasterChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * master节点更新监听
 * User: wuliepeng
 * Date: 2017-08-23
 * Time: 上午10:48
 */
public class MasterChangeListenerImpl implements MasterChangeListener {
    private static final Logger log = LoggerFactory.getLogger(MasterChangeListenerImpl.class);

    /**
     * master 为 master节点
     * isMaster 表示当前节点是不是master节点
     *
     * @param master
     * @param isMaster
     */
    @Override
    public void change(Node master, boolean isMaster) {
        // 一个节点组master节点变化后的处理 , 譬如我多个JobClient， 但是有些事情只想只有一个节点能做。
        if(isMaster){
            log.info("master节点变化为: {}",master.toFullString());
        }else{
            log.info("master节点变化为: {}",master.toFullString());
        }
    }
}
