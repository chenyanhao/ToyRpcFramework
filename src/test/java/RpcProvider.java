/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

/**
 *
 * @author yj
 * @version $Id: RpcProvider.java, v 0.1 2018��08��09�� ����5:51 yj Exp $
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 8888);
    }

}