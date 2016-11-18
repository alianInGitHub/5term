package Annotations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by anastasia on 13.11.16.
 */
public class SecurityFactory {
    public static AccountManager createSecurityObject(Object targetObject){

        AccountManager m = (AccountManager) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), new SecurityInvocationHandler(targetObject));
        return m;
    }

    private static class SecurityInvocationHandler implements InvocationHandler{
        private Object targetObject = null;

        public SecurityInvocationHandler(Object target){
            this.targetObject = target;
        }
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            SecurityLogic logic = new SecurityLogic();
            Method realMethod = targetObject.getClass().getMethod(method.getName(), (Class []) method.getGenericParameterTypes());
            BankingAnnotation annotation = realMethod.getAnnotation(BankingAnnotation.class);
            if(annotation != null){
                try {
                    logic.onInvoke(annotation.securityLevel(), realMethod, objects);
                    return method.invoke(targetObject, objects);
                } catch (InvocationTargetException e){
                    System.out.print(annotation.securityLevel() + "\n");
                    throw e.getCause();
                }

            }
            else {
                throw new InvocationTargetException(null, "Method " + realMethod + "should be annotated\n");
                //return null;
            }

        }
    }
}
