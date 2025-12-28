**SOLID APPLY ON LIBRARY MANAGEMENT SYSTEM PROJECT **
**
Single Responsibility Principle (SRP) Implementation **

**Single Responsibility Principle (SRP) Applied to Book.java **

In this project, the Single Responsibility Principle (SRP) has been applied to improve code organization and maintainability. SRP states that a class should have only one responsibility and only one reason to change. Following this principle helps keep classes simple, focused, and easier to manage over time. 

In the initial design, the Book class was responsible not only for storing book-related data such as author, subject, and title, but also for handling user input and updating that data. This design violated SRP because changes in user interaction or input handling would require modifying the Book class, even though the core book data structure remained unchanged. As a result, the class became tightly coupled with input logic and harder to maintain or extend. 

To resolve this issue, the responsibility of updating book information was separated from the Book class and moved into a new class called BookInfoUpdater. After applying SRP, the Book class focuses only on representing and managing book data, while the BookInfoUpdater class handles user interaction and update operations by using the setter methods of the Book class. This separation ensures that each class has a clear and single responsibility. 

By applying the Single Responsibility Principle, the code became more modular, readable, and maintainable. It is now easier to modify or replace the input mechanism without affecting the Book class, and testing each class independently has become simpler.  

**Single Responsibility Principle (SRP) Applied to Loan.java **

In this part of the project, the Single Responsibility Principle (SRP) has been applied to improve the design of fine calculation and payment handling related to book loans. According to SRP, a class should focus on a single responsibility and should not be affected by changes outside that responsibility. 

In the initial implementation, the Loan class was responsible for managing loan-related data as well as calculating fines and handling fine payment through user interaction. This design violated SRP because fine calculation logic, payment decision logic, and loan state management were tightly coupled inside a single class. Any change in fine policy, payment process, or input mechanism would require modifications to the Loan class, making it harder to maintain and extend. 

To address this issue, the fine-related responsibilities were extracted from the Loan class and moved into a separate class named FineService. After applying SRP, the Loan class is responsible only for storing and managing loan information such as issue date and fine status. The FineService class now handles fine calculation based on loan data and processes fine payment by interacting with the user and updating the loan status through setter methods. 

This refactoring results in a cleaner and more modular design. The responsibilities are clearly separated, which improves readability and reduces coupling between business logic and user interaction. It also makes the system easier to modify in the future. 

 
**
Open–Closed Principle (OCP) Implementation Applied to Borrower.java **

In the initial implementation, the logic for finding a borrower depended on checking the concrete class name using getClass().getSimpleName() and then casting the object to the Borrower type. This approach violated the Liskov Substitution Principle because it relied on specific subclass knowledge and assumed that the object would always be of a particular type. Any change in the class hierarchy or introduction of new subclasses could cause incorrect behavior or runtime errors. 

After applying LSP, a polymorphic approach was introduced by defining a getRole() method in the base Person class and overriding it in the Borrower class. The borrower identification logic now works with the base Person type and determines the correct object through behavior rather than concrete class checks. This ensures that a Borrower object can safely substitute a Person object without affecting program correctness and improves flexibility and maintainability. 

 
**
Liskov Substitution Principle (LSP) Applied to Clerk.java **

Previously, the clerk identification logic followed the same pattern of checking concrete class names and performing explicit type casting. This design violated LSP because it tightly coupled the logic to a specific subclass and made the system fragile. Any future change in the inheritance structure would require modifying the existing code, increasing the risk of errors. 

After applying the Liskov Substitution Principle, the Clerk class overrides the getRole() method from the base Person hierarchy, allowing the identification logic to rely on polymorphism. The system now processes all objects as instances of the base Person type and determines the correct subclass behavior dynamically. This refactoring removes dependency on concrete class checks, ensures safe substitution of subclasses, and results in a more robust and extensible design. 

Interface Segregation Principle (ISP) Applied to LibraryOperations.Java 

In this part of the project, the Interface Segregation Principle (ISP) has been applied to improve the design of library operation interfaces. ISP states that clients should not be forced to depend on interfaces they do not use. Large, general-purpose interfaces should be split into smaller, more specific interfaces so that implementing classes only depend on the methods relevant to their responsibilities. 

In the initial implementation, a single interface named LibraryOperations contained methods related to book management, member management, and report generation. Classes such as Member were forced to implement all these methods, even though many of them were not applicable to a member’s role. As a result, several methods in the Member class were left empty, indicating a violation of ISP and leading to poor design and reduced code clarity. 

After applying the Interface Segregation Principle, the large interface was divided into smaller and more focused interfaces: BookOperations, MemberOperations, and ReportOperations. Each interface now represents a specific set of responsibilities. The Librarian class implements all relevant interfaces because it performs all those operations, while the Member class implements only the interfaces related to its actual behavior. This ensures that each class depends only on the methods it truly needs. 

By applying ISP, the design becomes cleaner, more flexible, and easier to maintain. Classes are no longer burdened with unused methods, which improves readability and reduces the risk of incorrect or empty implementations. Overall, this refactoring aligns the system with SOLID principles and results in a more modular and scalable architecture. 

**Dependency Inversion Principle (DIP) applied in Borrower.java **

In this part of the project, the Dependency Inversion Principle (DIP) has been applied to reduce coupling between high-level and low-level components. DIP states that high-level modules should not depend on low-level modules; instead, both should depend on abstractions. This principle improves flexibility and makes the system easier to extend and test. 

In the initial implementation, the Borrower class directly depended on low-level input mechanisms such as Scanner and BufferedReader to collect user input. This tightly coupled the business logic of updating borrower information with console-based input handling. As a result, any change in the input source, such as switching from console input to a graphical interface or automated testing input, would require modifying the Borrower class itself. 

After applying DIP, the input-handling responsibility was inverted by introducing an abstraction called BorrowerInputHandler. The Borrower class now depends on this interface rather than concrete input classes. A concrete implementation, ConsoleInputHandler, handles console-based input, while the Borrower class interacts only with the abstraction. This allows different input mechanisms to be introduced without changing the core borrower logic. 

By applying the Dependency Inversion Principle, the code becomes more modular, flexible, and testable. The Borrower class is now independent of specific input implementations, reducing coupling and improving maintainability.  



*******************************************************************************************************************************************************************************************************************************************
**
 CODE SMELLS ON LIBRARY MANAGEMENT SYSTEM PROJECT **
**
Refactoring of Book.java **

In the original implementation of the Book class, a major design issue was found in the constructor. The constructor required multiple primitive parameters such as title, subject, author, and issued status. This created a Long Parameter List code smell. The problem with this design was that every time a Book object was created, the caller had to remember the exact order and meaning of each parameter. This increased the risk of passing incorrect values and reduced code readability at the object creation points. 

To fix this problem, the refactoring introduced a new domain class named BookInfo. Instead of passing multiple strings to the constructor, all descriptive book data was grouped inside this object. The constructor was then modified to accept a BookInfo instance. After this refactoring, object creation became clearer and safer, and the constructor signature became more expressive. Any future changes to book metadata can now be handled by modifying BookInfo without affecting the Book class. 

Another serious issue in this class was Primitive Obsession. The book’s title, subject, and author were represented using independent primitive strings, even though they collectively represented a single real-world concept. By introducing BookInfo, the refactoring replaced these primitives with a meaningful abstraction. This change improved domain modeling and made it easier to apply validation or formatting rules related to book information. 

The class also demonstrated Feature Envy, where it directly accessed internal structures of other classes to perform operations such as printing hold requests. This was problematic because the Book class was depending too much on the internal implementation of other classes. Refactoring moved these responsibilities to the appropriate owning classes, ensuring that each class managed its own data. After refactoring, coupling was reduced and encapsulation was restored. 

Borrower.java 

In Borrower.java, the most visible code smell was Feature Envy. The class frequently accessed methods of borrowed book objects to print detailed information. This indicated that the Borrower class was performing responsibilities that belonged to another class. The problem with this design was that any change in how book information was displayed would require modifying the Borrower class, increasing maintenance effort. 

The refactoring addressed this by moving the printing logic into the borrowed book class itself. After this change, the Borrower class simply called a single method to display summaries, rather than navigating through object relationships. This improved cohesion and made responsibilities clearer. 

Another issue was Primitive Obsession, where phone numbers were stored as integers. This was problematic because phone numbers are identifiers, not numeric values, and integers cannot represent leading zeros or country codes. Refactoring replaced the integer phone number with a string representation. After refactoring, the model became more realistic and capable of handling real-world phone formats. 

The class also contained a Long Method responsible for updating borrower details. This method combined user interaction, decision logic, and attribute updates into a single block of code. This made the method hard to read and difficult to modify. The refactoring split this logic into multiple small, focused methods, each handling a single update operation. As a result, the class became easier to test, understand, and extend. 

**Refactoring of Clerk.java **

The Clerk class mainly suffered from Inappropriate Naming. Variables were named using short, meaningless identifiers that did not reflect their actual purpose. This reduced code readability and made understanding the logic unnecessarily difficult. 

Refactoring replaced these unclear names with descriptive ones that clearly explained what the variable represented. After refactoring, the code became self-documenting, reducing the need for comments and making the class easier to maintain for future developers. 

Refactoring of HoldRequest.java 

During the refactoring of the HoldRequest.java class, three specific code smells were identified that negatively affected code readability, clarity, and maintainability. Although the class was relatively small, these issues reduced code quality and made the logic harder to understand for future developers. Each smell was carefully analyzed and refactored to improve the internal structure of the class while keeping its behavior unchanged. 

1. Inappropriate Naming 

The first code smell identified in HoldRequest.java was Inappropriate Naming, particularly in the constructor parameters. In the original implementation, short and ambiguous parameter names such as bor, b, and reqDate were used. These abbreviated names did not clearly indicate what the parameters actually represented. As a result, the constructor was difficult to read and required developers to inspect the method body to understand the meaning of each parameter. 

To resolve this issue, the constructor parameters were renamed to meaningful and descriptive names such as borrower, book, and requestDate. After refactoring, the constructor became self-explanatory, allowing developers to understand its purpose directly from the method signature. This change significantly improved code readability and reduced the mental effort required to understand object creation. 

2. Duplicate Code 

The second code smell found in HoldRequest.java was Duplicate Code. Certain methods, such as simple getter methods, followed repetitive and unnecessarily verbose structures. While this duplication was not large in size, it still contributed to code redundancy and inconsistency in formatting across the class. 

The refactoring simplified these duplicated structures by standardizing method formatting and removing unnecessary boilerplate. After refactoring, the methods became cleaner and more consistent with the rest of the codebase. This reduced redundancy and made the class easier to maintain, especially if similar changes are required in the future. 

3. Long Method  

The third issue identified in the HoldRequest.java class was a Long Method–related smell, specifically in the way output formatting logic was handled. The method responsible for printing hold request details contained a long and tightly coupled output statement that combined multiple data fields and formatting logic in a single line. Although the method was not extremely long in terms of line count, the structure made it hard to read and difficult to modify. 

Refactoring improved the clarity of this logic by simplifying and structuring the output statement in a more readable manner. After refactoring, the method became easier to understand and modify, especially if the output format needs to change in the future. This change improved readability and reduced the likelihood of errors during maintenance. 

Refactoring of HoldRequestOperations.java 

Several design problems were identified in HoldRequestOperations.java. First, Inappropriate Naming was present in method parameters that did not clearly describe their role. Refactoring improved these names to align with domain terminology, making method signatures easier to understand. 

Second, the class was identified as a Lazy Class, as it provided very limited functionality despite existing as a separate abstraction. This unnecessarily increased system complexity. Refactoring documented the need to either expand the class’s responsibility or merge it with a more appropriate class, improving architectural clarity. 

The class also suffered from Comment Smell, where comments merely repeated what the code already expressed. These comments were removed, and the code was rewritten to be self-explanatory, reducing clutter and improving maintainability. 

Refactoring of Librarian.java 

During the analysis of the Librarian.java class, a total of five distinct code smells were identified. These code smells negatively affected code readability, encapsulation, maintainability, and overall design quality. Each of these issues was carefully refactored to improve the structural integrity of the class while preserving its original functionality. 

1. Inappropriate Naming 

The first code smell identified in Librarian.java was Inappropriate Naming, particularly in the constructor parameters and variable names. The original constructor used short and unclear parameter names such as n, a, p, s, and of. These abbreviated names did not convey the actual meaning or role of the parameters, making the constructor difficult to understand without inspecting the entire class. This reduced readability and increased the cognitive load for developers reading or maintaining the code. 

To fix this issue, all abbreviated parameter names were replaced with meaningful and descriptive identifiers such as name, address, phoneNo, salary, and officeNumber. After refactoring, the constructor clearly communicated the purpose of each parameter. This change made the code self-explanatory, reduced ambiguity, and significantly improved readability without requiring additional comments. 

2. Comment Smell 

The second issue found in the Librarian.java class was a Comment Smell, where comments were used to describe behavior that was already obvious from the method name and implementation. Such comments do not add any new information and instead create unnecessary clutter in the codebase. 

The refactoring removed these redundant comments and focused on making the method implementation and naming clear enough to explain itself. After this change, the code became cleaner and easier to maintain. Developers can now understand the behavior by reading the method name and body, which aligns with clean code principles that emphasize expressive code over explanatory comments. 

3. Primitive Obsession 

The third code smell identified was Primitive Obsession, where important domain-related data such as the office number was handled as a raw primitive type without proper encapsulation. In the original implementation, this variable was not adequately protected, which increased the risk of unintended modification from outside the class and reduced control over how the data was used. 

To address this problem, the office number variable was properly encapsulated by declaring it as private and ensuring controlled access through the class interface. This refactoring improved data safety and reinforced object-oriented encapsulation. After refactoring, the class had better control over its internal state, reducing the likelihood of bugs caused by uncontrolled data access. 

4. Speculative Generality 

The fourth code smell found in Librarian.java was Speculative Generality. The class contained a static variable related to office number generation that was declared as public, even though there was no clear requirement or actual usage that justified such wide accessibility. This is a classic example of designing for hypothetical future needs rather than current requirements. 

The refactoring restricted the visibility of this variable by making it private. This change eliminated unnecessary exposure and reduced the risk of misuse from other parts of the system. After refactoring, the class design became more focused on current functionality, improving robustness and adherence to the principle of least privilege. 

5. Feature Envy 

The final code smell identified in Librarian.java was Feature Envy. In the original code, the librarian object was added to a shared collection in a way that suggested the class was interacting too deeply with another class’s internal data structure. This indicated that the Librarian class was taking responsibility for managing data that should belong to a higher-level coordinating class. 

To resolve this issue, the responsibility for managing shared collections was moved to the appropriate class, ensuring that Librarian only focused on representing librarian-specific data and behavior. After refactoring, the interaction between classes became cleaner, dependencies were reduced, and each class adhered more closely to the Single Responsibility Principle. 

**Refactoring of Library.java **

The Library.java class was one of the most critical and complex components of the Library Management System. Because Library.java acted as a central class, the presence of these smells had a ripple effect on the entire system. Each smell was carefully addressed through systematic refactoring while preserving the original behavior of the system. 

1. Inappropriate Naming 

The first issue identified in Library.java was Inappropriate Naming. Some method names did not clearly describe their purpose, such as addBookinLibrary, where inconsistent casing and unclear phrasing reduced readability. Poor naming makes the code harder to understand and increases the chance of misuse. 

Refactoring corrected these names to follow standard naming conventions and better express intent, such as renaming the method to addBookInLibrary. After refactoring, method names became self-explanatory, improving code readability and reducing the need for additional comments. 

2. Comment Smell 

The class contained several unnecessary and redundant comments, such as comments that simply stated obvious actions like importing files or creating objects. These comments did not add any meaningful information and instead cluttered the codebase. 

Refactoring removed these redundant comments and focused on making the code expressive through clear method and variable naming. After this change, the class became cleaner and easier to read, aligning with clean code principles that emphasize readable code over excessive commenting. 

3. Long Method 

One of the most serious problems in Library.java was the presence of Long Methods, particularly the populateLibrary() method. This method contained hundreds of lines of code and performed multiple unrelated tasks such as loading books, borrowers, clerks, loans, and hold requests from the database. 

This design made the method extremely difficult to understand, debug, and modify. Refactoring decomposed this large method into multiple smaller, well-named helper methods such as populateBooks(), populateBorrowers(), and populateLoans(). After refactoring, each method handled a single responsibility, significantly improving readability, maintainability, and testability. 

4. Large Class 

The Library.java class exhibited a classic Large Class code smell. It handled too many responsibilities at once, including business logic, database operations, user interaction, data storage, and printing. This violated the Separation of Concerns principle and made the class difficult to maintain. 

Refactoring addressed this issue by splitting responsibilities across multiple specialized classes such as service classes for business logic, repository classes for database access, and UI classes for user interaction. After refactoring, Library.java focused only on core domain responsibilities, making the overall system architecture more modular and scalable. 

5. Duplicate Code 

Several instances of Duplicate Code were found in Library.java, especially in database cleanup and SQL execution logic. The same sequence of preparing statements and executing SQL commands was repeated for multiple database tables. 

This duplication increased maintenance effort and raised the risk of inconsistent behavior if changes were required. Refactoring introduced a reusable helper method to execute SQL update operations. After this change, duplicated code was eliminated, reducing redundancy and improving maintainability. 

6. Dead Code 

The class contained Dead Code, including commented-out loops and alternative logic that were no longer used. Such code adds noise to the codebase and confuses developers about what logic is actually active. 

Refactoring removed all commented-out and unused code sections. After cleanup, the class became more concise and easier to navigate, reducing confusion and improving code clarity. 

7. Primitive Obsession 

Library.java also suffered from Primitive Obsession, where important concepts were represented using primitive data types or raw collections without generics. Examples included using raw ArrayList types and primitive values for domain-specific concepts like deadlines. 

Refactoring introduced proper generics and more meaningful domain representations, such as using typed collections and appropriate domain abstractions. After refactoring, type safety improved, runtime errors were reduced, and the code better reflected real-world concepts. 

8. Feature Envy 

The final code smell identified was Feature Envy, where Library.java directly accessed and manipulated internal data of other classes, such as iterating through person collections to find specific entities. This tightly coupled the class with the internal structure of other classes. 

Refactoring encapsulated this logic inside dedicated methods, such as person lookup utilities. After refactoring, Library.java interacted with other classes through well-defined interfaces rather than internal data structures. This reduced coupling, improved encapsulation, and made the code easier to extend and maintain. 
**
Refactoring of Loan.java **

The Loan.java class represents a critical domain concept in the Library Management System, as it is responsible for handling book issuing, returning, and fine-related operations. During refactoring, four major code smells were identified in this class. These issues affected readability, clarity, separation of concerns, and long-term maintainability. Each smell was carefully addressed to improve the internal structure of the class while keeping its external behavior unchanged. 

1. Inappropriate Naming 

The first code smell identified in Loan.java was Inappropriate Naming, particularly in the constructor parameters. In the original implementation, abbreviated and unclear parameter names such as bor, b, i, r, iDate, and rDate were used. These names did not clearly indicate the roles of the objects involved, especially for staff members responsible for issuing and receiving books. 

This naming made the constructor difficult to understand and required developers to inspect the method body to determine the meaning of each parameter. To resolve this issue, the parameters were renamed to meaningful and descriptive identifiers such as borrower, book, issuer, receiver, issuedDate, and returnDate. After refactoring, the constructor became self-explanatory, improving readability and reducing the cognitive load required to understand object creation. 

2. Comment Smell 

The second issue found in Loan.java was a Comment Smell, where comments were used to explain behavior that was already obvious from the method signature. For example, comments like “Returns the book” were placed above getter methods whose purpose was already clear. 

These comments did not add any additional value and instead cluttered the code. Refactoring removed such redundant comments and relied on clear method naming to communicate intent. After refactoring, the code became cleaner, easier to read, and more consistent with clean code practices that favor expressive code over unnecessary comments. 

3. Primitive Obsession 

The third code smell identified in the Loan.java class was Primitive Obsession, particularly in the handling of fine-related state using primitive boolean variables such as finePaid. While functional, this simplistic representation limited expressiveness and made it harder to extend fine-related behavior in the future. 

Refactoring ensured proper encapsulation of this field by clearly defining its access level and controlling how it is modified. This improved data integrity and clarified the role of the variable within the class. After refactoring, the code better reflected domain intent and became easier to extend if additional fine-related logic is required later. 

4. Long Method (Mixed Responsibilities in Fine Payment) 

The most significant design issue in Loan.java was a Long Method related to fine payment processing. The original payFine() method combined multiple responsibilities, including user input handling, decision-making, output formatting, and business logic for fine calculation and payment confirmation. This violated the Single Responsibility Principle and made the method difficult to read, test, and modify. 

Refactoring addressed this problem by separating user interaction logic from business logic. The payFine() method was simplified to focus only on fine-related behavior, while user interface concerns were handled elsewhere. After refactoring, the method became shorter, more focused, and easier to maintain. This separation also improved testability, as business logic could now be tested independently of user input. 

Detailed Refactoring of Main.java 

The Main.java class serves as the entry point of the Library Management System and initially acted as a controller for nearly all application functionality. During code analysis, eight major code smells were identified in this class. These issues significantly affected readability, maintainability, modularity, and separation of concerns. Because Main.java controlled most of the program flow, poor design decisions in this class had a strong negative impact on the entire system. Each identified smell was addressed through careful refactoring while preserving the original behavior of the application. 

1. Inappropriate Naming 

The first code smell identified in Main.java was Inappropriate Naming, particularly in method names such as takeInput. The method name did not clearly express its actual responsibility, which was to retrieve and validate a user’s menu choice within a defined range. Such vague naming made the code harder to understand and required additional context to interpret. 

Refactoring renamed this method to getUserChoice, along with more descriptive parameter names. After refactoring, the method signature clearly communicated its purpose, improving readability and reducing ambiguity throughout the codebase. 

2. Comment Smell 

Main.java contained several Comment Smells, including comments that simply stated obvious actions such as importing libraries or describing what a method already clearly did. These comments added noise rather than value and made the file longer and harder to scan. 

Refactoring removed these redundant comments and relied instead on clear method and variable names. This resulted in cleaner code that was easier to read and aligned with clean code principles, where code should be self-explanatory whenever possible. 

3. Long Method 

One of the most severe problems in Main.java was the presence of a Long Method, particularly within the main() method. The original main() method contained hundreds of lines of code handling user authentication, menu navigation, role-based logic, and system operations all at once. 

This made the method extremely difficult to understand, debug, and modify. Refactoring addressed this issue by extracting large sections of logic into smaller, well-defined helper methods such as showMainMenu() and delegating responsibilities to other classes. After refactoring, the main() method became concise and focused solely on application startup and control flow. 

4. Large Class 

Closely related to the long method problem, Main.java also suffered from the Large Class code smell. It handled multiple responsibilities including UI rendering, user role management, business logic, and system coordination. This violated the Single Responsibility Principle and made the class difficult to maintain. 

Refactoring decomposed the class into multiple role-specific menu classes such as AdminMenu, BorrowerMenu, ClerkMenu, and LibrarianMenu. After refactoring, each class focused on a single user role, significantly improving modularity, clarity, and extensibility. 

5. Duplicate Code 

The Main.java class contained repeated blocks of code for printing menu headers, separators, and user interface formatting. This duplication increased maintenance effort and made UI changes error-prone. 

Refactoring introduced reusable helper methods such as printPortalHeader() to centralize common UI logic. After refactoring, duplicate code was eliminated, reducing redundancy and ensuring consistent UI formatting across the application. 

6. Primitive Obsession 

Another issue identified in Main.java was Primitive Obsession, particularly in the repeated creation of Scanner objects for user input. Multiple instances of Scanner increased resource usage and made input handling inconsistent. 

Refactoring introduced a single, shared Scanner instance declared as a constant. After refactoring, input handling became more consistent, resource-efficient, and easier to manage across the application. 

7. Feature Envy 

Main.java exhibited Feature Envy by directly invoking lower-level library operations that belonged to the service layer. This tightly coupled the user interface with business logic and made future changes more difficult. 

Refactoring redirected these calls through a dedicated service layer, allowing Main.java to focus only on user interaction and navigation. After refactoring, dependencies were reduced, and the class adhered more closely to layered architecture principles. 

8. Dead Code 

The final issue identified in Main.java was Dead Code, including unused methods such as manual console-clearing logic that no longer served any purpose. Retaining such code caused confusion and cluttered the class. 

Refactoring removed all unused and obsolete code. After cleanup, Main.java became more concise, easier to read, and free from unnecessary distractions. 

Refactoring of Person.java 

The Person.java class represents a core domain entity in the Library Management System, as it serves as the base class for different types of users such as borrowers, staff members, and librarians. Because of this central role, any design issue in this class directly affects multiple parts of the system. During refactoring, three major code smells were identified in Person.java. These issues mainly impacted domain modeling, readability, and maintainability. Each smell was carefully refactored to improve the internal structure of the class while keeping its behavior unchanged. 

1. Inappropriate Naming 

The first code smell identified in Person.java was Inappropriate Naming, particularly in method names related to setting personal information. For example, a method like setPhone(int p) used an abbreviated parameter name that did not clearly describe its purpose. Such naming reduced readability and made the code harder to understand without inspecting the method body. 

Refactoring replaced these vague names with more descriptive ones, such as renaming the method to setPhoneNumber(int phoneNumber). After refactoring, the method signature clearly communicated what data was being modified. This change made the code more self-explanatory and reduced ambiguity, especially for developers who are new to the codebase. 

2. Comment Smell 

The second issue found in Person.java was a Comment Smell, where comments were used to explain behavior that was already obvious from the method name and implementation. For instance, comments like “Printing Info of a Person” appeared directly above a method named printInfo(). These comments did not add meaningful information and instead cluttered the code. 

Refactoring removed such redundant comments and focused on improving the internal structure of the method. Helper methods such as printSeparator() were introduced to make the logic clearer and reduce duplication. After refactoring, the code became cleaner, easier to read, and more consistent with clean code principles that emphasize expressive code over unnecessary comments. 

3. Primitive Obsession 

The most significant code smell in Person.java was Primitive Obsession. Important real-world concepts such as address and phone number were originally represented using primitive data types like String and int. This design limited expressiveness and made it difficult to enforce validation rules or extend functionality related to these concepts. 

Refactoring introduced dedicated value objects such as Address and PhoneNumber to represent these concepts properly. By replacing primitive fields with domain-specific objects, the class achieved stronger domain modeling and better encapsulation. After refactoring, validation logic could be centralized inside these value objects, and the Person class became more robust, flexible, and easier to maintain. 

 
**
Refactoring of Staff.java **

The Staff.java class represents a specialized extension of the Person class in the Library Management System. It is responsible for modeling staff-related attributes and behavior, such as salary information and staff-specific operations. Because this class inherits from Person, maintaining clean design and clear responsibility boundaries is essential. During refactoring, two key code smells were identified in Staff.java. These issues mainly affected readability, clarity, and proper responsibility alignment. Each smell was carefully refactored to improve the internal structure of the class while preserving its original functionality. 

 

1. Inappropriate Naming 

The first code smell identified in Staff.java was Inappropriate Naming, particularly in the constructor parameter list. In the original implementation, the constructor used abbreviated parameter names such as n, a, and p, which did not clearly express their meaning. Although the logic was correct, the lack of descriptive names made the constructor difficult to read and understand, especially for developers unfamiliar with the codebase. 

Refactoring replaced these abbreviated names with meaningful and descriptive identifiers such as name, address, and phone. After refactoring, the constructor signature clearly communicated the purpose of each parameter. This change significantly improved readability and made the code more self-explanatory without requiring additional comments. 

 

2. Feature Envy 

The second code smell found in Staff.java was Feature Envy, particularly in the printInfo() method. The method relied heavily on functionality inherited from the Person class while also adding staff-specific output. Although inheritance was used correctly, the method structure risked becoming overly dependent on the parent class’s implementation details if extended further. 

Refactoring ensured that the method maintained a clear separation between shared behavior and staff-specific logic. The printInfo() method now properly delegates common behavior to the parent class and focuses only on printing staff-related information. After refactoring, the class adheres more closely to object-oriented principles and maintains a clean inheritance structure. 

 

 

*******************************************************************************************************************************************************************************************************************************************

** Person Class – Builder Pattern and Decorator Pattern **

In the initial implementation, the Person class used a constructor that required all attributes such as name, address, and phone number to be passed at once. As the number of attributes increased, object creation became less readable and harder to manage. This approach also made it difficult to handle optional fields and reduced flexibility during object construction. 

To solve this problem, the Builder Pattern was applied to the Person class. A static inner Builder class was introduced to construct Person objects step by step using fluent method calls. The builder stores individual attribute values and creates the final Person object through the build() method. This pattern is used in the object creation part of the code, replacing the complex constructor-based initialization. 

After applying the Builder Pattern, object creation became more readable, flexible, and maintainable. It eliminated constructor overloading and made the code easier to extend when new fields are added to the Person class. 

In addition, the Decorator Pattern was applied to extend the behavior of the Person class without modifying its source code. The PersonComponent interface defines the common behavior, while PersonDecorator acts as a base wrapper. The VerifiedPersonDecorator adds extra functionality by displaying verification status along with the original person information. 

By applying the Decorator Pattern, new features can be added dynamically to a Person object without changing existing code. This improves extensibility, follows the open–closed principle, and allows flexible enhancement of person-related behavior. 

 

**Main Class – Facade Pattern and Factory Method Pattern **

In the initial implementation, the Main class was responsible for creating and coordinating multiple objects such as Library, Librarian, Clerk, Book, and Borrower. This resulted in tight coupling between the Main class and several subsystem classes, making the code harder to read, maintain, and modify. Any change in object creation logic or interaction flow would directly affect the Main class. 

To reduce this complexity, the Facade Pattern was applied by introducing the LibraryFacade class. The facade provides a simplified interface that encapsulates interactions between the Library, Librarian, and Clerk classes. In the updated design, the Main class communicates only with the facade instead of dealing with multiple subsystems directly. This pattern is used in the book addition and book issuing operations, where the facade internally delegates responsibilities to the appropriate classes. 

Additionally, the Factory Method Pattern was applied to centralize object creation logic. The ObjectFactory class provides static factory methods for creating instances of Library, Librarian, Clerk, Book, and Borrower. The Main class now relies on these factory methods instead of directly calling constructors, which reduces dependency on concrete implementations. 

After applying these design patterns, the Main class became cleaner, more readable, and easier to maintain. The facade hides subsystem complexity, while the factory methods simplify and standardize object creation. 

**Loan Class – Factory Method Pattern and State Pattern **

In the initial implementation, the Loan class used a public constructor that required the loan duration to be passed directly during object creation. This made the loan creation process rigid and exposed internal rules such as loan duration to the client code. Additionally, loan status was managed using a simple boolean flag, which led to conditional logic and made state transitions harder to control and extend. 

To improve object creation and encapsulate loan rules, the Factory Method Pattern was applied to the Loan class. The constructor was made private, and static factory methods such as createShortTermLoan() and createLongTermLoan() were introduced. These factory methods centralize loan creation logic and ensure that loan objects are created with predefined, valid durations. This pattern is used specifically at the point where loan objects are instantiated. 

To further enhance behavior management, the State Pattern was applied to handle different loan states such as active and returned. Instead of using a boolean flag and conditional checks, loan behavior was delegated to state classes like ActiveLoanState and ReturnedLoanState. The Loan class maintains a reference to a LoanState object and delegates state-specific behavior to it. 

After applying these design patterns, the Loan class became more flexible, extensible, and maintainable. Loan creation logic is now centralized and protected, while state transitions are clearly defined and easy to extend with new states if required. 

**Librarian Class – Facade Pattern and Strategy Pattern **

In the initial implementation, the Librarian class directly interacted with multiple system components such as Library, Book, and HoldRequest. This caused tight coupling between the librarian and the internal library subsystem. Any change in library management logic would directly affect the Librarian class, making the code harder to maintain and extend. 

To reduce this complexity, the Facade Pattern was applied by introducing the LibraryFacade class. The facade acts as a simplified interface that encapsulates library-related operations such as adding and removing books and approving hold requests. In the refactored design, the Librarian class communicates only with the facade instead of interacting with the Library class directly. This pattern is used in the book management and hold approval operations, where the facade internally handles the actual logic. 

Additionally, the Strategy Pattern was applied to make librarian behavior more flexible. Different actions such as adding or removing books were encapsulated into separate strategy classes (AddBookStrategy and RemoveBookStrategy) that implement the LibrarianStrategy interface. The Librarian class acts as a context and delegates the task execution to the selected strategy at runtime. 

After applying these design patterns, the Librarian class became more modular, flexible, and easier to extend. The facade hides subsystem complexity, while the strategy pattern removes conditional logic and allows new librarian actions to be added without modifying existing code.  

**HoldRequest Class – Proxy Pattern and Mediator Pattern **

In the initial implementation, the HoldRequest class directly handled the approval and rejection of hold requests. There was no control mechanism or intermediate layer to validate permissions before approving a request. Additionally, the HoldRequest class directly managed interactions between Book and Borrower, which resulted in tight coupling and reduced flexibility when modifying or extending the hold request workflow. 

To address access control and add an additional safety layer, the Proxy Pattern was applied. The HoldRequestSubject interface defines the common operations, while RealHoldRequest performs the actual approval and rejection logic. The HoldRequestProxy acts as an intermediary that performs permission checks before delegating the request to the real object. This pattern is used specifically in the approval and rejection process to control access without modifying the core logic. 

To further reduce coupling between system components, the Mediator Pattern was applied. A mediator interface (HoldMediator) and its concrete implementation (LibraryHoldMediator) were introduced to manage communication between Book and Borrower. Instead of interacting directly, the HoldRequest class now delegates coordination logic to the mediator, which centralizes control over hold request placement and approval. 

After applying these design patterns, the HoldRequest class became more secure, modular, and maintainable. The Proxy Pattern adds controlled access and extensibility, while the Mediator Pattern simplifies communication and reduces dependencies between objects.  

**Clerk Class – Strategy Pattern and Factory Method Pattern **

In the initial implementation, the Clerk class contained fixed logic for issuing books, including a hard-coded loan duration of 14 days. This made the issuing process rigid and difficult to modify. If the library needed different issuing rules, such as short-term or special loans, the existing code would have to be changed directly, violating the open–closed principle. 

To solve this problem, the Strategy Pattern was applied to the Clerk class. An IssuePolicy interface was introduced to define different loan duration strategies. Concrete implementations such as NormalIssuePolicy and ShortIssuePolicy encapsulate different issuing rules. The Clerk class acts as a context and delegates the loan duration decision to the selected strategy at runtime. This pattern is used in the issueBook() method, where the loan period is determined dynamically through the strategy. 

Additionally, the Factory Method Pattern was applied to control the creation of Clerk objects. The constructor was made private, and static factory methods such as createJuniorClerk() and createSeniorClerk() were introduced. This allows the system to create different types of clerks with predefined configurations without exposing constructor logic to the client code. 

After applying these design patterns, the Clerk class became more flexible, extensible, and maintainable. The Strategy Pattern enables easy modification of issuing rules without changing existing code, while the Factory Method Pattern standardizes object creation.  

**Book Class – Decorator Pattern **

In the initial implementation, the Book class was responsible for managing both core book information and its issuing behavior. The class directly handled availability status and issuing rules, which made it difficult to introduce special types of books, such as reference books, without modifying the existing class. Adding new book behaviors required changing the Book class itself, which violated the open–closed principle. 

To address this limitation, the Decorator Pattern was applied to the Book class. A common interface, BookComponent, was introduced to define shared behavior. The original Book class was transformed into a concrete component that implements this interface and focuses only on core book information such as the title. Additional responsibilities are added dynamically using decorator classes. 

The BookDecorator class serves as a base decorator that wraps a BookComponent object, while the ReferenceBookDecorator extends it to add extra behavior. This decorator is used to display additional information indicating that a book is a reference book and cannot be issued, without altering the original Book class.After applying the Decorator Pattern, the design became more flexible and extensible. New book types and behaviors can now be added dynamically without modifying existing code. 

**Borrower Class – Observer Pattern and Facade Pattern **

In the initial implementation, the Borrower class directly handled book borrowing and returning logic by interacting with the Book class. This created tight coupling between borrower behavior and book operations. Any change in borrowing rules or notification requirements would require modifying the Borrower class, making the design less flexible and harder to extend. 

To improve notification handling, the Observer Pattern was applied to the Borrower class. An Observer interface was introduced, and Borrower implements this interface to receive updates through the update() method. This allows the borrower to be notified about events, such as book availability or system messages, without being tightly coupled to the event source. The observer logic is used specifically for notification purposes and separates event handling from core borrower behavior. 

In addition, the Facade Pattern was applied to simplify borrowing and returning operations. The BorrowServiceFacade class encapsulates the interaction between Borrower and Book. Instead of directly calling book methods, the Borrower class delegates these responsibilities to the facade. This hides the complexity of borrowing logic and provides a clean, simplified interface for borrower actions. 

After applying these design patterns, the Borrower class became more modular, loosely coupled, and easier to maintain. The Observer Pattern enables flexible notification handling, while the Facade Pattern simplifies interactions with the book subsystem. 

**Library Class – Singleton Pattern and Facade Pattern **

In the initial implementation, the Library class allowed multiple instances to be created freely. Each instance maintained its own list of books, which could lead to data inconsistency across the system. If different parts of the application created separate Library objects, book information could become fragmented and unreliable. 

To solve this problem, the Singleton Pattern was applied to the Library class. The constructor was made private, and a static getInstance() method was introduced to ensure that only one instance of the library exists throughout the application. This pattern is used at the object creation level and guarantees a single, globally accessible library instance for managing shared resources such as the book collection. 

In addition, the Facade Pattern was applied to simplify interaction with the library subsystem. The LibraryFacade class provides a clean and minimal interface for common operations like adding, removing, and retrieving books. Client classes interact with the facade instead of directly accessing the internal Library implementation, which reduces coupling and hides internal complexity. 

After applying these design patterns, the Library class became more reliable, consistent, and easier to use. The Singleton Pattern ensures centralized data management, while the Facade Pattern simplifies system interaction and improves maintainability.  

 

*******************************************************************************************************************************************************************************************************************************************
 
  
**Architectural Context Diagram (ACD) **


The Architectural Context Diagram visually represents the Library Management System as a single, central target system and clearly defines its boundaries. The diagram places the Library Management System at the center to emphasize that it is the primary software system under consideration. All interactions shown in the diagram either originate from or terminate at this target system, which helps distinguish internal system functionality from external dependencies. 

On the left side of the diagram, the actors are shown. These include the Library Member (Student/Faculty), Librarian, and Clerk. Arrows labeled “Uses” indicate that these actors actively interact with the Library Management System to perform specific tasks. For example, a Library Member uses the system to search for books, place hold requests, and borrow books. The Librarian uses the system for administrative functions such as adding or removing books, approving loans, and managing reservations. The Clerk interacts with the system to support daily operations like issuing and returning books. The direction of the arrows clearly indicates that the system provides services to these actors. 

At the top of the diagram, a superordinate system, labeled the University System, is shown. The arrow marked “Used by” indicates that the Library Management System supplies data or services to this higher-level system. This relationship highlights that the library system is a subsystem within a larger academic information ecosystem. Even if the University System is not implemented within the project, its presence explains the broader operational context of the library system. 

On the right side of the diagram, a peer system, such as the Inventory System, is shown with an arrow labeled “Uses”. This indicates a collaboration between systems operating at the same hierarchical level. The Inventory System and Library Management System may exchange information related to physical book stock or asset tracking, but neither system controls the other. 

At the bottom of the diagram, a subordinate system—the Database or File Storage System—is shown. The arrow labeled “Depends on” emphasizes that the Library Management System relies on this component for persistent data storage. All book records, loan details, and hold request information are stored and retrieved through this subordinate system. This clearly shows that data persistence is an external responsibility, not part of the core application logic. 

 
**Archetype Diagram **

The archetype diagram visually organizes the system’s core abstractions around a central concept labeled “Archetypes”, indicating that all architectural roles stem from this classification. Each surrounding box represents a distinct archetype category, and the arrows connecting them to the center highlight their architectural importance rather than direct execution flow. 

The User Archetype groups entities such as Person and Staff, emphasizing that these classes represent individuals who interact with the system. The diagram shows that these users initiate actions but do not control system rules. Their responsibilities are limited to requesting services and receiving responses. 

The Administration Archetype, which includes Librarian and Clerk, is visually separated to indicate elevated authority. This separation highlights that administrative roles have additional privileges and responsibilities, such as managing resources and approving transactions. The diagram makes it clear that not all users are equal in terms of system access. 

The Resource Archetype, represented by the Book class, is shown as a core element because it is the primary object being managed. The diagram visually reinforces that most system operations revolve around this archetype, such as searching, borrowing, or reserving books. 

The Transaction Archetype includes Loan and HoldRequest and is represented as a separate category to emphasize time-bound processes. The diagram shows that these entities are not static; instead, they represent workflows that evolve over time and depend on system rules. 

The Core System Archetype, represented by the Library class, is placed in a central and prominent position, indicating that it coordinates all system activities. This placement visually communicates that the Library class acts as the system’s brain, enforcing business rules and orchestrating interactions. 

Finally, the Entry Point Archetype, represented by the Main class, is shown to indicate where system execution begins. The diagram clarifies that Main does not contain business logic but serves as a launcher for the system. 

 

 
**Refinement of the initial architecture into components **

 
The component refinement diagram shows how the system evolves from abstract design into concrete, deployable components. The diagram uses clearly labeled boxes to represent components and arrows to show communication and dependency relationships between them. 

At the top of the diagram, the User Management Component is shown, containing Person, Staff, Librarian, and Clerk. Its position indicates that user-related requests originate here. The arrows connecting this component to the central interface layer indicate that all user interactions must pass through a defined interface, ensuring controlled access to the system. 

In the center, the Library Core Component is highlighted to show its role as the central decision-making unit. Bidirectional arrows connect it with both the Resource Management and Transaction Management components, visually indicating that it coordinates resource availability with transaction processing. This design ensures that no transaction occurs without validation against business rules. 

The Resource Management Component, which manages Book entities, is shown as a separate block to emphasize modularity. The arrows between this component and the Library Core demonstrate that book-related operations are controlled and validated by core logic. 

The Transaction Management Component, which includes Loan and HoldRequest, is positioned symmetrically to Resource Management, reinforcing architectural balance. The arrows show that transaction execution depends on the Library Core for rule enforcement. 

At the bottom, the Data/Persistence Component is shown, connected to the Library Core through dependency arrows. This placement emphasizes that only validated and processed data reaches the persistence layer, ensuring data consistency and integrity. 

 

** Layered + MVC Architecture Applied on Project Library Management System **

 

 

The final diagram illustrates a multi-layered system structure, with each layer stacked vertically to reflect abstraction levels. The topmost Presentation Layer contains an embedded MVC structure, visually enclosed to show that MVC operates only within this layer. 

Inside the Presentation Layer, the View, Controller, and Model are arranged from left to right, connected by arrows that represent request and response flow. The View captures user input and displays output, the Controller processes input and invokes appropriate services, and the Model represents domain data. The arrows make it clear that the View never directly communicates with the Business Layer, ensuring proper separation of concerns. 

Below the Presentation Layer, the Application/Service Layer is shown as an intermediary. The arrows connecting it to both the Presentation and Business layers indicate that it manages coordination, validation, and workflow control. 

The Business Layer is positioned beneath the Service Layer and contains the Library class and business rules. Its central placement and strong connections highlight that it governs all system behavior. 

Finally, the Data Layer sits at the bottom, visually representing the lowest level of abstraction. The arrows indicate that all data access flows downward through validated paths, reinforcing clean architecture principles. 

 

 

 

 

 

 

 

 

 

 

 
