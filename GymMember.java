/**
 * Root class for GYM management system 
 * Sub classes common attributes with protected access modifier
 * 
 *@author Bhumika Rayamajhi
 *@version 2.0
 */
public abstract class GymMember {
    // Protected attributes accessible by child classes
    protected int id;                     // Unique identifier for each member
    protected String name;                // Member's full name
    protected String location;            // Member's location/address
    protected String phone;               // Member's phone number
    protected String email;               // Member's email address
    protected String gender;              // Member's gender (Male/Female)
    protected String DOB;                 // Member's date of birth (YYYY-MM-DD)
    protected String membershipStartDate; // Date when membership started (YYYY-MM-DD)
    protected int attendance;             // Number of times member has attended
    protected double loyaltyPoints;       // Loyalty points accumulated by member
    protected boolean activeStatus;       // Whether membership is active or not
    
    /**
     * Constructor for GymMember
     * Initializes all member attributes with provided values
     * Sets attendance and loyalty points to 0, and activeStatus to false by default
     * 
     * @param id Member ID (unique)
     * @param name Member's full name
     * @param location Member's location/address
     * @param phone Member's phone number
     * @param email Member's email address
     * @param gender Member's gender
     * @param DOB Member's date of birth
     * @param startDate Membership start date
     */
    public GymMember(int id, String name, String location, String phone, 
                     String email, String gender, String DOB, String startDate) {
        // Simple validations
        if (id <= 0) throw new IllegalArgumentException("ID must be positive");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (phone == null || phone.trim().isEmpty()) throw new IllegalArgumentException("Phone cannot be empty");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
        
        // Assign values
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = startDate;
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.activeStatus = false;
    }

    /**
     * Abstract method to mark attendance
     * Must be implemented by child classes
     * Different member types may have different attendance rules
     */
    public abstract void markAttendance();

    /**
     * Method to activate membership
     * Sets activeStatus to true
     */
    public void activateMembership() {
        this.activeStatus = true;
    }

    /**
     * Method to deactivate membership
     * Sets activeStatus to false if currently active
     */
    public void deactivateMembership() {
        if (this.activeStatus) {
            this.activeStatus = false;
        }
    }

    /**
     * Method to reset member
     * Sets activeStatus to false, attendance to 0, and loyaltyPoints to 0
     * Used when reverting a member
     */
    public void resetMember() {
        this.activeStatus = false;
        this.attendance = 0;
        this.loyaltyPoints = 0;
    }

    // Getter methods for all attributes
    /**
     * @return Member ID
     */
    public int getId() { return id; }
    
    /**
     * @return Member's name
     */
    public String getName() { return name; }
    
    /**
     * @return Member's location
     */
    public String getLocation() { return location; }
    
    /**
     * @return Member's phone number
     */
    public String getPhone() { return phone; }
    
    /**
     * @return Member's email
     */
    public String getEmail() { return email; }
    
    /**
     * @return Member's gender
     */
    public String getGender() { return gender; }
    
    /**
     * @return Member's date of birth
     */
    public String getDOB() { return DOB; }
    
    /**
     * @return Membership start date
     */
    public String getMembershipStartDate() { return membershipStartDate; }
    
    /**
     * @return Number of attendances
     */
    public int getAttendance() { return attendance; }
    
    /**
     * @return Loyalty points
     */
    public double getLoyaltyPoints() { return loyaltyPoints; }
    
    /**
     * @return Active status (true if active, false if inactive)
     */
    public boolean getActiveStatus() { return activeStatus; }

    /**
     * Method to display member information
     * @return A formatted string with member details
     */
    public String getDisplayInfo() {
        return "Member ID: " + id + "\n" +
               "Name: " + name + "\n" +
               "Location: " + location + "\n" +
               "Phone: " + phone + "\n" +
               "Email: " + email + "\n" +
               "Gender: " + gender + "\n" +
               "Date of Birth: " + DOB + "\n" +
               "Membership Start Date: " + membershipStartDate + "\n" +
               "Attendance: " + attendance + "\n" +
               "Loyalty Points: " + loyaltyPoints + "\n" +
               "Active Status: " + (activeStatus ? "Active" : "Inactive");
    }

    /**
     * Method to display member information to console
     */
    public void display() {
        System.out.println(getDisplayInfo());
    }
}
