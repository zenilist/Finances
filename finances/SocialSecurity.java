package finances;

public class SocialSecurity {
    double socialSecurityWithholding;
    static double totalSocialSecurityWithheld;
    private final double RATE = 6.2;

    public SocialSecurity(double grossPay) {
        this.socialSecurityWithholding = grossPay * RATE / 100;
    }

    public double getSocialSecurityWithholding() {
        return socialSecurityWithholding;
    }

    public static double getTotalSocialSecurityWithheld() {
        return totalSocialSecurityWithheld;
    }
}
