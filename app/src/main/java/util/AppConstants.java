package util;

/*
 * * Created by :
 * * Name : Md. Imran Hossain
 * * Date : 5/31/2018 at 11:54 PM
 * * Email : hossain.imran.cse11@gmail.com
 * *
 * * Last Edited by :
 * *
 * * Last Reviewed by :
 */
public class AppConstants {
    //Email Validation pattern
    public static final String EmailRegEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    public static final String UsernameRegEx = "\\p{L}+(?:[\\W_]\\p{L}+)*";

    //Fragments Tags
    public static final String Login_Fragment = "LoginFragment";
    public static final String SignUp_Fragment = "SignUpFragment";
    public static final String ForgotPassword_Fragment = "ForgotPasswordFragment";
    public static final String Soulmates_Fragment = "Soulmates_Fragment";
    public static final String Search_Soulmates_Fragment = "Search_Soulmates_Fragment";
    public static final String Notification_Fragment = "Notification_Fragment";
    public static final String Account_Fragment = "Account_Fragment";
}
