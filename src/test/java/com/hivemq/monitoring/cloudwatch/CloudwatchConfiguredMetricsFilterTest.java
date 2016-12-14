package com.hivemq.monitoring.cloudwatch;

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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Dominik Obermaier
 */
public class CloudwatchConfiguredMetricsFilterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    SystemInformation systemInformation;


    private CloudwatchMetricsReader reader;
    private File configFolder;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        configFolder = temporaryFolder.newFolder();
        when(systemInformation.getConfigFolder()).thenReturn(configFolder);
        reader = new CloudwatchMetricsReader(systemInformation);
    }

    @Test
    public void test_read_properties() throws Exception {

        Files.write("property1\nproperty2", new File(configFolder, "cloudwatch_metrics.properties"), StandardCharsets.ISO_8859_1);

        final List<String> strings = reader.readProperties();

        assertEquals(true, strings.contains("property1"));
        assertEquals(true, strings.contains("property2"));

    }

    @Test
    public void test_read_properties_ignore_commented_out_properties() throws Exception {

        Files.write("property1\n#property2", new File(configFolder, "cloudwatch_metrics.properties"), StandardCharsets.ISO_8859_1);

        final List<String> strings = reader.readProperties();
        assertEquals(true, strings.contains("property1"));
        assertEquals(false, strings.contains("property2"));
    }

    @Test
    public void test_read_properties_ignore_values() throws Exception {

        Files.write("property1=value\nproperty2=value2", new File(configFolder, "cloudwatch_metrics.properties"), StandardCharsets.ISO_8859_1);

        final List<String> strings = reader.readProperties();

        assertEquals(true, strings.contains("property1"));
        assertEquals(true, strings.contains("property2"));

    }
}