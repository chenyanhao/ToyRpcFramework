/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

/**
 *
 * @author yj
 * @version $Id: HelloServiceImpl.java, v 0.1 2018��08��09�� ����5:51 yj Exp $
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return String.format("Hello %s", name);
    }
}