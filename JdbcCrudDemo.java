import java.sql.*;
import java.util.Scanner;

public class JdbcCrudDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";  // your MySQL username
        String pass = "Mysql@2025";  // your MySQL password

        Scanner sc = new Scanner(System.in);

        try {
            // 1️⃣ Load and register driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Establish connection
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected successfully!");

            while (true) {
                System.out.println("\n=== Student Database Menu ===");
                System.out.println("1. Insert Student");
                System.out.println("2. Display Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        // ➕ INSERT Operation
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();

                        String insertQuery = "INSERT INTO student VALUES (?, ?, ?)";
                        PreparedStatement psInsert = con.prepareStatement(insertQuery);
                        psInsert.setInt(1, id);
                        psInsert.setString(2, name);
                        psInsert.setInt(3, age);

                        int rowsInserted = psInsert.executeUpdate();
                        System.out.println(rowsInserted + " record inserted successfully!");
                        psInsert.close();
                        break;

                    case 2:
                        // 📋 DISPLAY Operation
                        String selectQuery = "SELECT * FROM student";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        System.out.println("\nID\tName\tAge");
                        System.out.println("---------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getInt("age"));
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case 3:
                        // ✏️ UPDATE Operation
                        System.out.print("Enter student ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = sc.nextInt();

                        String updateQuery = "UPDATE student SET name=?, age=? WHERE id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                        psUpdate.setString(1, newName);
                        psUpdate.setInt(2, newAge);
                        psUpdate.setInt(3, uid);

                        int rowsUpdated = psUpdate.executeUpdate();
                        System.out.println(rowsUpdated + " record updated successfully!");
                        psUpdate.close();
                        break;

                    case 4:
                        // ❌ DELETE Operation
                        System.out.print("Enter student ID to delete: ");
                        int did = sc.nextInt();

                        String deleteQuery = "DELETE FROM student WHERE id=?";
                        PreparedStatement psDelete = con.prepareStatement(deleteQuery);
                        psDelete.setInt(1, did);

                        int rowsDeleted = psDelete.executeUpdate();
                        System.out.println(rowsDeleted + " record deleted successfully!");
                        psDelete.close();
                        break;

                    case 5:
                        // 🚪 EXIT
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