package blog.yrol.service;

import blog.yrol.io.UsersDatabase;
import blog.yrol.io.UsersDatabaseMapImpl;
import blog.yrol.services.UserService;
import blog.yrol.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Using @TestInstance(TestInstance.Lifecycle.PER_CLASS) to create instance one time only for all the test methods
 * Using annotation Order in order to run test cases in a given order.
 * NOTE: The test class should be executed at once in order to pass all the test cases (since the test data is dependent on each other).
 * **/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId;

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Cena");
        createdUserId = userService.createUser(user);

        // Making the user ID of the newly created is not null
        assertNotNull(createdUserId, "User Id should not be null");
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {

        Map getOriginalUser = userService.getUserDetails(createdUserId);
        assertEquals("John", getOriginalUser.get("firstName"), "Invalid user first name");
        assertEquals("Cena", getOriginalUser.get("lastName"),"Invalid user last name");


        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Robert");
        newUserDetails.put("lastName", "De Niro");

        userService.updateUser(createdUserId, newUserDetails);

        Map getUpdatedUser = userService.getUserDetails(createdUserId);

        assertEquals("Robert", getUpdatedUser.get("firstName"), "Invalid user first name after update");
        assertEquals("De Niro", getUpdatedUser.get("lastName"), "Invalid user last name after update");
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        Map getUser = userService.getUserDetails(createdUserId);
        assertEquals("Robert", getUser.get("firstName"));
        assertEquals("De Niro", getUser.get("lastName"));
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        userService.deleteUser(createdUserId);
        Map getUser = userService.getUserDetails(createdUserId);
        assertNull(getUser, "User exists");
    }

}
