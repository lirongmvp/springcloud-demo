package com.lirong.servicehi.ftp;

import org.apache.commons.lang3.StringUtils;

/**
 * Title: ClientProp <br>
 * Description: 连接标识的基础属性，用于{@link DownloadManager} 管理的{@link PoolManager}集合的区分, 通过该接口的实现作为key调用对应的Pool <br>
 * Date: 2018年09月18日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
public interface ClientProp {

    /**
     * 协议 ftp sftp http https
     *
     * @return ftp sftp http https
     */
    String protocol();

    /**
     * ip 当http 和https 时这个属性没有意义
     *
     * @return ip host
     */
    String ip();

    /**
     * 端口
     *
     * @return port
     */
    int port();

    /**
     * 所有者 暂时没使用
     *
     * @return 用户名
     */
    String owner();

    /**
     * 是否相同
     *
     * @param clientProp clientProp
     * @return boolean
     */
    default boolean sameAddress(ClientProp clientProp) {
        return StringUtils.equals(clientProp.protocol(), this.protocol())
                && StringUtils.equals(clientProp.ip(), this.ip()) && clientProp.port() == this.port();
    }
}
