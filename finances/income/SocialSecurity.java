package finances.income;

public class SocialSecurity {
    static double totalSocialSecurityWithheld;
    private static final double RATE = 6.2;
    private final double socialSecurityWithholding;

    public SocialSecurity(double grossPay) {
        this.socialSecurityWithholding = grossPay * RATE / 100;
        totalSocialSecurityWithheld += this.socialSecurityWithholding;
    }

    public double getSocialSecurityWithholding() {
        return socialSecurityWithholding;
    }

    public static double getTotalSocialSecurityWithheld() {
        return totalSocialSecurityWithheld;
    }
}
