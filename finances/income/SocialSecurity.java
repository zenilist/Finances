package finances.income;

public class SocialSecurity {
    static double totalSocialSecurityWithheld;
    private final double RATE = 6.2;
    double socialSecurityWithholding;

    public SocialSecurity(double grossPay) {
        this.socialSecurityWithholding = grossPay * RATE / 100;
        totalSocialSecurityWithheld += this.socialSecurityWithholding;
    }

    public SocialSecurity() {
    }

    public double getSocialSecurityWithholding() {
        return socialSecurityWithholding;
    }

    public static double getTotalSocialSecurityWithheld() {
        return totalSocialSecurityWithheld;
    }
}
