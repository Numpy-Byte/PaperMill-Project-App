// This package contains all the classes for the paper mill application
package cse213.papermillapp;

// We need this to handle input/output errors
// IOException is thrown when file operations fail
import java.io.IOException;
// We need this to write objects to a file/stream
// ObjectOutputStream is the parent class for writing Java objects
import java.io.ObjectOutputStream;
// We need this for the OutputStream parent class
// OutputStream is a basic class for writing data
import java.io.OutputStream;

// This is a special version of ObjectOutputStream that allows appending to files
// Normally, ObjectOutputStream creates a new file header each time
// This class skips that header, so we can add more objects to an existing file
// We use this to add production records to ProductionRecord.bin without overwriting
public class AppendableObjectOutputStream extends ObjectOutputStream {

    // This creates a new AppendableObjectOutputStream
    // Constructor method that sets up the stream for appending data
    // It takes an output stream (like a file) to write to
    // We pass in an OutputStream object which could be a file stream
    // This constructor calls the parent ObjectOutputStream constructor
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        // Call the parent ObjectOutputStream constructor to set it up
        // super() calls the parent class's constructor method
        // This initializes the ObjectOutputStream with the provided stream
        // This allows us to use the parent class's write capabilities
        super(out);
    }

    // This method is called when starting to write objects to the stream
    // We override this method to change how the stream header is written
    // Normally it writes a header, but we override it to skip the header
    // This allows us to append to an existing file without breaking it
    // The @Override annotation tells Java we are changing parent class behavior
    @Override
    // This method is protected so only this class and subclasses can call it
    // It throws IOException if something goes wrong with writing
    protected void writeStreamHeader() throws IOException {
        // Reset the stream instead of writing a header
        // reset() clears the object stream's internal state
        // This clears any cached objects to save memory
        // This lets us add more objects to an existing file without issues
        // Without this, we would write a new header and corrupt the file format
        // The file would then be unreadable by ObjectInputStream
        reset();
    }
}
