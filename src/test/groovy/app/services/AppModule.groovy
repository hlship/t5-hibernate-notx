package app.services

import com.howardlewisship.notx.modules.NoTxModule
import org.apache.tapestry5.SymbolConstants
import org.apache.tapestry5.ioc.MappedConfiguration
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.annotations.SubModule
import org.apache.tapestry5.ioc.services.ApplicationDefaults
import org.apache.tapestry5.ioc.services.SymbolProvider


// Always necessary to explicitly include modules built by this module, since there's no MANIFEST.MF
// until after test and build.
@SubModule(NoTxModule)
class AppModule {

    @Contribute(SymbolProvider)
    @ApplicationDefaults
    static void setupDefaults(MappedConfiguration<String,Object> configuration) {
        configuration.add SymbolConstants.PRODUCTION_MODE, false
    }

}