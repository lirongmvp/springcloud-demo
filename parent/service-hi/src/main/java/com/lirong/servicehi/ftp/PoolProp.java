package com.lirong.servicehi.ftp;

/**
 * Title: PoolProp <br>
 * Description: PoolProp <br>
 * Date: 2018年09月12日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
public interface PoolProp {

    /**
     * 最大数量
     *
     * @return 最大数量
     */
    int maxTotal();

    /**
     * 最大
     *
     * @return
     */
    int maxIdle();

    int minIdle();

}
