package com.nitsoft.ecommerce.notification.email.transport;

import java.io.InputStream;
import java.util.Properties;

public class EmailTransportConfiguration {

    private static final String PROPERTIES_FILE = "fluent-mail-api.properties";
    private static final String KEY_SMTP_SERVER = "smtp.server";
    private static final String KEY_AUTH_REQUIRED = "auth.required";
    private static final String KEY_USE_SECURE_SMTP = "use.secure.smtp";
    private static final String KEY_USERNAME = "smtp.username";
    private static final String KEY_PASSWORD = "smtp.password";
    private static String smtpServer = "";
    private static int smtpPort = 25;
    private static boolean authenticationRequired = false;
    private static boolean useSecureSmtp = false;
    private static String username = null;
    private static String password = null;

    static {
        Properties properties = loadProperties();

        String smtpServer = properties.getProperty(KEY_SMTP_SERVER);
        boolean authenticationRequired = Boolean.parseBoolean(KEY_AUTH_REQUIRED);
        boolean useSecureSmtp = Boolean.parseBoolean(KEY_USE_SECURE_SMTP);
        String username = properties.getProperty(KEY_USERNAME);
        String password = properties.getProperty(KEY_PASSWORD);

        configure(smtpServer,25, authenticationRequired, useSecureSmtp, username,
                password);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        InputStream inputStream = EmailTransportConfiguration.class.getResourceAsStream(PROPERTIES_FILE);

        if (inputStream == null) {
            inputStream = EmailTransportConfiguration.class.getResourceAsStream("/" + PROPERTIES_FILE);
        }

        try {
            properties.load(inputStream);
        } catch (Exception e) {
            // Properties file not found, no problem.
        }

        return properties;
    }

    /**
     * Configure mail transport to use the specified SMTP server. Because this
     * configuration mode does not require to inform username and password, it
     * assumes that authentication and secure SMTP are not required.
     * 
     * @param smtpServer
     *            The SMTP server to use for mail transport. To use a specific
     *            port, user the syntax server:port.
     */
    public static void configure(String smtpServer) {
        configure(smtpServer,25,false, false, null, null);
    }

    /**
     * @param smtpServer
     *            The SMTP server to use for mail transport. To use a specific
     *            port, user the syntax server:port.
     * @param authenticationRequired
     *            Informs if mail transport needs to authenticate to send mail
     *            or not.
     * @param useSecureSmtp
     *            Use secure SMTP to send messages.
     * @param username
     *            The SMTP username.
     * @param password
     *            The SMTP password.
     */
    public static void configure(String smtpServer,int smtpPort,
            boolean authenticationRequired, boolean useSecureSmtp,
            String username, String password) {
        EmailTransportConfiguration.smtpServer = smtpServer;
         EmailTransportConfiguration.smtpPort = smtpPort;
        EmailTransportConfiguration.authenticationRequired = authenticationRequired;
        EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
        EmailTransportConfiguration.username = username;
        EmailTransportConfiguration.password = password;
    } 
    
    
     public static void configure(String smtpServer,int smtpPort,boolean useSecureSmtp) {
        EmailTransportConfiguration.smtpServer = smtpServer;
         EmailTransportConfiguration.smtpPort = smtpPort;
        EmailTransportConfiguration.authenticationRequired = false;
        EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean useSecureSmtp() {
        return useSecureSmtp;
    }

    public int getSmtpPort() {
        return smtpPort;
    }
}
