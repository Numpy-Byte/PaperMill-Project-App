// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to save objects to file
import java.io.Serializable;

// This class stores login information for a user
// It helps verify who can log into the system
public class LoginCredentials implements Serializable {
    // The ID of the user
    private int userId;
    
    // The password for this user
    private String pw;
    
    // The type of user (like "admin", "supervisor", "officer")
    private String userType;

    // This creates new login credentials
    public LoginCredentials(int userId, String pw, String userType) {
        // Save the user ID
        this.userId = userId;
        // Save the password
        this.pw = pw;
        // Save the user type
        this.userType = userType;
    }

    // Get the user ID
    public int getUserId() {
        return userId;
    }

    // Get the password
    public String getPw() {
        return pw;
    }

    // Get the user type
    public String getUserType() {
        return userType;
    }
}
