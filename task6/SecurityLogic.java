package Annotations;

import java.lang.reflect.Method;

/**
 * Created by anastasia on 13.11.16.
 */
public class SecurityLogic {
    public void onInvoke(SecurityLevelEnum level, Method method, Object[] objs){
        StringBuilder builder = new StringBuilder();
        for (Object o: objs) {
            builder.append(o.toString() + ":");
        }
        //builder.setLength(objs.length - 1);
        String s = builder.toString();
        System.out.print(String.format("Method %s has invokes with parameters : %s", method.toString(), builder.toString()));

        switch (level) {
            case LOW:
                System.out.print("\nLow security level\n");
                break;
            case NORMAL:
                System.out.print("\nNormal security level\n");
                break;
            case HIGH:
                System.out.print("\nHigh security level\n");
                break;
        }
    }
}
