/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author yj
 * @version $Id: RpcFramework.java, v 0.1 2018年08月09日 下午5:26 yj Exp $
 */
public class RpcFramework {
    public static void export(final Object target, int port) throws Exception {
        if (target == null || port <= 0 || port > 65535) {
            throw new RuntimeException("illegal argument");
        }
        System.out.println(String.format("Export service %s on port %s", target.getClass().getName(), port));

        final ServerSocket server = new ServerSocket(port);
        while (true) {
            final Socket client = server.accept();
            new Thread(() -> {
                try {
                    ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();

                    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                    Method method = target.getClass().getMethod(methodName, parameterTypes);
                    Object result = method.invoke(target, arguments);
                    output.writeObject(result);

                    input.close();
                    output.close();
                    client.close();
                } catch (Exception e) {
                    // 略
                } finally {
                    // 关闭各种 IO 流，为了代码整齐，就在 try 中关闭
                }
            }).start();
        }
    }

    public static <T> T refer(Class<T> interfaceClass, final String host, final int port) {
        if (interfaceClass == null || !interfaceClass.isInterface() || host == null || host.length() == 0 || port <= 0 || port > 65535) {
            throw new RuntimeException("illegal argument");
        }
        System.out.println(String.format("Get remote service %s from server %s on port %s", interfaceClass.getClass().getName(), host, port));

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, (proxy, method, args) -> {
            Object result = new Object();
            try {
                Socket client = new Socket(host, port);
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);

                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                result = input.readObject();

                input.close();
                output.close();
                client.close();
            } catch (Exception e) {
                // 略
            } finally {
                // 关闭各种 IO 流，为了代码整齐，就在 try 中关闭
            }
            return result;
        });
    }

}













