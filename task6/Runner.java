package Annotations;

import java.lang.reflect.AnnotatedArrayType;

/**
 * Created by anastasia on 13.11.16.
 */
public class Runner {
    public static void main(String[] args){
        AccountManager manager = new AccountOperationManager();
        AccountManager securityAccount = SecurityFactory.createSecurityObject(manager);
        securityAccount.depositInCash(1024, 234.78);
        securityAccount.convert(123.1);
        securityAccount.transfer(1345, 120);
        securityAccount.withdraw(5489, 3988.0);

    }
}
