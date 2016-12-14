package com.hivemq.monitoring.cloudwatch;

import com.hivemq.spi.HiveMQPluginModule;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.plugin.meta.Information;

/**
 * @author Dominik Obermaier
 */
@Information(name = "AWS Cloudwatch Plugin", author = "dc-square GmbH", version = "1.0")
public class CloudwatchPluginModule extends HiveMQPluginModule {

    @Override
    protected void configurePlugin() {
    }

    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return AWSCloudwatchPluginEntryPoint.class;
    }
}
