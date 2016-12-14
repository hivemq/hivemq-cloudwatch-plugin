package com.hivemq.monitoring.cloudwatch;

import com.amazonaws.ClientConfiguration;
import com.hivemq.spi.config.SystemInformation;

import javax.inject.Inject;

/**
 * @author Georg Held
 */
public class CloudwatchConfiguration extends CloudwatchConfigurationReader {

    @Inject
    CloudwatchConfiguration(final SystemInformation systemInformation) {
        super(systemInformation);
    }

    @Override
    protected String getFileName() {
        return "cloudwatch_config.properties";
    }

    public int getReportInterval() {
        return Integer.parseInt(super.properties.getProperty("reportInterval", "1"));
    }

    public int getConnectionTimeout() {
        return Integer.parseInt(super.properties.getProperty("connectionTimeout", "" + ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT));
    }

}
