package com.hivemq.monitoring.cloudwatch;

import com.amazonaws.ClientConfiguration;
import com.hivemq.spi.config.SystemInformation;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Georg Held
 */
public abstract class CloudwatchConfigurationReader {

    private static final Logger log = getLogger(CloudwatchConfigurationReader.class);
    private final SystemInformation systemInformation;

    protected Properties properties;
    private File configFile;


    @Inject
    CloudwatchConfigurationReader(final SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }

    @PostConstruct
    public void postConstruct() {
        configFile = new File(systemInformation.getConfigFolder() + File.separator + getFileName());
        properties = new Properties();

        try (final FileReader configReader = new FileReader(configFile)) {
            properties.load(configReader);
        } catch (IOException ignored) {
            log.error("Not able to load configuration file {}, using default configs", configFile.getAbsolutePath());
        }
    }

    protected abstract String getFileName();
}
