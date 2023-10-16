package ai.dm;

import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryApplication extends Application<InventoryConfiguration> {

    private final static Logger LOG = LoggerFactory.getLogger(InventoryApplication.class);

    public static void main(String[] args) throws Exception {
        new InventoryApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<InventoryConfiguration> bootstrap) {
        EnvironmentVariableSubstitutor substitutor = new EnvironmentVariableSubstitutor(false);
        SubstitutingSourceProvider provider =
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), substitutor);
        bootstrap.setConfigurationSourceProvider(provider);
    }

    @Override
    public void run(InventoryConfiguration configuration, Environment environment) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        environment.jersey().register(new ProductResource(jdbi.onDemand(ProductDAO.class)));
        LOG.info("Started");
    }
}
