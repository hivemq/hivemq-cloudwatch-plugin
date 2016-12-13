package com.hivemq.monitoring.cloudwatch;

import com.amazonaws.ClientConfiguration;
import com.google.common.io.Files;
import com.hivemq.spi.config.SystemInformation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Georg Held
 */
public class CloudwatchConfigurationTest {

    @Mock
    private SystemInformation systemInformation;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File configFolder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        configFolder = temporaryFolder.newFolder();
        when(systemInformation.getConfigFolder()).thenReturn(configFolder);

    }

    @Test
    public void test_default_properties() throws Exception {

        final CloudwatchConfiguration cloudwatchConfiguration = new CloudwatchConfiguration(systemInformation);
        cloudwatchConfiguration.postConstruct();

        assertEquals(1, cloudwatchConfiguration.getReportInterval());
        assertEquals(ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT, cloudwatchConfiguration.getConnectionTimeout());
    }


    @Test
    public void test_custom_properties() throws Exception {
        Files.write("connectionTimeout=400\nreportInterval=5", new File(configFolder, "cloudwatch_config.properties"), StandardCharsets.ISO_8859_1);
        final CloudwatchConfiguration cloudwatchConfiguration = new CloudwatchConfiguration(systemInformation);
        cloudwatchConfiguration.postConstruct();

        assertEquals(5, cloudwatchConfiguration.getReportInterval());
        assertEquals(400, cloudwatchConfiguration.getConnectionTimeout());
    }

}