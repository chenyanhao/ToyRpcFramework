/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

/**
 *
 * @author yj
 * @version $Id: RpcConsumer.java, v 0.1 2018年08月09日 下午5:52 yj Exp $
 */
public class RpcConsumer {

    public static void main(String[] args) throws Exception {
        HelloService helloService = RpcFramework.refer(HelloService.class, "127.0.0.1", 8888);
        for (int i = 0; i < 5; ++i) {
            String hello = helloService.hello("World " + i);
            Thread.sleep(1000);
            System.out.println(hello);
        }
    }
}