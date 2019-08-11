package org.sergei.processor.config.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Sergei Visotsky
 */
@Profile({"!plain", "!test"})
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}