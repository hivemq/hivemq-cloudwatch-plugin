package com.hivemq.monitoring.cloudwatch;

import com.google.common.collect.ImmutableList;
import com.hivemq.spi.config.SystemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author Dominik Obermaier
 */
public class CloudwatchMetricsReader {


    private static final Logger log = LoggerFactory.getLogger(CloudwatchMetricsReader.class);

    private final SystemInformation systemInformation;

    @Inject
    CloudwatchMetricsReader(final SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }

    public List<String> readProperties() {

        final File configFolder = systemInformation.getConfigFolder();

        final File metricsFile = new File(configFolder, "cloudwatch_metrics.properties");

        if (metricsFile.canRead()) {

        } else {
            log.error("Could not read {}. No metrics are reported", metricsFile.getAbsolutePath());
            return ImmutableList.of();
        }

        try (InputStream is = new FileInputStream(metricsFile)) {

            log.debug("Reading property file {}", metricsFile.getAbsolutePath());

            final Properties properties = new Properties();
            properties.load(is);

            return ImmutableList.copyOf(properties.stringPropertyNames());

        } catch (IOException e) {
            log.error("An error occurred while reading the properties file {}. No metrics are reported", metricsFile.getAbsolutePath(), e);
        }


        return ImmutableList.of();
    }
}
