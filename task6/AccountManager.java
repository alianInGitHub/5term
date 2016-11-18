package Annotations;

/**
 * Created by anastasia on 13.11.16.
 */
public interface AccountManager {
    double depositInCash(int accountNumber, double sum);
    double convert(double sum);
    double withdraw(int accountNumber, double sum);
    double transfer(int accountNumber, double sum);
}
