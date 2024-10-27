
# fsjndi

`fsjndi` is a lightweight implementation of JNDI (Java Naming and Directory Interface) that uses local file storage for object persistence, ideal for development and testing environments. It enables named object storage without relying on an external JNDI service. This project also includes JMS compatibility with ActiveMQ, making it easier to develop and test messaging applications.

## Features

- **Local JNDI Context**: Provides a local, file-based JNDI context, allowing for persistent access to objects.
- **Object Serialization**: Objects are serialized and saved to files, making their state persistent across executions.
- **JMS Compatibility with ActiveMQ**: Supports managing JMS objects, such as `ConnectionFactory` and `Queue`, with ActiveMQ.
- **Unit Tests**: Contains unit tests and is configured for continuous integration using GitHub Actions.

## Prerequisites

- **Java**: Version 8 or higher
- **Maven**: Version 3.6 or higher
- **ActiveMQ**: Required for JMS functionality (optional for basic usage)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/gregoryguibert/fsjndi.git
   cd fsjndi
   ```

2. Build and install dependencies with Maven:
   ```bash
   mvn clean install
   ```

3. Configure a `jndi.properties` file if you want to customize the JNDI configuration for your environment.

## JNDI Configuration with `jndi.properties`

To use the local context, create a `jndi.properties` file at the project root or in the path specified by your application. Example content:

```properties
java.naming.factory.initial=fr.sirconflex.utils.fsjndi.SimpleFileContextFactory
java.naming.provider.url=file:/target/jndi-directory
```

Make sure that the path specified in `provider.url` exists or is writable.

## Usage

### Example of JNDI Context Initialization

```java
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Example {
    public static void main(String[] args) {
        try {
            Context context = new InitialContext();

            // Bind an object to the JNDI context
            context.bind("java:/testString", "Hello, JNDI!");
            
            // Lookup the bound object
            String result = (String) context.lookup("java:/testString");
            System.out.println("Object found: " + result);
            
            // Close the context
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
```

### Running Unit Tests

To run tests, use Maven with the command:

```bash
mvn test
```

The tests temporarily create files in the `target/test-directory` folder to simulate the local context.

## JMS Integration with ActiveMQ

The project includes Maven dependencies for ActiveMQ. To test JMS functionalities, configure your `ConnectionFactory` and `Queue` objects as shown below:

```java
context.bind("java:/ConnectionFactory", new ActiveMQConnectionFactory("tcp://localhost:61616"));
```

## Implementation Limitations

While `fsjndi` provides essential JNDI functionality, it has the following limitations:

- **Partial Implementation of `Context` Interface**: The `SimpleNamingContext` class does not implement all methods in the `javax.naming.Context` interface. Unsupported methods will throw `UnsupportedOperationException`. This may limit compatibility with applications expecting a fully functional `Context` interface.
- **Local Storage Only**: This implementation uses a local file system to store objects, making it unsuitable for distributed applications where remote JNDI access is required.
- **Serialization Requirement**: Objects must be serializable to be bound to the context, as they are stored as serialized files.
- **Limited Concurrency**: This implementation does not handle concurrent access, so race conditions may arise if multiple instances interact with the same storage directory simultaneously.
- **Simple Security Model**: There is no built-in authentication or access control, which may pose security risks in multi-user environments.

## Contributions

Contributions are welcome! Please submit an **issue** for bug reports or feature requests, and create a **pull request** for any contributions.

## License

This project is licensed under the Apache License, Version 2.0. See the `LICENSE` file for more details.
