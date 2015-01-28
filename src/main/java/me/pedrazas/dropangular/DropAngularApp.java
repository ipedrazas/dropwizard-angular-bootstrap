package me.pedrazas.dropangular;


import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.pedrazas.dropangular.om.Ping;

public class DropAngularApp extends Application<DropAngularConfiguration> {
	public static void main(String[] args) throws Exception {
		new DropAngularApp().run(args);
	}
	
	private final HibernateBundle<DropAngularConfiguration> hibernateBundle =
            new HibernateBundle<DropAngularConfiguration>(Ping.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(DropAngularConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
            
    private final MigrationsBundle<DropAngularConfiguration> migrationBundle = 
    		new MigrationsBundle<DropAngularConfiguration>() {
		        @Override
		        public DataSourceFactory getDataSourceFactory(DropAngularConfiguration configuration) {
		            return configuration.getDataSourceFactory();
		        }
		    };

	@Override
	public void initialize(Bootstrap<DropAngularConfiguration> bootstrap) {
		bootstrap.addBundle(migrationBundle);
        bootstrap.addBundle(hibernateBundle);        
        // with this, we're adding the webapp folder (located in our resources)
        // as root of our webapp.
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html"));
	}

	@Override
	public void run(DropAngularConfiguration config, Environment environment) throws ClassNotFoundException {
		final PingDAO pingDAO = new PingDAO(hibernateBundle.getSessionFactory());
		environment.jersey().register(new PingResource(pingDAO));
		// If you're using a version previous to v.0.8.x
		// you need this line to avoid the error:
		// Multiple servlets map to path: /*:
//    	environment.jersey().setUrlPattern("/api/*");
		// there's an option to configure your rootpath in your configuration.yml file
    	((DefaultServerFactory) config.getServerFactory()).setJerseyRootPath("/api/*");
    	
    	environment.healthChecks().register("Ping", new PingHealthCheck());
	}
}
