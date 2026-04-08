package dev.rubinaw.auth;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Activates JAX-RS for the application and exposes resources from the root path.
 */
@ApplicationPath("/")
public class AuthApplication extends Application {
}
