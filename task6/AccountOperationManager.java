package Annotations;

/**
 * Created by anastasia on 13.11.16.
 */
public class AccountOperationManager implements AccountManager{
    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.HIGH)
    public double depositInCash(int accountNumber, double sum){
        //System.out.print("dep\n");
        return 0;
    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.HIGH)
    public double withdraw(int accountNumber, double sum){
        //System.out.print("withd\n");
        return 0;
    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.LOW)
    public double convert(double sum){
        //System.out.print("conv\n");
        return sum;
    }

    @Override
    @BankingAnnotation(securityLevel = SecurityLevelEnum.LOW)
    public double transfer(int accountNumber, double sum){
        //System.out.print("trans\n");
        return 0;
    }
}
