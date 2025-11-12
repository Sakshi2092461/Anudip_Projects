import java.sql.*;
import java.util.Scanner;

public class JdbcCustomerCrud {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/customerdb";
        String user = "root";         // your MySQL username
        String pass = "Mysql@2025"; // your MySQL password

        Scanner sc = new Scanner(System.in);

        try {
            // Step 1: Load and register driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish connection
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected successfully!");

            while (true) {
                System.out.println("\n=== Customer Database Menu ===");
                System.out.println("1. Add Customer");
                System.out.println("2. Display All Customers");
                System.out.println("3. Update Customer");
                System.out.println("4. Delete Customer");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // INSERT operation
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();

                        String insertQuery = "INSERT INTO customer VALUES (?, ?, ?, ?)";
                        PreparedStatement psInsert = con.prepareStatement(insertQuery);
                        psInsert.setInt(1, id);
                        psInsert.setString(2, name);
                        psInsert.setString(3, email);
                        psInsert.setString(4, phone);

                        int rowsInserted = psInsert.executeUpdate();
                        System.out.println(rowsInserted + " record inserted successfully!");
                        psInsert.close();
                        break;

                    case 2:
                        // DISPLAY operation
                        String selectQuery = "SELECT * FROM customer";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        System.out.println("\nID\tName\t\tEmail\t\t\tPhone");
                        System.out.println("------------------------------------------------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + "\t" +
                                               rs.getString("name") + "\t" +
                                               rs.getString("email") + "\t" +
                                               rs.getString("phone"));
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case 3:
                        // UPDATE operation
                        System.out.print("Enter Customer ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new Email: ");
                        String newEmail = sc.nextLine();
                        System.out.print("Enter new Phone: ");
                        String newPhone = sc.nextLine();

                        String updateQuery = "UPDATE customer SET email=?, phone=? WHERE id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                        psUpdate.setString(1, newEmail);
                        psUpdate.setString(2, newPhone);
                        psUpdate.setInt(3, uid);

                        int rowsUpdated = psUpdate.executeUpdate();
                        System.out.println(rowsUpdated + " record updated successfully!");
                        psUpdate.close();
                        break;

                    case 4:
                        // DELETE operation
                        System.out.print("Enter Customer ID to delete: ");
                        int did = sc.nextInt();

                        String deleteQuery = "DELETE FROM customer WHERE id=?";
                        PreparedStatement psDelete = con.prepareStatement(deleteQuery);
                        psDelete.setInt(1, did);

                        int rowsDeleted = psDelete.executeUpdate();
                        System.out.println(rowsDeleted + " record deleted successfully!");
                        psDelete.close();
                        break;

                    case 5:
                        // EXIT
                        System.out.println("Exiting program... Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
