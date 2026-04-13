package dev.rubinaw.auth.repository;

import dev.rubinaw.auth.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Implements user lookup operations with plain JDBC.
 */
public class JdbcUserRepository implements UserRepository {

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                user_id VARCHAR(36) PRIMARY KEY,
                email VARCHAR(255) NOT NULL UNIQUE,
                email_norm VARCHAR(255) NOT NULL UNIQUE,
                password_hash VARCHAR(255) NOT NULL,
                is_active TINYINT(1) NOT NULL DEFAULT 1,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
            """;

    private static final String FIND_BY_EMAIL_SQL = """
            SELECT user_id, email, password_hash
            FROM users
            WHERE email_norm = ?
            LIMIT 1
            """;

    private static final String INSERT_USER_SQL = """
            INSERT INTO users (user_id, email, email_norm, password_hash)
            VALUES (?, ?, ?, ?)
            """;

    /**
     * Creates repository and ensures required table exists.
     */
    public JdbcUserRepository() {
        createUsersTableIfNotExists();
    }

    /**
     * Finds a user by email address.
     *
     * @param email the email address to search for
     * @return the user when found, otherwise {@code null}
     */
    @Override
    public User findByEmail(String email) {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {

            statement.setString(1, normalizeEmail(email));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password_hash")
                );
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to find user by email.", exception);
        }
    }

    /**
     * Saves a new user with the required registration fields.
     *
     * @param userId the user identifier to store
     * @param email the email address to store
     * @param emailNorm the normalized email address to store
     * @param passwordHash the password hash to store
     */
    public void save(String userId, String email, String emailNorm, String passwordHash) {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {

            statement.setString(1, userId);
            statement.setString(2, email);
            statement.setString(3, emailNorm);
            statement.setString(4, passwordHash);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to save user.", exception);
        }
    }

    /**
     * Creates the users table when it does not exist.
     */
    private void createUsersTableIfNotExists() {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL)) {

            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to create users table.", exception);
        }
    }

    /**
     * Creates a new MySQL database connection from environment variables.
     *
     * @return the open database connection
     * @throws SQLException when the database connection fails
     */
    private Connection createConnection() throws SQLException {
        String url = getRequiredEnv("DB_URL");
        String user = getRequiredEnv("DB_USER");
        String password = getRequiredEnv("DB_PASSWORD");
        try {
            // Explicitly load and register the MySQL JDBC driver because automatic driver discovery
            // did not work reliably in the Tomcat Docker runtime environment.
            // If another database type is used in the future, replace this driver class accordingly.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
          throw new IllegalStateException("MySQL driver not found.", exception);
        }
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Reads a required environment variable.
     *
     * @param key the environment variable name
     * @return the environment variable value
     */
    private String getRequiredEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    /**
     * Normalizes an email address for case-insensitive lookups.
     *
     * @param email the email address to normalize
     * @return the normalized email address
     */
    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
