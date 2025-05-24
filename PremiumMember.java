/**
 * Class representing a Premium Member
 * Extends GymMember abstract class
 * Contains specific attributes and methods for premium members
 * 
 * @author Bhumika Rayamajhi
 * @version 2.0
 */
public class PremiumMember extends GymMember {
    // Constant for premium charge
    public static final double PREMIUM_CHARGE = 50000.0;
    
    // Private attributes specific to PremiumMember
    private final double premiumCharge;    // Fixed charge for premium membership
    private String personalTrainer;        // Name of personal trainer
    private boolean isFullPayment;         // Whether full payment has been made
    private double paidAmount;             // Amount paid so far
    private double discountAmount;         // Discount amount (calculated if full payment)
    
    /**
     * Constructor for PremiumMember
     * Initializes all member attributes with provided values
     * Sets default values for PremiumMember-specific attributes
     * 
     * @param id Member ID (unique)
     * @param name Member's full name
     * @param location Member's location/address
     * @param phone Member's phone number
     * @param email Member's email address
     * @param gender Member's gender
     * @param DOB Member's date of birth
     * @param startDate Membership start date
     * @param personalTrainer Name of personal trainer
     */
    public PremiumMember(int id, String name, String location, String phone, 
                        String email, String gender, String DOB, 
                        String startDate, String personalTrainer) {
        // Call parent constructor to initialize common attributes
        super(id, name, location, phone, email, gender, DOB, startDate);
        this.premiumCharge = PREMIUM_CHARGE;  // Use the constant
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;        // Not fully paid by default
        this.paidAmount = 0;               // No payment made by default
        this.discountAmount = 0;           // No discount by default
    }
    
    /**
     * Method to mark attendance
     * Increments attendance by 1 and adds 10 loyalty points
     * Only works if membership is active
     */
    @Override
    public void markAttendance() {
        if (activeStatus) {
            this.attendance++;
            this.loyaltyPoints += 10;  // Premium members get more loyalty points
        }
    }
    
    /**
     * Method to pay due amount
     * Adds to paidAmount and checks if full payment has been made
     * Calculates discount if full payment
     * 
     * @param amount The amount to pay
     * @return A message indicating success or failure
     */
    public String payDueAmount(double amount) {
        // Check if already fully paid
        if (isFullPayment) {
            return "Payment is already complete!";
        }
        
        // Validate payment amount
        if (amount <= 0) {
            return "Invalid payment amount!";
        }
        
        // Check if payment exceeds due amount
        double remainingAmount = premiumCharge - paidAmount;
        if (amount > remainingAmount) {
            return "Payment amount exceeds the due amount of Rs. " + remainingAmount + "!";
        }
        
        // Process payment
        paidAmount += amount;
        
        // Check if full payment
        if (Math.abs(paidAmount - premiumCharge) < 0.01) { // Using epsilon comparison for floating point
            isFullPayment = true;
            calculateDiscount();  // Calculate discount on full payment
        }
        
        // Return success message with remaining amount
        remainingAmount = premiumCharge - paidAmount;
        return "Payment successful! Remaining amount: Rs. " + remainingAmount;
    }
    
    /**
     * Method to calculate discount
     * Calculates 10% discount if isFullPayment is true
     */
    public void calculateDiscount() {
        if (isFullPayment) {
            discountAmount = premiumCharge * 0.10;  // 10% discount on full payment
        }
    }
    
    /**
     * Method to revert premium member
     * Resets member and all premium-specific attributes
     */
    public void revertPremiumMember() {
        resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }
    
    // Getter methods for PremiumMember-specific attributes
    /**
     * @return Premium charge (fixed)
     */
    public double getPremiumCharge() { return premiumCharge; }
    
    /**
     * @return Name of personal trainer
     */
    public String getPersonalTrainer() { return personalTrainer; }
    
    /**
     * @return Whether full payment has been made
     */
    public boolean isFullPayment() { return isFullPayment; }
    
    /**
     * @return Amount paid so far
     */
    public double getPaidAmount() { return paidAmount; }
    
    /**
     * @return Discount amount
     */
    public double getDiscountAmount() { return discountAmount; }
    
    /**
     * Method to display member information
     * Calls super.display() and displays additional PremiumMember-specific information
     */
    @Override
    public void display() {
        super.display();
        System.out.println("Membership Type: Premium");
        System.out.println("Premium Charge: Rs. " + premiumCharge);
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: Rs. " + paidAmount);
        System.out.println("Payment Status: " + (isFullPayment ? "Complete" : "Incomplete"));
        if (isFullPayment) {
            System.out.println("Discount Amount: Rs. " + discountAmount);
        }
        System.out.println("Remaining Amount: Rs. " + (premiumCharge - paidAmount));
    }
}