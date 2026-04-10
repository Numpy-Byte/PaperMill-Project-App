// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// This is a base class for all users of the system
// We use "abstract" because we don't create plain users - we create specific types
public abstract class User {
    // The ID of this user
    private int id;
    
    // The name of this user
    private String name;
    
    // The password for this user
    private String pw;

    // This creates a new user with their details
    public User(int id, String name, String pw) {
        // Save the ID
        this.id = id;
        // Save the name
        this.name = name;
        // Save the password
        this.pw = pw;
    }

    // This is a placeholder for updating user profile
    // Subclasses will define their own implementation
    public abstract void updateProfile();

    // Get the user ID
    public int getId() {return id;}
    
    // Get the user name
    public String getName() {return name;}
    
    // Get the password
    public String getPw() {return pw;}
}
