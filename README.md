# Test Lifecycle Per Class Demo project
A demo Test Lifecycle Per Class project which evaluates the following in a single test instance:
- User creation
- Updating user
- Fetching user
- Deleting user

The main test class can be found in `src/test/java/blog/yrol/service/UserServiceImplTest.java`

The tests should be executed at class level instead of running individual methods since the user data is dependent on each other. 
When running the test class, the test methods will execute in order automatically.

![](https://i.imgur.com/C2em0BE.png)

