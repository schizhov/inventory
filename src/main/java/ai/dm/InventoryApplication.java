package ai.dm;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryApplication extends Application<InventoryConfiguration> {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryApplication.class);

    public static void main(String[] args) throws Exception {
        new InventoryApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<InventoryConfiguration> bootstrap) {
    }

    @Override
    public void run(InventoryConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new ProductResource());
        LOG.info("Started");
    }
}
