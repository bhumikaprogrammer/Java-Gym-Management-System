/**
 * Class representing a Regular Member
 * Extends GymMember abstract class
 * Contains specific attributes and methods for regular members
 * 
 * @author Bhumika Rayamajhi
 * @version 2.0
 */
public class RegularMember extends GymMember {
    // Constants for plan prices
    public static final double BASIC_PRICE = 6500.0;
    public static final double STANDARD_PRICE = 12500.0;
    public static final double DELUXE_PRICE = 18500.0;
    
    // Private attributes specific to RegularMember
    private final int attendanceLimit;     // Attendance limit for upgrade eligibility
    private boolean isEligibleForUpgrade;  // Whether member is eligible for plan upgrade
    private String removalReason;          // Reason for membership removal
    private String referralSource;         // Source of referral
    private String plan;                   // Current plan (basic, standard, deluxe)
    private double price;                  // Price of current plan
    
    /**
     * Constructor for RegularMember
     * Initializes all member attributes with provided values
     * Sets default values for RegularMember-specific attributes
     * 
     * @param id Member ID (unique)
     * @param name Member's full name
     * @param location Member's location/address
     * @param phone Member's phone number
     * @param email Member's email address
     * @param gender Member's gender
     * @param DOB Member's date of birth
     * @param startDate Membership start date
     * @param referralSource Source of referral
     */
    public RegularMember(int id, String name, String location, String phone, 
                        String email, String gender, String DOB, 
                        String startDate, String referralSource) {
        // Call parent constructor to initialize common attributes
        super(id, name, location, phone, email, gender, DOB, startDate);
        this.referralSource = referralSource;
        this.attendanceLimit = 30;         // Set attendance limit to 30
        this.isEligibleForUpgrade = false; // Not eligible for upgrade by default
        this.plan = "basic";               // Default plan is basic
        this.price = 6500;                 // Default price for basic plan
        this.removalReason = "";           // No removal reason by default
    }
    
    /**
     * Method to mark attendance
     * Increments attendance by 1 and adds 5 loyalty points
     * Checks if eligible for upgrade based on attendance limit
     * Only works if membership is active
     */
    @Override
    public void markAttendance() {
        if (activeStatus) {
            this.attendance++;
            this.loyaltyPoints += 5;
            
            // Check if eligible for upgrade
            if (this.attendance >= attendanceLimit && !this.isEligibleForUpgrade) {
                this.isEligibleForUpgrade = true;
            }
        }
    }
    
    /**
     * Method to get plan price
     * Returns the price for a given plan
     * 
     * @param plan The plan name (basic, standard, deluxe)
     * @return The price of the plan, or -1 if invalid plan
     */
    private double getPlanPrice(String plan) {
        if (plan.equalsIgnoreCase("basic")) {
            return BASIC_PRICE;
        } else if (plan.equalsIgnoreCase("standard")) {
            return STANDARD_PRICE;
        } else if (plan.equalsIgnoreCase("deluxe")) {
            return DELUXE_PRICE;
        } else {
            return -1.0; // Invalid plan
        }
    }
    
    /**
     * Method to upgrade plan
     * Upgrades the member's plan if eligible
     * 
     * @param plan The new plan (basic, standard, or deluxe)
     * @return A message indicating success or failure
     */
    public String upgradePlan(String plan) {
        // Check if eligible for upgrade
        if (!isEligibleForUpgrade) {
            return "Not eligible for upgrade. Need at least " + attendanceLimit + " attendances.";
        }
        
        // Check if plan is valid and get its price
        double newPrice = getPlanPrice(plan);
        if (newPrice == -1.0) {
            return "Invalid plan selected. Choose basic, standard, or deluxe.";
        }
        
        // Check if same plan is selected
        if (this.plan.equalsIgnoreCase(plan)) {
            return "You are already on the " + plan + " plan.";
        }
        
        // Upgrade plan
        this.plan = plan.toLowerCase();
        this.price = newPrice;
        return "Plan upgraded successfully to " + plan + " for Rs. " + price;
    }
    
    /**
     * Method to revert regular member
     * Resets member and sets removal reason
     * 
     * @param reason The reason for removal
     */
    public void revertRegularMember(String reason) {
        resetMember();
        this.removalReason = reason;
        this.plan = "basic";
        this.price = 6500;
        this.isEligibleForUpgrade = false;
    }
    
    
    // Getter methods for RegularMember-specific attributes
    /**
     * @return Attendance limit for upgrade eligibility
     */
    public int getAttendanceLimit() { return attendanceLimit; }
    
    /**
     * @return Whether member is eligible for upgrade
     */
    public boolean getIsEligibleForUpgrade() { return isEligibleForUpgrade; }
    
    /**
     * @return Reason for membership removal
     */
    public String getRemovalReason() { return removalReason; }
    
    
    
    /**
     * @return Current plan
     */
    public String getPlan() { return plan; }
    
    /**
     * @return Price of current plan
     */
    public double getPrice() { return price; }
    
    /**
     * Gets the referral source for this member
     * 
     * @return The referral source
     */
    public String getReferralSource() {
        return referralSource;
    }
    
    /**
     * Method to display member information
     * Calls super.display() and displays additional RegularMember-specific information
     */
    @Override
    public void display() {
        super.display();
        System.out.println("Membership Type: Regular");
        System.out.println("Plan: " + plan);
        System.out.println("Price: Rs. " + price);
        System.out.println("Referral Source: " + referralSource);
        System.out.println("Attendance Limit: " + attendanceLimit);
        System.out.println("Eligible for Upgrade: " + isEligibleForUpgrade);
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}